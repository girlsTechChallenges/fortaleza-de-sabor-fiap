//package com.br.fiap.fortaleza.sabor.application.usecase.restaurant;
//
//import com.br.fiap.fortaleza.sabor.application.ports.out.RestaurantsRepositoryPort;
//import com.br.fiap.fortaleza.sabor.domain.model.restaurant.Restaurant;
//import com.br.fiap.fortaleza.sabor.infrastructure.exception.RestaurantAlreadyExistsException;
//import com.br.fiap.fortaleza.sabor.infrastructure.exception.UserNotFoundException;
//import com.br.fiap.fortaleza.sabor.utils.TestDataBuilder;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("CreateRestaurantUseCase Tests")
//class RestaurantUseCaseTest {
//
//    @Mock
//    private RestaurantsRepositoryPort restaurantRepository;
//
//    @InjectMocks
//    private RestaurantUseCase restaurantUseCase;
//
//    @Test
//    @DisplayName("Should create restaurant successfully when valid data provided")
//    void shouldCreateRestaurantSuccessfullyWhenValidDataProvided() {
//        // Arrange
//        Restaurant restaurantToCreate = TestDataBuilder.createValidRestaurant();
//        Restaurant createdRestaurant = TestDataBuilder.createValidRestaurant();
//
//        when(restaurantRepository.create(any(Restaurant.class))).thenReturn(createdRestaurant);
//
//        // Act
//        Restaurant result = restaurantUseCase.save(restaurantToCreate);
//
//        // Assert
//        assertNotNull(result, "Created restaurant should not be null");
//        assertEquals(createdRestaurant.getName(), result.getName(), "Restaurant name should match");
//        assertEquals(createdRestaurant.getKitchenType(), result.getKitchenType(), "Kitchen type should match");
//        assertEquals(createdRestaurant.getEmail(), result.getEmail(), "Email should match");
//        assertEquals(createdRestaurant.getOwner(), result.getOwner(), "Owner should match");
//        assertNotNull(result.getAddress(), "Address should be present");
//        assertNotNull(result.getBusinessHours(), "Business hours should be present");
//
//        verify(restaurantRepository, times(1)).create(restaurantToCreate);
//        verifyNoMoreInteractions(restaurantRepository);
//    }
//
//    @Test
//    @DisplayName("Should handle null restaurant gracefully")
//    void shouldHandleNullRestaurantGracefully() {
//        // Arrange
//        Restaurant nullRestaurant = null;
//
//        when(restaurantRepository.create(isNull())).thenReturn(null);
//
//        // Act
//        Restaurant result = restaurantUseCase.save(nullRestaurant);
//
//        // Assert
//        assertNull(result, "Result should be null for null input");
//
//        verify(restaurantRepository, times(1)).create(nullRestaurant);
//        verifyNoMoreInteractions(restaurantRepository);
//    }
//
//    @Test
//    @DisplayName("Should propagate UserNotFoundException when user not found")
//    void shouldPropagateUserNotFoundExceptionWhenUserNotFound() {
//        // Arrange
//        Restaurant restaurantToCreate = TestDataBuilder.createValidRestaurant();
//        UserNotFoundException userNotFoundException = new UserNotFoundException("User not found");
//
//        when(restaurantRepository.create(any(Restaurant.class))).thenThrow(userNotFoundException);
//
//        // Act & Assert
//        UserNotFoundException thrownException = assertThrows(
//            UserNotFoundException.class,
//            () -> restaurantUseCase.save(restaurantToCreate),
//            "Should throw UserNotFoundException when user not found"
//        );
//
//        assertEquals(userNotFoundException.getMessage(), thrownException.getMessage(),
//            "Exception message should be preserved");
//
//        verify(restaurantRepository, times(1)).create(restaurantToCreate);
//        verifyNoMoreInteractions(restaurantRepository);
//    }
//
//    @Test
//    @DisplayName("Should propagate RestaurantAlreadyExistsException when restaurant already exists")
//    void shouldPropagateRestaurantAlreadyExistsExceptionWhenRestaurantAlreadyExists() {
//        // Arrange
//        Restaurant restaurantToCreate = TestDataBuilder.createValidRestaurant();
//        RestaurantAlreadyExistsException alreadyExistsException =
//            new RestaurantAlreadyExistsException("Restaurant already exists");
//
//        when(restaurantRepository.create(any(Restaurant.class))).thenThrow(alreadyExistsException);
//
//        // Act & Assert
//        RestaurantAlreadyExistsException thrownException = assertThrows(
//            RestaurantAlreadyExistsException.class,
//            () -> restaurantUseCase.save(restaurantToCreate),
//            "Should throw RestaurantAlreadyExistsException when restaurant already exists"
//        );
//
//        assertEquals(alreadyExistsException.getMessage(), thrownException.getMessage(),
//            "Exception message should be preserved");
//
//        verify(restaurantRepository, times(1)).create(restaurantToCreate);
//        verifyNoMoreInteractions(restaurantRepository);
//    }
//
//    @Test
//    @DisplayName("Should handle repository generic exceptions")
//    void shouldHandleRepositoryGenericExceptions() {
//        // Arrange
//        Restaurant restaurantToCreate = TestDataBuilder.createValidRestaurant();
//        RuntimeException repositoryException = new RuntimeException("Database connection failed");
//
//        when(restaurantRepository.create(any(Restaurant.class))).thenThrow(repositoryException);
//
//        // Act & Assert
//        RuntimeException thrownException = assertThrows(
//            RuntimeException.class,
//            () -> restaurantUseCase.save(restaurantToCreate),
//            "Should propagate repository exceptions"
//        );
//
//        assertEquals(repositoryException.getMessage(), thrownException.getMessage(),
//            "Exception message should be preserved");
//
//        verify(restaurantRepository, times(1)).create(restaurantToCreate);
//        verifyNoMoreInteractions(restaurantRepository);
//    }
//
//    @Test
//    @DisplayName("Should create restaurant with complete address information")
//    void shouldCreateRestaurantWithCompleteAddressInformation() {
//        // Arrange
//        Restaurant restaurantToCreate = TestDataBuilder.createValidRestaurant();
//        Restaurant createdRestaurant = TestDataBuilder.createValidRestaurant();
//
//        when(restaurantRepository.create(any(Restaurant.class))).thenReturn(createdRestaurant);
//
//        // Act
//        Restaurant result = restaurantUseCase.save(restaurantToCreate);
//
//        // Assert
//        assertNotNull(result, "Created restaurant should not be null");
//        assertNotNull(result.getAddress(), "Address should be present");
//        assertFalse(result.getAddress().isEmpty(), "Address list should not be empty");
//
//        verify(restaurantRepository, times(1)).create(restaurantToCreate);
//        verifyNoMoreInteractions(restaurantRepository);
//    }
//
//    @Test
//    @DisplayName("Should create restaurant with business hours information")
//    void shouldCreateRestaurantWithBusinessHoursInformation() {
//        // Arrange
//        Restaurant restaurantToCreate = TestDataBuilder.createValidRestaurant();
//        Restaurant createdRestaurant = TestDataBuilder.createValidRestaurant();
//
//        when(restaurantRepository.create(any(Restaurant.class))).thenReturn(createdRestaurant);
//
//        // Act
//        Restaurant result = restaurantUseCase.save(restaurantToCreate);
//
//        // Assert
//        assertNotNull(result, "Created restaurant should not be null");
//        assertNotNull(result.getBusinessHours(), "Business hours should be present");
//        assertFalse(result.getBusinessHours().isEmpty(), "Business hours list should not be empty");
//
//        verify(restaurantRepository, times(1)).create(restaurantToCreate);
//        verifyNoMoreInteractions(restaurantRepository);
//    }
//
//    @Test
//    @DisplayName("Should validate restaurant creation flow")
//    void shouldValidateRestaurantCreationFlow() {
//        // Arrange
//        Restaurant restaurantToCreate = TestDataBuilder.createValidRestaurant();
//        Restaurant createdRestaurant = TestDataBuilder.createValidRestaurant();
//
//        when(restaurantRepository.create(any(Restaurant.class))).thenReturn(createdRestaurant);
//
//        // Act
//        Restaurant result = restaurantUseCase.save(restaurantToCreate);
//
//        // Assert
//        assertNotNull(result, "Result should not be null");
//        assertNotNull(result.getName(), "Restaurant name should not be null");
//        assertNotNull(result.getKitchenType(), "Kitchen type should not be null");
//        assertNotNull(result.getEmail(), "Email should not be null");
//        assertNotNull(result.getOwner(), "Owner should not be null");
//
//        // Verify that the use case properly delegates to repository
//        verify(restaurantRepository, times(1)).create(restaurantToCreate);
//        verifyNoMoreInteractions(restaurantRepository);
//    }
//}
