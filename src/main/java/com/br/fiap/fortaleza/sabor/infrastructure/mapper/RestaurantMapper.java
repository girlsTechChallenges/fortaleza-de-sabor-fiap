package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.restaurant.BusinessHours;
import com.br.fiap.fortaleza.sabor.domain.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.RestaurantRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.RestaurantResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.BusinessHoursEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.AddressEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantMapper {

    public Restaurant toRestaurantDomain(RestaurantRequestDto restaurantRequestDto) {

        List<Address> addressList = restaurantRequestDto.address().stream()
                .map(addressDto -> new Address(
                        addressDto.rua(),
                        addressDto.bairro(),
                        addressDto.complemento(),
                        addressDto.numero(),
                        addressDto.estado(),
                        addressDto.cidade(),
                        addressDto.cep()
                )).toList();

        List<BusinessHours> businessHoursList = restaurantRequestDto.businessHours().stream()
                .map(dto -> new BusinessHours(
                        dto.dayOfWeek(),
                        dto.openingTime(),
                        dto.closingTime(),
                        dto.observations()
                )).toList();

        return new Restaurant(
                null,
                restaurantRequestDto.name(),
                restaurantRequestDto.kitchenType(),
                restaurantRequestDto.email(),
                null,
                addressList,
                businessHoursList
        );
    }

    public Restaurant toRestaurantDomain(RestaurantEntity restaurantEntity) {

        List<Address> addresses = restaurantEntity.getAddress().stream()
                .map(addressEntity -> new Address(
                        addressEntity.getRua(),
                        addressEntity.getBairro(),
                        addressEntity.getComplemento(),
                        addressEntity.getNumero(),
                        addressEntity.getEstado(),
                        addressEntity.getCidade(),
                        addressEntity.getCep()
                )).toList();

        List<BusinessHours> businessHours = restaurantEntity.getBusinessHours().stream()
                .map(businessHoursEntity -> new BusinessHours(
                        businessHoursEntity.getDay(),
                        businessHoursEntity.getOpeningTime(),
                        businessHoursEntity.getClosingTime(),
                        businessHoursEntity.getObservations()
                )).toList();

        return new Restaurant(
                restaurantEntity.getId(),
                restaurantEntity.getname(),
                restaurantEntity.getKitchenType(),
                restaurantEntity.getUser() != null ? restaurantEntity.getUser().getEmail() : null,
                restaurantEntity.getUser() != null ? restaurantEntity.getUser().getNome() : null,
                addresses,
                businessHours
        );
    }

    public RestaurantEntity toRestaurantEntity(Restaurant restaurant) {

        List<AddressEntity> addressEntities = restaurant.getAddress()
                .stream().map(address -> new AddressEntity(
                        address.getRua(),
                        address.getBairro(),
                        address.getComplemento(),
                        address.getNumero(),
                        address.getEstado(),
                        address.getCidade(), address.getCep())).toList();

        List<BusinessHoursEntity> businessHoursEntities = restaurant.getBusinessHours()
                .stream().map(businessHours -> new BusinessHoursEntity(
                        businessHours.getDayOfWeek(),
                        businessHours.getOpeningTime(),
                        businessHours.getClosingTime(),
                        businessHours.getObservations(), null )).toList();

        return new RestaurantEntity(
                restaurant.getName(),
                restaurant.getKitchenType(),
                addressEntities,
                businessHoursEntities,
               null
        );
    }

    public RestaurantResponseDto toRestaurantResponseDto(Restaurant restaurant) {
        return new RestaurantResponseDto(restaurant.getId(), restaurant.getKitchenType(), restaurant.getOwner());
    }
}
