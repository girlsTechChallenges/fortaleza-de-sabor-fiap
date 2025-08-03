package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserAuthDto Tests")
class UserAuthDtoTest {

    @Test
    @DisplayName("Should create UserAuthDto with valid token and expiration")
    void shouldCreateUserAuthDtoWithValidTokenAndExpiration() {
        // Arrange & Act
        UserAuthDto userAuthDto = new UserAuthDto("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9", 3600L);

        // Assert
        assertThat(userAuthDto).isNotNull();
        assertThat(userAuthDto.accessToken()).isEqualTo("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        assertThat(userAuthDto.expiresIn()).isEqualTo(3600L);
    }

    @Test
    @DisplayName("Should create UserAuthDto with null values")
    void shouldCreateUserAuthDtoWithNullValues() {
        // Arrange & Act
        UserAuthDto userAuthDto = new UserAuthDto(null, null);

        // Assert
        assertThat(userAuthDto).isNotNull();
        assertThat(userAuthDto.accessToken()).isNull();
        assertThat(userAuthDto.expiresIn()).isNull();
    }

    @Test
    @DisplayName("Should handle different expiration times")
    void shouldHandleDifferentExpirationTimes() {
        // Arrange & Act
        UserAuthDto shortExpiry = new UserAuthDto("token1", 300L);
        UserAuthDto longExpiry = new UserAuthDto("token2", 86400L);

        // Assert
        assertThat(shortExpiry.expiresIn()).isEqualTo(300L);
        assertThat(longExpiry.expiresIn()).isEqualTo(86400L);
    }

    @Test
    @DisplayName("Should validate record equals and hashCode behavior")
    void shouldValidateRecordEqualsAndHashCodeBehavior() {
        // Arrange
        UserAuthDto auth1 = new UserAuthDto("token", 3600L);
        UserAuthDto auth2 = new UserAuthDto("token", 3600L);
        UserAuthDto auth3 = new UserAuthDto("different-token", 3600L);

        // Act & Assert
        assertThat(auth1).isEqualTo(auth2);
        assertThat(auth1).isNotEqualTo(auth3);
        assertThat(auth1.hashCode()).isEqualTo(auth2.hashCode());
    }

    @Test
    @DisplayName("Should validate toString method")
    void shouldValidateToStringMethod() {
        // Arrange
        UserAuthDto userAuthDto = new UserAuthDto("sample-token", 1800L);

        // Act
        String toString = userAuthDto.toString();

        // Assert
        assertThat(toString).contains("sample-token");
        assertThat(toString).contains("1800");
        assertThat(toString).contains("UserAuthDto");
    }
}
