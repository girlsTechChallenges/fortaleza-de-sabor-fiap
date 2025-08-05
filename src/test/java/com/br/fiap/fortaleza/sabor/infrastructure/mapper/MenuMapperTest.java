package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.MenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateMenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu.MenuItemsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("MenuMapper Tests")
class MenuMapperTest {

    @InjectMocks
    private MenuMapper menuMapper;

    private MenuItemRequestDto menuItemRequestDto;
    private UpdateMenuItemRequestDto updateMenuItemRequestDto;
    private MenuItemsEntity menuItemsEntity;

    @BeforeEach
    void setUp() {
        menuItemRequestDto = new MenuItemRequestDto(
                "Pizza Margherita",
                "Traditional Italian pizza with tomato, mozzarella and basil",
                "29.90",
                true,
                "pizza-margherita.jpg"
        );

        updateMenuItemRequestDto = new UpdateMenuItemRequestDto(
                "Pizza Margherita Updated",
                "Updated description for traditional Italian pizza",
                "32.90",
                false,
                "pizza-margherita-updated.jpg"
        );

        menuItemsEntity = new MenuItemsEntity(
                "Pasta Carbonara",
                "Classic pasta with eggs, cheese, pancetta and pepper",
                "24.50",
                true,
                "pasta-carbonara.jpg"
        );
    }

