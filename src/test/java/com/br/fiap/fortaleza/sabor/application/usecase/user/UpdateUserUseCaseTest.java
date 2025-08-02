//package com.br.fiap.fortaleza.sabor.application.usecase.user;
//
//import com.br.fiap.fortaleza.sabor.application.ports.out.UsersRepositoryPort;
//import com.br.fiap.fortaleza.sabor.domain.model.user.User;
//import com.br.fiap.fortaleza.sabor.utils.TestConstants;
//import com.br.fiap.fortaleza.sabor.utils.TestDataBuilder;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("UpdateUserUseCase Tests")
//class UpdateUserUseCaseTest {
//
//    @Mock
//    private UsersRepositoryPort usersRepositoryPort;
//
//    @Mock
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UpdateUserUseCase updateUserUseCase;
//
//    @Test
//    @DisplayName("Should update user successfully when user exists")
//    void shouldUpdateUserSuccessfullyWhenUserExists() {
//        // Arrange
//        Long userId = TestConstants.VALID_ID;
//        User userToUpdate = TestDataBuilder.createValidUser();
//        User updatedUser = TestDataBuilder.createValidUser();
//        Optional<User> expectedResult = Optional.of(updatedUser);
//        String encodedPassword = "encodedPassword123";
//
//        when(passwordEncoder.encode(TestConstants.VALID_USER_PASSWORD)).thenReturn(encodedPassword);
//        when(usersRepositoryPort.update(eq(userId), any(User.class))).thenReturn(expectedResult);
//
//        // Act
//        Optional<User> result = updateUserUseCase.update(userId, userToUpdate);
//
//        // Assert
//        assertTrue(result.isPresent(), "Update result should be present");
//        assertEquals(updatedUser, result.get(), "Should return updated user");
//
//        verify(passwordEncoder, times(1)).encode(TestConstants.VALID_USER_PASSWORD);
//        verify(usersRepositoryPort, times(1)).update(eq(userId), any(User.class));
//        verifyNoMoreInteractions(usersRepositoryPort, passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("Should return empty Optional when user not found")
//    void shouldReturnEmptyOptionalWhenUserNotFound() {
//        // Arrange
//        Long nonExistentId = TestConstants.INVALID_ID;
//        User userToUpdate = TestDataBuilder.createValidUser();
//        String encodedPassword = "encodedPassword123";
//
//        when(passwordEncoder.encode(TestConstants.VALID_USER_PASSWORD)).thenReturn(encodedPassword);
//        when(usersRepositoryPort.update(eq(nonExistentId), any(User.class))).thenReturn(Optional.empty());
//
//        // Act
//        Optional<User> result = updateUserUseCase.update(nonExistentId, userToUpdate);
//
//        // Assert
//        assertFalse(result.isPresent(), "Result should not be present");
//        assertTrue(result.isEmpty(), "Result should be empty when user not found");
//
//        verify(passwordEncoder, times(1)).encode(TestConstants.VALID_USER_PASSWORD);
//        verify(usersRepositoryPort, times(1)).update(eq(nonExistentId), any(User.class));
//        verifyNoMoreInteractions(usersRepositoryPort, passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("Should handle null user gracefully")
//    void shouldHandleNullUserGracefully() {
//        // Arrange
//        Long validId = TestConstants.VALID_ID;
//        User nullUser = null;
//
//        // Act & Assert
//        assertThrows(NullPointerException.class,
//            () -> updateUserUseCase.update(validId, nullUser),
//            "Should throw NullPointerException when user is null"
//        );
//
//        verifyNoInteractions(usersRepositoryPort, passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("Should handle null ID gracefully")
//    void shouldHandleNullIdGracefully() {
//        // Arrange
//        Long nullId = null;
//        User userToUpdate = TestDataBuilder.createValidUser();
//        String encodedPassword = "encodedPassword123";
//
//        when(passwordEncoder.encode(TestConstants.VALID_USER_PASSWORD)).thenReturn(encodedPassword);
//        when(usersRepositoryPort.update(isNull(), any(User.class))).thenReturn(Optional.empty());
//
//        // Act
//        Optional<User> result = updateUserUseCase.update(nullId, userToUpdate);
//
//        // Assert
//        assertFalse(result.isPresent(), "Result should not be present for null ID");
//
//        verify(passwordEncoder, times(1)).encode(TestConstants.VALID_USER_PASSWORD);
//        verify(usersRepositoryPort, times(1)).update(eq(nullId), any(User.class));
//        verifyNoMoreInteractions(usersRepositoryPort, passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("Should propagate repository exceptions")
//    void shouldPropagateRepositoryExceptions() {
//        // Arrange
//        Long validId = TestConstants.VALID_ID;
//        User userToUpdate = TestDataBuilder.createValidUser();
//        RuntimeException repositoryException = new RuntimeException("Database connection failed");
//        String encodedPassword = "encodedPassword123";
//
//        when(passwordEncoder.encode(TestConstants.VALID_USER_PASSWORD)).thenReturn(encodedPassword);
//        when(usersRepositoryPort.update(eq(validId), any(User.class))).thenThrow(repositoryException);
//
//        // Act & Assert
//        RuntimeException thrownException = assertThrows(
//            RuntimeException.class,
//            () -> updateUserUseCase.update(validId, userToUpdate),
//            "Should propagate repository exceptions"
//        );
//
//        assertEquals(repositoryException.getMessage(), thrownException.getMessage(),
//            "Exception message should be preserved");
//
//        verify(passwordEncoder, times(1)).encode(TestConstants.VALID_USER_PASSWORD);
//        verify(usersRepositoryPort, times(1)).update(eq(validId), any(User.class));
//        verifyNoMoreInteractions(usersRepositoryPort, passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("Should handle valid update with specific user details")
//    void shouldHandleValidUpdateWithSpecificUserDetails() {
//        // Arrange
//        Long userId = TestConstants.VALID_ID;
//        User userToUpdate = TestDataBuilder.createValidUser();
//        User updatedUser = TestDataBuilder.createValidUser();
//        Optional<User> expectedResult = Optional.of(updatedUser);
//        String encodedPassword = "encodedPassword123";
//
//        when(passwordEncoder.encode(TestConstants.VALID_USER_PASSWORD)).thenReturn(encodedPassword);
//        when(usersRepositoryPort.update(eq(userId), any(User.class))).thenReturn(expectedResult);
//
//        // Act
//        Optional<User> result = updateUserUseCase.update(userId, userToUpdate);
//
//        // Assert
//        assertTrue(result.isPresent(), "Update result should be present");
//        User returnedUser = result.get();
//        assertNotNull(returnedUser.getNome(), "Updated user should have name");
//        assertNotNull(returnedUser.getEmail(), "Updated user should have email");
//        assertNotNull(returnedUser.getLogin(), "Updated user should have login");
//
//        verify(passwordEncoder, times(1)).encode(TestConstants.VALID_USER_PASSWORD);
//        verify(usersRepositoryPort, times(1)).update(eq(userId), any(User.class));
//        verifyNoMoreInteractions(usersRepositoryPort, passwordEncoder);
//    }
//}
