package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.BusinessHours;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.BusinessHoursDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.RestaurantRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.RestaurantUpdateDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.RestaurantFullDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.RestaurantResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.AddressRestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.BusinessHoursEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RestaurantMapper {

    public Restaurant toRestaurantDomain(RestaurantRequestDto dto) {
        List<Address> addresses = dto.address().stream()
                .map(addressDto -> new Address(
                        addressDto.rua(),
                        addressDto.bairro(),
                        addressDto.complemento(),
                        addressDto.numero(),
                        addressDto.estado(),
                        addressDto.cidade(),
                        addressDto.cep()))
                .toList();

        List<BusinessHours> businessHours = dto.businessHours().stream()
                .map(bh -> new BusinessHours(
                        bh.dayOfWeek(),
                        bh.openingTime(),
                        bh.closingTime(),
                        bh.observations()))
                .toList();

        return new Restaurant(
                null,
                dto.name(),
                dto.kitchenType(),
                dto.email(),
                null,
                addresses,
                businessHours);
    }

    public Restaurant toRestaurantDomain(RestaurantUpdateDto dto) {
        List<Address> addresses = dto.address().stream()
                .map(addressDto -> new Address(
                        addressDto.rua(),
                        addressDto.bairro(),
                        addressDto.complemento(),
                        addressDto.numero(),
                        addressDto.estado(),
                        addressDto.cidade(),
                        addressDto.cep()))
                .toList();

        List<BusinessHours> businessHours = dto.businessHours().stream()
                .map(bh -> new BusinessHours(
                        bh.dayOfWeek(),
                        bh.openingTime(),
                        bh.closingTime(),
                        bh.observations()))
                .toList();

        return new Restaurant(
                null,
                dto.name(),
                dto.kitchenType(),
                null,
                null,
                addresses,
                businessHours);
    }

    public RestaurantEntity toRestaurantEntity(Restaurant restaurant) {
        RestaurantEntity entity = new RestaurantEntity(
                restaurant.getName(),
                restaurant.getKitchenType(),
                new UserEntity(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        List<AddressRestaurantEntity> addressEntities = restaurant.getAddress().stream()
                .map(address -> {
                    AddressRestaurantEntity ae = new AddressRestaurantEntity(
                            address.getRua(),
                            address.getBairro(),
                            address.getComplemento(),
                            address.getNumero(),
                            address.getEstado(),
                            address.getCidade(),
                            address.getCep()
                    );
                    ae.setRestaurante(entity);
                    return ae;
                })
                .toList();

        List<BusinessHoursEntity> businessHoursEntities = restaurant.getBusinessHours().stream()
                .filter(bh -> bh.getDayOfWeek() != null && bh.getOpeningTime() != null && bh.getClosingTime() != null)
                .map(bh -> new BusinessHoursEntity(
                        bh.getDayOfWeek(),
                        bh.getOpeningTime(),
                        bh.getClosingTime(),
                        bh.getObservations(),
                        entity))
                .toList();

        entity.setAddress(addressEntities);
        entity.setBusinessHours(businessHoursEntities);
        return entity;
    }

    public List<BusinessHoursEntity> toBusinessHoursEntities(Restaurant restaurant, RestaurantEntity entity) {
        return restaurant.getBusinessHours().stream()
                .filter(bh -> bh.getDayOfWeek() != null && bh.getOpeningTime() != null && bh.getClosingTime() != null)
                .map(bh -> new BusinessHoursEntity(
                        bh.getDayOfWeek(),
                        bh.getOpeningTime(),
                        bh.getClosingTime(),
                        bh.getObservations(),
                        entity))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Restaurant toRestaurantDomain(RestaurantEntity entity) {
        List<Address> addresses = entity.getAddress().stream()
                .map(ae -> new Address(
                        ae.getRua(),
                        ae.getBairro(),
                        ae.getComplemento(),
                        ae.getNumero(),
                        ae.getEstado(),
                        ae.getCidade(),
                        ae.getCep()))
                .toList();

        List<BusinessHours> businessHours = entity.getBusinessHours().stream()
                .map(bh -> new BusinessHours(
                        bh.getDay(),
                        bh.getOpeningTime(),
                        bh.getClosingTime(),
                        bh.getObservations()))
                .toList();

        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getTypeKitchen(),
                entity.getOwner().getEmail(),
                entity.getOwner().getNome(),
                addresses,
                businessHours);
    }

    public RestaurantResponseDto toRestaurantResponseDto(Restaurant restaurant) {
        return new RestaurantResponseDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getOwner());
    }

    public RestaurantFullDto toRestaurantFullByIdDto(Restaurant restaurant) {
        return new RestaurantFullDto(restaurant.getId(),
                restaurant.getName(),
                restaurant.getKitchenType(),
                restaurant.getEmail(),
                restaurant.getOwner(),
                convertAddresses(restaurant.getAddress()),
                convertBusinessHours(restaurant.getBusinessHours()));
    }

    public RestaurantFullDto toRestaurantFullDto(Restaurant restaurant) {
        return new RestaurantFullDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getKitchenType(),
                restaurant.getEmail(),
                restaurant.getOwner(),
                convertAddresses(restaurant.getAddress()),
                convertBusinessHours(restaurant.getBusinessHours())
        );
    }

    private List<AddressDto> convertAddresses(List<Address> addresses) {
        return addresses.stream()
                .map(address -> new AddressDto(
                        address.getRua(),
                        address.getBairro(),
                        address.getComplemento(),
                        address.getNumero(),
                        address.getEstado(),
                        address.getCidade(),
                        address.getCep()))
                .toList();
    }

    private List<BusinessHoursDto> convertBusinessHours(List<BusinessHours> businessHours) {
        return businessHours.stream()
                .map(bh -> new BusinessHoursDto(
                        bh.getDayOfWeek(),
                        bh.getOpeningTime(),
                        bh.getClosingTime(),
                        bh.getObservations()))
                .toList();
    }

    public RestaurantResponseDto toRestaurantResponseDto(Optional<Restaurant> updatedRestaurant) {
        return updatedRestaurant.map(this::toRestaurantResponseDto)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
    }
}