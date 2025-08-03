package com.br.fiap.fortaleza.sabor.infrastructure.adapter;

import com.br.fiap.fortaleza.sabor.application.ports.out.RestaurantsRepositoryPort;
import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.RestaurantAlreadyExistsException;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.RestaurantNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserTypeMismatchException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.RestaurantMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.AddressRestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantRepositoryAdapter;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserRepositoryAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.br.fiap.fortaleza.sabor.infrastructure.common.MessageConstants.RESTAURANT_NOT_FOUND;
import static com.br.fiap.fortaleza.sabor.infrastructure.common.MessageConstants.USER_NOT_FOUND_WITH_EMAIL;

@Service
public class RestaurantRepositoryPortJpa implements RestaurantsRepositoryPort {

    Logger log = LoggerFactory.getLogger(RestaurantRepositoryPortJpa.class);
    private final RestaurantRepositoryAdapter restaurantRepositoryAdapter;
    private final UserRepositoryAdapter userRepositoryAdapter;
    private final RestaurantMapper mapper;

    public RestaurantRepositoryPortJpa(RestaurantRepositoryAdapter restaurantRepositoryAdapter, UserRepositoryAdapter userRepositoryAdapter, RestaurantMapper mapper) {
        this.restaurantRepositoryAdapter = restaurantRepositoryAdapter;
        this.userRepositoryAdapter = userRepositoryAdapter;
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

        var userEntity = userRepositoryAdapter.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_WITH_EMAIL + email));

        if("DONO".equals(userEntity.getEmail())) {
            throw new UserNotFoundException("User with email " + email + " is not a restaurant owner");
        }

        if (restaurantRepositoryAdapter.findByName(restaurant.getName()).isPresent()) {
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

        var savedEntity = restaurantRepositoryAdapter.save(restaurantEntity);
        return mapper.toRestaurantDomain(savedEntity);
    }

    @Override
    public Optional<Restaurant> update(Long idRestaurant, Restaurant restaurant) {
        log.info("Updating restaurant with id: {}", idRestaurant);

        var userEntity = userRepositoryAdapter.findByEmail(restaurant.getEmail())
                .orElseThrow(() -> new RestaurantNotFoundException("User not found with email: " + restaurant.getEmail()));

        if (!userEntity.getEmail().equals(restaurant.getEmail())) {
            throw new RestaurantAlreadyExistsException("Email does not match the registered user. " + restaurant.getEmail());
        }

        if("DONO".equals(userEntity.getEmail())) {
            throw new UserNotFoundException("User with email " + restaurant.getEmail() + " is not a restaurant owner");
        }

        var restaurantEntity = restaurantRepositoryAdapter.findById(idRestaurant)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + idRestaurant));

        restaurantEntity.setName(restaurant.getName());
        restaurantEntity.setTypeKitchen(restaurant.getKitchenType());

        restaurantEntity.getAddress().clear();
        restaurantEntity.getAddress().addAll(addressEntityList(restaurant.getAddress(), restaurantEntity));

        restaurantEntity.getBusinessHours().clear();
        restaurantEntity.getBusinessHours().addAll(mapper.toBusinessHoursEntities(restaurant, restaurantEntity));

        var updatedEntity = restaurantRepositoryAdapter.save(restaurantEntity);
        return Optional.of(mapper.toRestaurantDomain(updatedEntity));
    }

    @Override
    public Optional<Restaurant> getById(Long idRestaurant) {
        log.info("Received request to get restaurant with id: {}", idRestaurant);

        return restaurantRepositoryAdapter.findById(idRestaurant)
                .map(mapper::toRestaurantDomain)
                .or(() -> {
                    log.error("Restaurant not found with id: {}", idRestaurant);
                    throw new RestaurantNotFoundException(RESTAURANT_NOT_FOUND + idRestaurant);
                });
    }

    @Override
    public List<Restaurant> getAll() {
        log.info("Received request to get all restaurants");

        List<RestaurantEntity> entities = restaurantRepositoryAdapter.findAll();

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
    public Optional<Restaurant> updateOwner(Long idRestaurant, String ownerName, String email){
        log.info("Updating the restaurant owner with id: {}", idRestaurant);

        var restaurantEntity = restaurantRepositoryAdapter.findById(idRestaurant)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + idRestaurant));

        var userEntity = userRepositoryAdapter.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        if("DONO".equals(userEntity.getEmail()))

        {
            throw new UserTypeMismatchException("User with email " + email + " is not a restaurant owner");
        }

        if (userEntity.getEmail().equals(email) && userEntity.getNome().equals(ownerName)) {
            restaurantEntity.setOwner(userEntity);
            restaurantRepositoryAdapter.save(restaurantEntity);
            return Optional.of(mapper.toRestaurantDomain(restaurantEntity));
        }

        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> deleteById(Long idRestaurant) {
        log.info("Received request to delete restaurant with id: {}", idRestaurant);

        return restaurantRepositoryAdapter.findById(idRestaurant)
                .map(restaurantEntity -> {
                    restaurantRepositoryAdapter.delete(restaurantEntity);
                    log.info("Restaurant with id: {} deleted successfully", idRestaurant);
                    return mapper.toRestaurantDomain(restaurantEntity);
                })
                .or(() -> {
                    log.error("Restaurant not found with id: {}", idRestaurant);
                    throw new RestaurantNotFoundException(RESTAURANT_NOT_FOUND + idRestaurant);
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