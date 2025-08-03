package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TypeUserResponse Tests")
class TypeUserResponseTest {

    @Test
    @DisplayName("Should create TypeUserResponse with all fields correctly")
    void shouldCreateTypeUserResponseWithAllFieldsCorrectly() {
        // Arrange & Act
        TypeUserResponse response = new TypeUserResponse(1L, "ADMIN");

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nameType()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Should create TypeUserResponse with null id")
    void shouldCreateTypeUserResponseWithNullId() {
        // Arrange & Act
        TypeUserResponse response = new TypeUserResponse(null, "USER");

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.id()).isNull();
        assertThat(response.nameType()).isEqualTo("USER");
    }

    @Test
    @DisplayName("Should create TypeUserResponse with null nameType")
    void shouldCreateTypeUserResponseWithNullNameType() {
        // Arrange & Act
        TypeUserResponse response = new TypeUserResponse(1L, null);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nameType()).isNull();
    }

    @Test
    @DisplayName("Should create TypeUserResponse with different user types")
    void shouldCreateTypeUserResponseWithDifferentUserTypes() {
        // Arrange & Act
        TypeUserResponse admin = new TypeUserResponse(1L, "ADMIN");
        TypeUserResponse user = new TypeUserResponse(2L, "USER");
        TypeUserResponse customer = new TypeUserResponse(3L, "CUSTOMER");
        TypeUserResponse employee = new TypeUserResponse(4L, "EMPLOYEE");

        // Assert
        assertThat(admin.nameType()).isEqualTo("ADMIN");
        assertThat(user.nameType()).isEqualTo("USER");
        assertThat(customer.nameType()).isEqualTo("CUSTOMER");
        assertThat(employee.nameType()).isEqualTo("EMPLOYEE");
    }

    @Test
    @DisplayName("Should handle special characters in nameType")
    void shouldHandleSpecialCharactersInNameType() {
        // Arrange & Act
        TypeUserResponse response = new TypeUserResponse(1L, "SUPER_ADMIN");

        // Assert
        assertThat(response.nameType()).isEqualTo("SUPER_ADMIN");
    }

    @Test
    @DisplayName("Should handle numeric characters in nameType")
    void shouldHandleNumericCharactersInNameType() {
        // Arrange & Act
        TypeUserResponse response = new TypeUserResponse(1L, "LEVEL_1_USER");

        // Assert
        assertThat(response.nameType()).isEqualTo("LEVEL_1_USER");
    }

    @Test
    @DisplayName("Should handle empty string in nameType")
    void shouldHandleEmptyStringInNameType() {
        // Arrange & Act
        TypeUserResponse response = new TypeUserResponse(1L, "");

        // Assert
        assertThat(response.nameType()).isEmpty();
    }

    @Test
    @DisplayName("Should validate record equals and hashCode behavior")
    void shouldValidateRecordEqualsAndHashCodeBehavior() {
        // Arrange
        TypeUserResponse response1 = new TypeUserResponse(1L, "ADMIN");
        TypeUserResponse response2 = new TypeUserResponse(1L, "ADMIN");
        TypeUserResponse response3 = new TypeUserResponse(2L, "ADMIN");
        TypeUserResponse response4 = new TypeUserResponse(1L, "USER");

        // Act & Assert
        assertThat(response1).isEqualTo(response2);
        assertThat(response1).isNotEqualTo(response3);
        assertThat(response1).isNotEqualTo(response4);
        assertThat(response1.hashCode()).isEqualTo(response2.hashCode());
    }

    @Test
    @DisplayName("Should validate toString method")
    void shouldValidateToStringMethod() {
        // Arrange
        TypeUserResponse response = new TypeUserResponse(1L, "ADMIN");

        // Act
        String toString = response.toString();

        // Assert
        assertThat(toString).contains("1");
        assertThat(toString).contains("ADMIN");
        assertThat(toString).contains("TypeUserResponse");
    }

    @Test
    @DisplayName("Should handle large ID values")
    void shouldHandleLargeIdValues() {
        // Arrange & Act
        TypeUserResponse response = new TypeUserResponse(Long.MAX_VALUE, "ADMIN");

        // Assert
        assertThat(response.id()).isEqualTo(Long.MAX_VALUE);
        assertThat(response.nameType()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("Should handle lowercase nameType")
    void shouldHandleLowercaseNameType() {
        // Arrange & Act
        TypeUserResponse response = new TypeUserResponse(1L, "admin");

        // Assert
        assertThat(response.nameType()).isEqualTo("admin");
    }

    @Test
    @DisplayName("Should handle mixed case nameType")
    void shouldHandleMixedCaseNameType() {
        // Arrange & Act
        TypeUserResponse response = new TypeUserResponse(1L, "AdminUser");

        // Assert
        assertThat(response.nameType()).isEqualTo("AdminUser");
    }
}
