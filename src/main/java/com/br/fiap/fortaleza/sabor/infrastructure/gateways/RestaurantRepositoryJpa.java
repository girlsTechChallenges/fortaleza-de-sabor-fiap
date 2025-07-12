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
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.AddressEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            log.error("Email do restaurante é nulo.");
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
        restaurantEntity.setAddress(addressEntityList(restaurant.getAddress()));
        restaurantEntity.setBusinessHours(mapper.toBusinessHoursEntities(restaurant, restaurantEntity));

        restaurantEntity.getBusinessHours().forEach(bh -> {
            if (bh == null) {
                log.warn("⚠️ Horário nulo detectado.");
            } else {
                log.info("🕑 Horário válido: {} - {} às {}", bh.getDay(), bh.getOpeningTime(), bh.getClosingTime());
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

        if(!userEntity.getEmail().equals(restaurant.getEmail())) {
            throw new RestaurantAlreadyExistsException("Email does not match the registered user. " + restaurant.getEmail());
        }

        if (restaurantRepository.findByName(restaurant.getName()).isPresent()) {
            throw new RestaurantAlreadyExistsException("Restaurant with name " + restaurant.getName() + " already exists");
        }

        if (!TypeEntityEnum.DONO.equals(userEntity.getTipo())) {
            throw new UserNotFoundException("User with email " + restaurant.getEmail() + " is not a restaurant owner");
        }

        var restaurantEntity = restaurantRepository.findById(idRestaurant)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + idRestaurant));

        restaurantEntity.setName(restaurant.getName());
        restaurantEntity.setTypeKitchen(restaurant.getKitchenType());
        restaurantEntity.setAddress(addressEntityList(restaurant.getAddress()));
        restaurantEntity.setBusinessHours(mapper.toBusinessHoursEntities(restaurant, restaurantEntity));

        var updatedEntity = restaurantRepository.save(restaurantEntity);
        return Optional.of(mapper.toRestaurantDomain(updatedEntity));
    }

    private List<AddressEntity> addressEntityList(List<Address> addresses) {
        return addresses.stream()
                .map(address -> {
                    AddressEntity newAddress = new AddressEntity();
                    newAddress.setRua(address.getRua());
                    newAddress.setBairro(address.getBairro());
                    newAddress.setComplemento(address.getComplemento());
                    newAddress.setNumero(address.getNumero());
                    newAddress.setEstado(address.getEstado());
                    newAddress.setCidade(address.getCidade());
                    newAddress.setCep(address.getCep());
                    return newAddress;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }
}