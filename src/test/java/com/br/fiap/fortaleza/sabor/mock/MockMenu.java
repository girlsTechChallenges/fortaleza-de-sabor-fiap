package com.br.fiap.fortaleza.sabor.mock;

import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.MenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.MenuItemResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu.MenuItemsEntity;

public class MockMenu {

    public static MenuItemsEntity menuEntityMockOne() {
        return new MenuItemsEntity(
                "Pizza Margherita",
                "Delicious pizza with fresh tomatoes and basil",
                "29.90",
                true,
                "https://example.com/images/margherita.png"
        );
    }

    public static MenuItemsEntity menuEntityMockTwo() {
        return new MenuItemsEntity(
                "Spaghetti Carbonara",
                "Classic Italian pasta with eggs, cheese, and bacon",
                "34.90",
                true,
                "https://example.com/images/carbonara.png"
        );
    }

    public static MenuItem menuItemMockOne() {
        return new MenuItem(
                "Pizza Margherita",
                "Delicious pizza with fresh tomatoes and basil",
                "29.90",
                true,
                "https://example.com/images/margherita.png"
        );
    }

    public static MenuItem menuItemMockTwo() {
        return new MenuItem(
                "Spaghetti Carbonara",
                "Classic Italian pasta with eggs, cheese, and bacon",
                "34.90",
                true,
                "https://example.com/images/carbonara.png"
        );
    }

    public static MenuItemResponseDto responseDtoMockOne() {
        return new MenuItemResponseDto(
                "Pizza Margherita",
                "Delicious pizza with fresh tomatoes and basil",
                "29.90",
                true,
                "https://example.com/images/margherita.png"
        );
    }

    public static MenuItemResponseDto responseDtoMockTwo() {
        return new MenuItemResponseDto(
                "Spaghetti Carbonara",
                "Classic Italian pasta with eggs, cheese, and bacon",
                "34.90",
                true,
                "https://example.com/images/carbonara.png"
        );
    }

    public static MenuItemRequestDto requestDtoMockOne() {
        return new MenuItemRequestDto(
                "Pizza Margherita",
                "Delicious pizza with fresh tomatoes and basil",
                "29.90",
                true,
                "https://example.com/images/margherita.png"
        );
    }

    public static MenuItemRequestDto requestDtoMockTwo() {
        return new MenuItemRequestDto(
                "Spaghetti Carbonara",
                "Classic Italian pasta with eggs, cheese, and bacon",
                "34.90",
                true,
                "https://example.com/images/carbonara.png"
        );
    }
}