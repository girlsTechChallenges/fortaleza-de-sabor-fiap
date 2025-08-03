package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import com.br.fiap.fortaleza.sabor.domain.model.menu.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MenuDto Tests")
class MenuDtoTest {

    private MenuItem validMenuItem;

    @BeforeEach
    void setUp() {
        // Create a valid MenuItem for testing
        validMenuItem = new MenuItem();
        validMenuItem.setNome("Pizza Margherita");
        validMenuItem.setItemDescription("Pizza com molho de tomate e mussarela");
        validMenuItem.setItemPrice("25.90");
        validMenuItem.setAvailability(true);
        validMenuItem.setItemImage("pizza.jpg");
    }

    @Test
    @DisplayName("Should create MenuDto with valid menu items list")
    void shouldCreateMenuDtoWithValidMenuItemsList() {
        // Arrange & Act
        MenuDto menuDto = new MenuDto(List.of(validMenuItem));

        // Assert
        assertThat(menuDto.menuItemsList()).isNotNull();
        assertThat(menuDto.menuItemsList()).hasSize(1);
        assertThat(menuDto.menuItemsList().get(0)).isEqualTo(validMenuItem);
    }

    @Test
    @DisplayName("Should create MenuDto with null menu items list")
    void shouldCreateMenuDtoWithNullMenuItemsList() {
        // Arrange & Act
        MenuDto menuDto = new MenuDto(null);

        // Assert
        assertThat(menuDto.menuItemsList()).isNull();
    }

    @Test
    @DisplayName("Should create MenuDto with empty menu items list")
    void shouldCreateMenuDtoWithEmptyMenuItemsList() {
        // Arrange & Act
        MenuDto menuDto = new MenuDto(List.of());

        // Assert
        assertThat(menuDto.menuItemsList()).isEmpty();
    }

    @Test
    @DisplayName("Should create MenuDto with multiple valid menu items")
    void shouldCreateMenuDtoWithMultipleValidMenuItems() {
        // Arrange
        MenuItem secondItem = new MenuItem();
        secondItem.setNome("Hambúrguer");
        secondItem.setItemDescription("Hambúrguer artesanal");
        secondItem.setItemPrice("18.50");
        secondItem.setAvailability(true);
        secondItem.setItemImage("hamburger.jpg");

        // Act
        MenuDto menuDto = new MenuDto(List.of(validMenuItem, secondItem));

        // Assert
        assertThat(menuDto.menuItemsList()).hasSize(2);
        assertThat(menuDto.menuItemsList()).contains(validMenuItem, secondItem);
    }

    @Test
    @DisplayName("Should validate record properties correctly")
    void shouldValidateRecordPropertiesCorrectly() {
        // Arrange
        List<MenuItem> menuItems = List.of(validMenuItem);
        MenuDto menu1 = new MenuDto(menuItems);
        MenuDto menu2 = new MenuDto(menuItems);
        MenuDto menu3 = new MenuDto(List.of());

        // Act & Assert
        assertThat(menu1.menuItemsList()).isEqualTo(menuItems);
        assertThat(menu1.menuItemsList()).hasSize(1);
        assertThat(menu1.menuItemsList().get(0)).isEqualTo(validMenuItem);
        
        assertThat(menu1).isEqualTo(menu2);
        assertThat(menu1).isNotEqualTo(menu3);
        assertThat(menu1.hashCode()).isEqualTo(menu2.hashCode());
        assertThat(menu1.toString()).contains("MenuDto");
    }

    @Test
    @DisplayName("Should handle list modification correctly")
    void shouldHandleListModificationCorrectly() {
        // Arrange
        MenuItem anotherItem = new MenuItem();
        anotherItem.setNome("Salada Caesar");
        anotherItem.setItemDescription("Salada com frango grelhado");
        anotherItem.setItemPrice("15.90");
        anotherItem.setAvailability(true);
        anotherItem.setItemImage("caesar.jpg");

        MenuDto menuDto = new MenuDto(List.of(validMenuItem, anotherItem));

        // Act & Assert
        assertThat(menuDto.menuItemsList()).hasSize(2);
        assertThat(menuDto.menuItemsList()).contains(validMenuItem, anotherItem);
        
        // Test that the list is immutable (as it should be with record)
        assertThat(menuDto.menuItemsList()).isInstanceOf(List.class);
    }
}