    @Test
    @DisplayName("Should convert MenuItemRequestDto to MenuItem domain correctly")
    void shouldConvertMenuItemRequestDtoToMenuItemDomainCorrectly() {
        // Act
        MenuItem result = menuMapper.toMenuDomain(menuItemRequestDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Pizza Margherita");
        assertThat(result.getItemDescription()).isEqualTo("Traditional Italian pizza with tomato, mozzarella and basil");
        assertThat(result.getItemPrice()).isEqualTo("29.90");
        assertThat(result.getAvailability()).isTrue();
        assertThat(result.getItemImage()).isEqualTo("pizza-margherita.jpg");
    }

    @Test
    @DisplayName("Should convert UpdateMenuItemRequestDto to MenuItem domain correctly")
    void shouldConvertUpdateMenuItemRequestDtoToMenuItemDomainCorrectly() {
        // Act
        MenuItem result = menuMapper.updateToMenuDomain(updateMenuItemRequestDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Pizza Margherita Updated");
        assertThat(result.getItemDescription()).isEqualTo("Updated description for traditional Italian pizza");
        assertThat(result.getItemPrice()).isEqualTo("32.90");
        assertThat(result.getAvailability()).isFalse();
        assertThat(result.getItemImage()).isEqualTo("pizza-margherita-updated.jpg");
    }

    @Test
    @DisplayName("Should convert MenuItem domain to MenuItemsEntity correctly")
    void shouldConvertMenuItemDomainToMenuItemsEntityCorrectly() {
        // Arrange
        MenuItem menuItem = new MenuItem(
                "Lasagna",
                "Homemade lasagna with meat sauce and cheese",
                "35.90",
                true,
                "lasagna.jpg"
        );

        // Act
        MenuItemsEntity result = menuMapper.toMenuItemsEntity(menuItem);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Lasagna");
        assertThat(result.getItemDescription()).isEqualTo("Homemade lasagna with meat sauce and cheese");
        assertThat(result.getItemPrice()).isEqualTo("35.90");
        assertThat(result.getAvailability()).isTrue();
        assertThat(result.getItemImage()).isEqualTo("lasagna.jpg");
    }

    @Test
    @DisplayName("Should convert MenuItemsEntity to MenuItem domain correctly")
    void shouldConvertMenuItemsEntityToMenuItemDomainCorrectly() {
        // Act
        MenuItem result = menuMapper.toMenuItemDomain(menuItemsEntity);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Pasta Carbonara");
        assertThat(result.getItemDescription()).isEqualTo("Classic pasta with eggs, cheese, pancetta and pepper");
        assertThat(result.getItemPrice()).isEqualTo("24.50");
        assertThat(result.getAvailability()).isTrue();
        assertThat(result.getItemImage()).isEqualTo("pasta-carbonara.jpg");
    }

    @Test
    @DisplayName("Should handle null availability correctly when converting from DTO")
    void shouldHandleNullAvailabilityCorrectlyWhenConvertingFromDto() {
        // Arrange
        MenuItemRequestDto dtoWithNullAvailability = new MenuItemRequestDto(
                "Test Item",
                "Test description",
                "10.00",
                null,
                "test.jpg"
        );

        // Act
        MenuItem result = menuMapper.toMenuDomain(dtoWithNullAvailability);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAvailability()).isNull();
        assertThat(result.getName()).isEqualTo("Test Item");
    }

    @Test
    @DisplayName("Should handle empty strings correctly when converting from DTO")
    void shouldHandleEmptyStringsCorrectlyWhenConvertingFromDto() {
        // Arrange
        MenuItemRequestDto dtoWithEmptyStrings = new MenuItemRequestDto(
                "",
                "",
                "0.00",
                false,
                ""
        );

        // Act
        MenuItem result = menuMapper.toMenuDomain(dtoWithEmptyStrings);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEmpty();
        assertThat(result.getItemDescription()).isEmpty();
        assertThat(result.getItemPrice()).isEqualTo("0.00");
        assertThat(result.getAvailability()).isFalse();
        assertThat(result.getItemImage()).isEmpty();
    }

    @Test
    @DisplayName("Should handle special characters in descriptions correctly")
    void shouldHandleSpecialCharactersInDescriptionsCorrectly() {
        // Arrange
        MenuItemRequestDto dtoWithSpecialChars = new MenuItemRequestDto(
                "Açaí Bowl",
                "Delicious açaí with granola & fresh fruits - 100% natural!",
                "18.75",
                true,
                "acai-bowl.jpg"
        );

        // Act
        MenuItem result = menuMapper.toMenuDomain(dtoWithSpecialChars);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Açaí Bowl");
        assertThat(result.getItemDescription()).isEqualTo("Delicious açaí with granola & fresh fruits - 100% natural!");
        assertThat(result.getItemPrice()).isEqualTo("18.75");
        assertThat(result.getAvailability()).isTrue();
        assertThat(result.getItemImage()).isEqualTo("acai-bowl.jpg");
    }

    @Test
    @DisplayName("Should convert UpdateMenuItemRequestDto with different availability values")
    void shouldConvertUpdateMenuItemRequestDtoWithDifferentAvailabilityValues() {
        // Arrange
        UpdateMenuItemRequestDto updateWithFalseAvailability = new UpdateMenuItemRequestDto(
                "Seasonal Dish",
                "Available only during specific seasons",
                "45.00",
                false,
                "seasonal-dish.jpg"
        );

        // Act
        MenuItem result = menuMapper.updateToMenuDomain(updateWithFalseAvailability);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Seasonal Dish");
        assertThat(result.getItemDescription()).isEqualTo("Available only during specific seasons");
        assertThat(result.getItemPrice()).isEqualTo("45.00");
        assertThat(result.getAvailability()).isFalse();
        assertThat(result.getItemImage()).isEqualTo("seasonal-dish.jpg");
    }

    @Test
    @DisplayName("Should preserve all data integrity during bidirectional conversion")
    void shouldPreserveAllDataIntegrityDuringBidirectionalConversion() {
        // Arrange
        MenuItem originalMenuItem = new MenuItem(
                "Bidirectional Test",
                "Testing data integrity in conversions",
                "99.99",
                true,
                "test-image.jpg"
        );

        // Act - Convert to entity and back to domain
        MenuItemsEntity entity = menuMapper.toMenuItemsEntity(originalMenuItem);
        MenuItem convertedBack = menuMapper.toMenuItemDomain(entity);

        // Assert
        assertThat(convertedBack).isNotNull();
        assertThat(convertedBack.getName()).isEqualTo(originalMenuItem.getName());
        assertThat(convertedBack.getItemDescription()).isEqualTo(originalMenuItem.getItemDescription());
        assertThat(convertedBack.getItemPrice()).isEqualTo(originalMenuItem.getItemPrice());
        assertThat(convertedBack.getAvailability()).isEqualTo(originalMenuItem.getAvailability());
        assertThat(convertedBack.getItemImage()).isEqualTo(originalMenuItem.getItemImage());
    }
}
