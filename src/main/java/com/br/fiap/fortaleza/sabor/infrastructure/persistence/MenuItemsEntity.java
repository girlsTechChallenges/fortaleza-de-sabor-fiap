package com.br.fiap.fortaleza.sabor.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "items_cardapio")
public class MenuItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemCardapio;

    @ManyToOne
    @JoinColumn(name = "id_cardapio")
    private MenuEntity cardapio;

    private String nome;

    private String itemDescription;

    private String itemPrice;

    private Boolean availability;

    private String itemImage;

    public MenuItemsEntity() {}

    public MenuItemsEntity(Long idItemCardapio, MenuEntity cardapio, String nome, String itemDescription, String itemPrice, Boolean availability, String itemImage) {
        this.idItemCardapio = idItemCardapio;
        this.cardapio = cardapio;
        this.nome = nome;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.availability = availability;
        this.itemImage = itemImage;
    }

    public Long getIdTtemCardapio() {
        return idItemCardapio;
    }

    public void setIdItemCardapio(Long idItemCardapio) {
        this.idItemCardapio = idItemCardapio;
    }

    public MenuEntity getCardapio() {
        return cardapio;
    }

    public void setCardapio(MenuEntity cardapio) {
        this.cardapio = cardapio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}
