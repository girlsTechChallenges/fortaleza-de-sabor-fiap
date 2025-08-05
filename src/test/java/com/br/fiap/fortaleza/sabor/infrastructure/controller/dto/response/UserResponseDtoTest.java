package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response;

import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserResponseDto Tests")
class UserResponseDtoTest {

    @Test
    @DisplayName("Should create UserResponseDto with all fields")
    void shouldCreateUserResponseDtoWithAllFields() {
        // Arrange
        List<AddressDto> addresses = List.of(
            new AddressDto("Street das Flores", "Centro", "Apt 123", 123, "SP", "São Paulo", "01234567")
        );

        // Act
        UserResponseDto userDto = new UserResponseDto(
            "João Silva",
            "joao.silva",
            "joao@email.com",
            "ADMIN",
            addresses
        );

        // Assert
        assertThat(userDto.name()).isEqualTo("João Silva");
        assertThat(userDto.login()).isEqualTo("joao.silva");
        assertThat(userDto.email()).isEqualTo("joao@email.com");
        assertThat(userDto.type()).isEqualTo("ADMIN");
        assertThat(userDto.address()).isEqualTo(addresses);
    }

    @Test
    @DisplayName("Should create UserResponseDto with null fields")
    void shouldCreateUserResponseDtoWithNullFields() {
        // Arrange & Act
        UserResponseDto userDto = new UserResponseDto(null, null, null, null, null);

        // Assert
        assertThat(userDto.name()).isNull();
        assertThat(userDto.login()).isNull();
        assertThat(userDto.email()).isNull();
        assertThat(userDto.type()).isNull();
        assertThat(userDto.address()).isNull();
    }

    @Test
    @DisplayName("Should create UserResponseDto with empty address list")
    void shouldCreateUserResponseDtoWithEmptyAddressList() {
        // Arrange & Act
        UserResponseDto userDto = new UserResponseDto(
            "Maria Silva",
            "maria.silva",
            "maria@email.com",
            "USER",
            List.of()
        );

        // Assert
        assertThat(userDto.address()).isEmpty();
        assertThat(userDto.name()).isEqualTo("Maria Silva");
    }

    @Test
    @DisplayName("Should validate record equality")
    void shouldValidateRecordEquality() {
        // Arrange
        List<AddressDto> addresses = List.of(
            new AddressDto("Street das Flores", "Centro", "Apt 123", 123, "SP", "São Paulo", "01234567")
        );

        UserResponseDto user1 = new UserResponseDto(
            "João Silva", "joao.silva", "joao@email.com", "ADMIN", addresses
        );
        
        UserResponseDto user2 = new UserResponseDto(
            "João Silva", "joao.silva", "joao@email.com", "ADMIN", addresses
        );
        
        UserResponseDto user3 = new UserResponseDto(
            "Maria Silva", "maria.silva", "maria@email.com", "USER", addresses
        );

        // Act & Assert
        assertThat(user1).isEqualTo(user2);
        assertThat(user1).isNotEqualTo(user3);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }

    @Test
    @DisplayName("Should generate proper toString")
    void shouldGenerateProperToString() {
        // Arrange
        List<AddressDto> addresses = List.of(
            new AddressDto("Street das Flores", "Centro", "Apt 123", 123, "SP", "São Paulo", "01234567")
        );

        UserResponseDto userDto = new UserResponseDto(
            "João Silva", "joao.silva", "joao@email.com", "ADMIN", addresses
        );

        // Act
        String toString = userDto.toString();

        // Assert
        assertThat(toString).contains("João Silva");
        assertThat(toString).contains("joao.silva");
        assertThat(toString).contains("joao@email.com");
        assertThat(toString).contains("ADMIN");
    }

    @Test
    @DisplayName("Should handle empty strings")
    void shouldHandleEmptyStrings() {
        // Arrange & Act
        UserResponseDto userDto = new UserResponseDto("", "", "", "", List.of());

        // Assert
        assertThat(userDto.name()).isEmpty();
        assertThat(userDto.login()).isEmpty();
        assertThat(userDto.email()).isEmpty();
        assertThat(userDto.type()).isEmpty();
        assertThat(userDto.address()).isEmpty();
    }

    @Test
    @DisplayName("Should handle multiple addresses")
    void shouldHandleMultipleAddresses() {
        // Arrange
        List<AddressDto> addresses = List.of(
            new AddressDto("Street das Flores", "Centro", "Apt 123", 123, "SP", "São Paulo", "01234567"),
            new AddressDto("Av. Paulista", "Bela Vista", "Sala 45", 1000, "SP", "São Paulo", "01310100")
        );

        // Act
        UserResponseDto userDto = new UserResponseDto(
            "João Silva", "joao.silva", "joao@email.com", "ADMIN", addresses
        );

        // Assert
        assertThat(userDto.address()).hasSize(2);
        assertThat(userDto.address()).containsExactlyElementsOf(addresses);
    }
}
