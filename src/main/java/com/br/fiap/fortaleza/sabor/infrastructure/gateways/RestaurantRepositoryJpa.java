package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.RestaurantsRepository;
import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.RestaurantAlreadyExistsException;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.RestaurantNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.RestaurantMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.RestaurantRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.AddressRestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantRepositoryJpa implements RestaurantsRepository {

    private static final Logger log = LoggerFactory.getLogger(RestaurantRepositoryJpa.class);
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RestaurantMapper mapper;

    public RestaurantRepositoryJpa(RestaurantRepository restaurantRepository, UserRepository userRepository, RestaurantMapper mapper) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        String email = restaurant.getEmail();
        if (email == null) {
            log.error("Restaurant email is null.");
            return null;
        }

        log.info("Creating restaurant with email: {}", email);

        var userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        if (!TypeEntityEnum.DONO.equals(userEntity.getTipo())) {
            throw new UserNotFoundException("User with email " + email + " is not a restaurant owner");
        }

        if (restaurantRepository.findByName(restaurant.getName()).isPresent()) {
            throw new RestaurantAlreadyExistsException("Restaurant with name " + restaurant.getName() + " already exists");
        }

        RestaurantEntity restaurantEntity = mapper.toRestaurantEntity(restaurant);
        restaurantEntity.setOwner(userEntity);
        restaurantEntity.setAddress(addressEntityList(restaurant.getAddress(), restaurantEntity));
        restaurantEntity.setBusinessHours(mapper.toBusinessHoursEntities(restaurant, restaurantEntity));

        restaurantEntity.getBusinessHours().forEach(bh -> {
            if (bh == null) {
                log.warn("Null time detected");
            } else {
                log.info("Valid time: {} - {} às {}", bh.getDay(), bh.getOpeningTime(), bh.getClosingTime());
            }
        });

        var savedEntity = restaurantRepository.save(restaurantEntity);
        return mapper.toRestaurantDomain(savedEntity);
    }

    @Override
    public Optional<Restaurant> update(Long idRestaurant, Restaurant restaurant) {
        log.info("Updating restaurant with id: {}", idRestaurant);

        var userEntity = userRepository.findByEmail(restaurant.getEmail())
                .orElseThrow(() -> new RestaurantNotFoundException("User not found with email: " + restaurant.getEmail()));

        if (!userEntity.getEmail().equals(restaurant.getEmail())) {
            throw new RestaurantAlreadyExistsException("Email does not match the registered user. " + restaurant.getEmail());
        }

        if (!TypeEntityEnum.DONO.equals(userEntity.getTipo())) {
            throw new UserNotFoundException("User with email " + restaurant.getEmail() + " is not a restaurant owner");
        }

        var restaurantEntity = restaurantRepository.findById(idRestaurant)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + idRestaurant));

        restaurantEntity.setName(restaurant.getName());
        restaurantEntity.setTypeKitchen(restaurant.getKitchenType());

        restaurantEntity.getAddress().clear();
        restaurantEntity.getAddress().addAll(addressEntityList(restaurant.getAddress(), restaurantEntity));

        restaurantEntity.getBusinessHours().clear();
        restaurantEntity.getBusinessHours().addAll(mapper.toBusinessHoursEntities(restaurant, restaurantEntity));

        var updatedEntity = restaurantRepository.save(restaurantEntity);
        return Optional.of(mapper.toRestaurantDomain(updatedEntity));
    }

    @Override
    public Optional<Restaurant> getById(Long idRestaurant) {
        log.info("Received request to get restaurant with id: {}", idRestaurant);

        return restaurantRepository.findById(idRestaurant)
                .map(mapper::toRestaurantDomain)
                .or(() -> {
                    log.error("Restaurant not found with id: {}", idRestaurant);
                    throw new RestaurantNotFoundException("Restaurant not found with id: " + idRestaurant);
                });
    }

    @Override
    public List<Restaurant> getAll() {
        log.info("Received request to get all restaurants");

        List<RestaurantEntity> entities = restaurantRepository.findAll();

        if (entities.isEmpty()) {
            log.warn("No restaurants found");
            return Collections.emptyList();
        }

        log.info("Found {} restaurants", entities.size());
        return entities.stream()
                .map(mapper::toRestaurantDomain)
                .toList();
    }

    @Override
    public Optional<Restaurant> deleteById(Long idRestaurant) {
        log.info("Received request to delete restaurant with id: {}", idRestaurant);

        return restaurantRepository.findById(idRestaurant)
                .map(restaurantEntity -> {
                    restaurantRepository.delete(restaurantEntity);
                    log.info("Restaurant with id: {} deleted successfully", idRestaurant);
                    return mapper.toRestaurantDomain(restaurantEntity);
                })
                .or(() -> {
                    log.error("Restaurant not found with id: {}", idRestaurant);
                    throw new RestaurantNotFoundException("Restaurant not found with id: " + idRestaurant);
                });
    }

    private List<AddressRestaurantEntity> addressEntityList(List<Address> addresses, RestaurantEntity restaurantEntity) {
        return addresses.stream()
                .map(address -> {
                    AddressRestaurantEntity newAddress = new AddressRestaurantEntity();
                    newAddress.setRua(address.getRua());
                    newAddress.setBairro(address.getBairro());
                    newAddress.setComplemento(address.getComplemento());
                    newAddress.setNumero(address.getNumero());
                    newAddress.setEstado(address.getEstado());
                    newAddress.setCidade(address.getCidade());
                    newAddress.setCep(address.getCep());
                    newAddress.setRestaurante(restaurantEntity);
                    return newAddress;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }
}