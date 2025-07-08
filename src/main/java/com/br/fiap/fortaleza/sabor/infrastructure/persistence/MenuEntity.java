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
    private List<MenuItemsEntity> lista_items_cardapio;

    public MenuEntity() {}

    public MenuEntity(Long id_cardapio, List<MenuItemsEntity> lista_items_cardapio){
        this.id_cardapio = id_cardapio;
        this.lista_items_cardapio = lista_items_cardapio;
    }

    public Long getId_cardapio() { return id_cardapio;}

    public void setId_cardapio(Long id_cardapio) {
        this.id_cardapio = id_cardapio;
    }

    public List<MenuItemsEntity> getLista_items_cardapio(){ return lista_items_cardapio; }

    public void setLista_items_cardapio(List<MenuItemsEntity> lista_items_cardapio) {
        this.lista_items_cardapio = lista_items_cardapio;
    }
}
