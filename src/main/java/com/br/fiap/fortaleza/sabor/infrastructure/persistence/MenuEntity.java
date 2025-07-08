package com.br.fiap.fortaleza.sabor.infrastructure.persistence;

import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cardapio")
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cardapio;

    @OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL)
    private List<MenuItemsEntity> menuItemsList;

    public MenuEntity() {}

    public MenuEntity(Long id_cardapio, List<MenuItemsEntity> menuItemsList){
        this.id_cardapio = id_cardapio;
        this.menuItemsList = menuItemsList;
    }

    public Long getId_cardapio() { return id_cardapio;}

    public void setId_cardapio(Long id_cardapio) {
        this.id_cardapio = id_cardapio;
    }

    public List<MenuItemsEntity> getMenuItemsList(){ return menuItemsList; }

    public void setMenuItemsList(List<MenuItemsEntity> menuItemsList) {
        this.menuItemsList = menuItemsList;
    }
}
