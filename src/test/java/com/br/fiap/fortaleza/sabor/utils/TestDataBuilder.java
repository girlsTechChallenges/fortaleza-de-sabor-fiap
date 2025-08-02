package com.br.fiap.fortaleza.sabor.utils;

import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.BusinessHours;
import com.br.fiap.fortaleza.sabor.domain.model.restaurant.Restaurant;
import com.br.fiap.fortaleza.sabor.domain.model.user.TypeUser;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.*;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.*;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu.MenuItemsEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.AddressRestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.BusinessHoursEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import java.util.List;

import static com.br.fiap.fortaleza.sabor.utils.TestConstants.*;

/**
 * Builder de objetos para testes unitários
 * Facilita a criação de objetos de teste com dados consistentes
 */
public final class TestDataBuilder {

    private TestDataBuilder() {
        // Utility class
    }

    // User Domain Builders
    public static User createValidUser() {
        return new User(
                VALID_USER_NAME,
                VALID_USER_EMAIL,
                VALID_USER_LOGIN,
                VALID_USER_PASSWORD,
                VALID_USER_DATE,
                OWNER_TYPE_NAME,
                List.of(createValidAddress())
        );
    }

    public static User createUserWithId(Long id) {
        // User domain class doesn't have setId, so we create new instance with all fields
        User user = createValidUser();
        // Since User doesn't have ID field in domain, we return the user as is
        // ID is handled at entity level
        return user;
    }

    public static TypeUser createValidTypeUser() {
        return new TypeUser(OWNER_TYPE_ID, OWNER_TYPE_NAME);
    }

    public static TypeUser createClientTypeUser() {
        return new TypeUser(CLIENT_TYPE_ID, CLIENT_TYPE_NAME);
    }

    public static Address createValidAddress() {
        return new Address(
                VALID_STREET,
                VALID_NEIGHBORHOOD,
                VALID_COMPLEMENT,
                VALID_NUMBER,
                VALID_STATE,
                VALID_CITY,
                VALID_CEP
        );
    }

    // Menu Item Domain Builders
    public static MenuItem createValidMenuItem() {
        return new MenuItem(
                VALID_MENU_NAME,
                VALID_MENU_DESCRIPTION,
                VALID_MENU_PRICE,
                VALID_MENU_AVAILABILITY,
                VALID_MENU_IMAGE
        );
    }

    public static MenuItem createMenuItemWithId(Long id) {
        // MenuItem domain class doesn't have setId, so we return the item as is
        // ID is handled at entity level
        return createValidMenuItem();
    }

    // Restaurant Domain Builders
    public static Restaurant createValidRestaurant() {
        return new Restaurant(
                VALID_RESTAURANT_ID,
                VALID_RESTAURANT_NAME,
                VALID_RESTAURANT_KITCHEN_TYPE,
                VALID_RESTAURANT_EMAIL,
                VALID_RESTAURANT_OWNER,
                List.of(createValidAddress()),
                List.of(createValidBusinessHours())
        );
    }

    public static BusinessHours createValidBusinessHours() {
        return new BusinessHours(
                VALID_DAY_OF_WEEK,
                VALID_OPENING_TIME,
                VALID_CLOSING_TIME,
                VALID_BUSINESS_HOURS_OBSERVATION
        );
    }

    // Entity Builders
    public static UserEntity createValidUserEntity() {
        return new UserEntity(
                VALID_ID,
                VALID_USER_NAME,
                VALID_USER_EMAIL,
                VALID_USER_LOGIN,
                VALID_USER_PASSWORD,
                VALID_USER_DATE,
                createValidTypeEntity(),
                List.of(createValidAddressEntity())
        );
    }

    public static TypeEntity createValidTypeEntity() {
        return new TypeEntity(OWNER_TYPE_ID, OWNER_TYPE_NAME);
    }

    public static AddressEntity createValidAddressEntity() {
        return new AddressEntity(
                VALID_STREET,
                VALID_NEIGHBORHOOD,
                VALID_COMPLEMENT,
                VALID_NUMBER,
                VALID_STATE,
                VALID_CITY,
                VALID_CEP
        );
    }

    public static MenuItemsEntity createValidMenuItemEntity() {
        return new MenuItemsEntity(
                VALID_MENU_NAME,
                VALID_MENU_DESCRIPTION,
                VALID_MENU_PRICE,
                VALID_MENU_AVAILABILITY,
                VALID_MENU_IMAGE
        );
    }

