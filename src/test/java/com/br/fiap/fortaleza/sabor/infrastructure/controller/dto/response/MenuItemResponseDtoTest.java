package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MenuItemResponseDto Tests")
class MenuItemResponseDtoTest {

    @Test
    @DisplayName("Should create MenuItemResponseDto with all fields")
    void shouldCreateMenuItemResponseDtoWithAllFields() {
        // Arrange & Act
        MenuItemResponseDto menuItemDto = new MenuItemResponseDto(
            "Pizza Margherita",
            "Pizza com molho de tomate, mussarela e manjericão",
            "25.90",
            true,
            "pizza-margherita.jpg"
        );

        // Assert
        assertThat(menuItemDto.nome()).isEqualTo("Pizza Margherita");
        assertThat(menuItemDto.itemDescription()).isEqualTo("Pizza com molho de tomate, mussarela e manjericão");
        assertThat(menuItemDto.itemPrice()).isEqualTo("25.90");
        assertThat(menuItemDto.availability()).isTrue();
        assertThat(menuItemDto.itemImage()).isEqualTo("pizza-margherita.jpg");
    }

    @Test
    @DisplayName("Should create MenuItemResponseDto with null fields")
    void shouldCreateMenuItemResponseDtoWithNullFields() {
        // Arrange & Act
        MenuItemResponseDto menuItemDto = new MenuItemResponseDto(
            null,
            null,
            null,
            null,
            null
        );

        // Assert
        assertThat(menuItemDto.nome()).isNull();
        assertThat(menuItemDto.itemDescription()).isNull();
        assertThat(menuItemDto.itemPrice()).isNull();
        assertThat(menuItemDto.availability()).isNull();
        assertThat(menuItemDto.itemImage()).isNull();
    }

    @Test
    @DisplayName("Should handle availability false")
    void shouldHandleAvailabilityFalse() {
        // Arrange & Act
        MenuItemResponseDto menuItemDto = new MenuItemResponseDto(
            "Prato Indisponível",
            "Prato temporariamente indisponível",
            "30.00",
            false,
            "prato-indisponivel.jpg"
        );

        // Assert
        assertThat(menuItemDto.availability()).isFalse();
    }

    @Test
    @DisplayName("Should validate record equality")
    void shouldValidateRecordEquality() {
        // Arrange
        MenuItemResponseDto menuItem1 = new MenuItemResponseDto(
            "Pizza Margherita",
            "Pizza com molho de tomate",
            "25.90",
            true,
            "pizza.jpg"
        );
        
        MenuItemResponseDto menuItem2 = new MenuItemResponseDto(
            "Pizza Margherita",
            "Pizza com molho de tomate",
            "25.90",
            true,
            "pizza.jpg"
        );
        
        MenuItemResponseDto menuItem3 = new MenuItemResponseDto(
            "Pizza Calabresa",
            "Pizza com calabresa",
            "28.90",
            true,
            "calabresa.jpg"
        );

        // Act & Assert
        assertThat(menuItem1).isEqualTo(menuItem2);
        assertThat(menuItem1).isNotEqualTo(menuItem3);
        assertThat(menuItem1.hashCode()).isEqualTo(menuItem2.hashCode());
    }

    @Test
    @DisplayName("Should generate proper toString")
    void shouldGenerateProperToString() {
        // Arrange
        MenuItemResponseDto menuItemDto = new MenuItemResponseDto(
            "Hambúrguer",
            "Hambúrguer artesanal",
            "18.50",
            true,
            "hamburger.jpg"
        );

        // Act
        String toString = menuItemDto.toString();

        // Assert
        assertThat(toString).contains("Hambúrguer");
        assertThat(toString).contains("Hambúrguer artesanal");
        assertThat(toString).contains("18.50");
        assertThat(toString).contains("true");
        assertThat(toString).contains("hamburger.jpg");
    }

    @Test
    @DisplayName("Should handle empty strings")
    void shouldHandleEmptyStrings() {
        // Arrange & Act
        MenuItemResponseDto menuItemDto = new MenuItemResponseDto(
            "",
            "",
            "",
            false,
            ""
        );

        // Assert
        assertThat(menuItemDto.nome()).isEmpty();
        assertThat(menuItemDto.itemDescription()).isEmpty();
        assertThat(menuItemDto.itemPrice()).isEmpty();
        assertThat(menuItemDto.availability()).isFalse();
        assertThat(menuItemDto.itemImage()).isEmpty();
    }
}
