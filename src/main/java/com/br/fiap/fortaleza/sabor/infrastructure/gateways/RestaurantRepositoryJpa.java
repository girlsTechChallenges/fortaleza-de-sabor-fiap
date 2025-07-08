package com.br.fiap.fortaleza.sabor.infrastructure.gateways;

import com.br.fiap.fortaleza.sabor.application.gateways.RestaurantsRepository;
import com.br.fiap.fortaleza.sabor.domain.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.RestaurantAlreadyExistsException;
import com.br.fiap.fortaleza.sabor.infrastructure.config.exception.UserNotFoundException;
import com.br.fiap.fortaleza.sabor.infrastructure.mapper.RestaurantMapper;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.RestaurantRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserRepository;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.BusinessHoursEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        if (email == null) return null;

        log.info("Creating restaurant with email: {}", email);
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found with email: " + email);
        }

        UserEntity userEntity = optionalUser.get();

        if (!TypeEntityEnum.DONO.equals(userEntity.getTipo())) {
            throw new UserNotFoundException("User with email " + email + " is not a restaurant owner");
        }

        if(!verifyRestaurant(restaurant.getName(), restaurant.getEmail())) {
            throw new RestaurantAlreadyExistsException("Restaurant with name " + restaurant.getName() + " already exists");
        }

        RestaurantEntity restaurantEntity = mapper.toRestaurantEntity(restaurant);
        restaurantEntity.setUser(userEntity);

        List<AddressEntity> addresses = userEntity.getEnderecos().stream()
                .map(address -> {
                    AddressEntity newAddress = new AddressEntity();
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
                .toList();

        restaurantEntity.setAddress(addresses);

        List<BusinessHoursEntity> business = restaurant.getBusinessHours().stream()
                .map(bh -> new BusinessHoursEntity(
                        bh.getDayOfWeek(),
                        bh.getOpeningTime(),
                        bh.getClosingTime(),
                        bh.getObservations(),
                        restaurantEntity))
                .toList();

        restaurantEntity.setBusinessHours(business);

        return mapper.toRestaurantDomain(restaurantRepository.save(restaurantEntity));
    }

    private boolean verifyRestaurant(String nome, String email) {

        var findName = restaurantRepository.findByName(nome);
        if(findName.isPresent()) {
            log.error("Restaurant with name {} already exists", nome);
            throw new RestaurantAlreadyExistsException("Restaurant with name " + nome + " already exists");
        }
        return true;
    }
}
