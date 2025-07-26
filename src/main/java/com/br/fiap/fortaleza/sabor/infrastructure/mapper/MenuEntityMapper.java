package com.br.fiap.fortaleza.sabor.infrastructure.mapper;


import com.br.fiap.fortaleza.sabor.domain.menu.MenuItem;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.MenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateMenuItemRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.MenuItemResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu.MenuItemsEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MenuEntityMapper {
    public MenuItem toMenuDomain(MenuItemRequestDto menuRequestDto) {
        return new MenuItem(
                menuRequestDto.nome(),
                menuRequestDto.itemDescription(),
                menuRequestDto.itemPrice(),
                menuRequestDto.availability(),
                menuRequestDto.itemImage()
        );
    }

    public MenuItem updateToMenuDomain(UpdateMenuItemRequestDto updateUserRequestDto) {
        return new MenuItem(
                updateUserRequestDto.nome(),
                updateUserRequestDto.itemDescription(),
                updateUserRequestDto.itemPrice(),
                updateUserRequestDto.availability(),
                updateUserRequestDto.itemImage());
    }

    public MenuItemsEntity toMenuItemsEntity(MenuItem menuItem) {
        return new MenuItemsEntity(
                menuItem.getNome(),
                menuItem.getItemDescription(),
                menuItem.getItemPrice(),
                menuItem.getAvailability(),
                menuItem.getItemImage()
        );
    }

    public MenuItem toMenuItemDomain(MenuItemsEntity menuItemsEntity) {
        return new MenuItem(
                menuItemsEntity.getNome(),
                menuItemsEntity.getItemDescription(),
                menuItemsEntity.getItemPrice(),
                menuItemsEntity.getAvailability(),
                menuItemsEntity.getItemImage()
        );
    }

    public MenuItemRequestDto toMenuItemRequestDto(MenuItemsEntity entity) {
        return new MenuItemRequestDto(
                entity.getNome(),
                entity.getItemDescription(),
                entity.getItemPrice(),
                entity.getAvailability(),
                entity.getItemImage()
        );
    }

    public MenuItemResponseDto toMenuItemResponseDto(MenuItem menuItem) {
        return new MenuItemResponseDto(
                menuItem.getNome(),
                menuItem.getItemDescription(),
                menuItem.getItemPrice(),
                menuItem.getAvailability(),
                menuItem.getItemImage()
        );
    }

    public MenuItemResponseDto getMenuByIdToMenuResponseDto(Optional<MenuItem> optionalMenuItem) {
        if (optionalMenuItem.isEmpty()) {
            throw new IllegalArgumentException("Menu not found");
        }

        MenuItem menuItem = optionalMenuItem.get();

        return new MenuItemResponseDto(
                menuItem.getNome(),
                menuItem.getItemDescription(),
                menuItem.getItemPrice(),
                menuItem.getAvailability(),
                menuItem.getItemImage()
        );
    }
}
