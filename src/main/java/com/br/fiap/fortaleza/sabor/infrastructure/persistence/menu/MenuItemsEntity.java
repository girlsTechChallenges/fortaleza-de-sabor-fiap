package com.br.fiap.fortaleza.sabor.infrastructure.persistence.menu;


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

    private String name;

    private String itemDescription;

    private String itemPrice;

    private Boolean availability;

    private String itemImage;

    public MenuItemsEntity() {}

    public MenuItemsEntity(String name, String itemDescription, String itemPrice, Boolean availability, String itemImage) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
