package com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cardapio")
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL)
    private List<MenuItemsEntity> menuItemsList;

    public MenuEntity() {}

    public MenuEntity(Long id, List<MenuItemsEntity> menuItemsList){
        this.id = id;
        this.menuItemsList = menuItemsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MenuItemsEntity> getMenuItemsList() {
        return menuItemsList;
    }

    public void setMenuItemsList(List<MenuItemsEntity> menuItemsList) {
        this.menuItemsList = menuItemsList;
    }
}