    public static RestaurantEntity createValidRestaurantEntity() {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(VALID_RESTAURANT_ID);
        entity.setName(VALID_RESTAURANT_NAME);
        entity.setTypeKitchen(VALID_RESTAURANT_KITCHEN_TYPE);
        entity.setOwner(createValidUserEntity());
        entity.setAddress(List.of(createValidAddressRestaurantEntity()));
        entity.setBusinessHours(List.of(createValidBusinessHoursEntity()));
        return entity;
    }

    public static AddressRestaurantEntity createValidAddressRestaurantEntity() {
        AddressRestaurantEntity entity = new AddressRestaurantEntity();
        entity.setRua(VALID_STREET);
        entity.setBairro(VALID_NEIGHBORHOOD);
        entity.setComplemento(VALID_COMPLEMENT);
        entity.setNumero(VALID_NUMBER);
        entity.setEstado(VALID_STATE);
        entity.setCidade(VALID_CITY);
        entity.setCep(VALID_CEP);
        return entity;
    }

    public static BusinessHoursEntity createValidBusinessHoursEntity() {
        BusinessHoursEntity entity = new BusinessHoursEntity();
        entity.setDay(VALID_DAY_OF_WEEK);
        entity.setOpeningTime(VALID_OPENING_TIME);
        entity.setClosingTime(VALID_CLOSING_TIME);
        entity.setObservations(VALID_BUSINESS_HOURS_OBSERVATION);
        return entity;
    }

    // DTO Builders
    public static UserRequestDto createValidUserRequestDto() {
        return new UserRequestDto(
                VALID_USER_NAME,
                VALID_USER_EMAIL,
                VALID_USER_LOGIN,
                VALID_USER_PASSWORD,
                VALID_USER_DATE,
                OWNER_TYPE_NAME,
                List.of(createValidAddressDto())
        );
    }

    public static AddressDto createValidAddressDto() {
        return new AddressDto(
                VALID_STREET,
                VALID_NEIGHBORHOOD,
                VALID_COMPLEMENT,
                VALID_NUMBER,
                VALID_STATE,
                VALID_CITY,
                VALID_CEP
        );
    }

    public static MenuItemRequestDto createValidMenuItemRequestDto() {
        return new MenuItemRequestDto(
                VALID_MENU_NAME,
                VALID_MENU_DESCRIPTION,
                VALID_MENU_PRICE,
                VALID_MENU_AVAILABILITY,
                VALID_MENU_IMAGE
        );
    }

    public static RestaurantRequestDto createValidRestaurantRequestDto() {
        return new RestaurantRequestDto(
                VALID_RESTAURANT_NAME,
                VALID_RESTAURANT_KITCHEN_TYPE,
                VALID_RESTAURANT_EMAIL,
                List.of(createValidAddressDto()),
                List.of(createValidBusinessHoursDto())
        );
    }

    public static BusinessHoursDto createValidBusinessHoursDto() {
        return new BusinessHoursDto(
                VALID_DAY_OF_WEEK,
                VALID_OPENING_TIME,
                VALID_CLOSING_TIME,
                VALID_BUSINESS_HOURS_OBSERVATION
        );
    }

    public static TypeUserRequestDto createValidTypeUserRequestDto() {
        return new TypeUserRequestDto(OWNER_TYPE_NAME);
    }

    // Response DTO Builders
    public static UserResponseDto createValidUserResponseDto() {
        return new UserResponseDto(
                VALID_USER_NAME,
                VALID_USER_LOGIN,
                VALID_USER_EMAIL,
                OWNER_TYPE_NAME,
                List.of(createValidAddressDto())
        );
    }

    public static MenuItemResponseDto createValidMenuItemResponseDto() {
        return new MenuItemResponseDto(
                VALID_MENU_NAME,
                VALID_MENU_DESCRIPTION,
                VALID_MENU_PRICE,
                VALID_MENU_AVAILABILITY,
                VALID_MENU_IMAGE
        );
    }

    public static RestaurantResponseDto createValidRestaurantResponseDto() {
        return new RestaurantResponseDto(
                VALID_RESTAURANT_ID,
                VALID_RESTAURANT_NAME,
                VALID_RESTAURANT_OWNER
        );
    }
}
