package com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant;

import jakarta.persistence.*;

@Entity
@Table(name = "enderecos_restaurantes")
public class AddressRestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String district;

    private String complement;

    private int number;

    private String state;

    private String city;

    private String postCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private RestaurantEntity restaurant;


    public AddressRestaurantEntity(String street, String district, String complement, int number, String state, String city, String postCode) {
        this.street = street;
        this.district = district;
        this.complement = complement;
        this.number = number;
        this.state = state;
        this.city = city;
        this.postCode = postCode;
    }

    public AddressRestaurantEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public int getNumero() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public RestaurantEntity getRestaurante() {return restaurant; }

    public void setRestaurante(RestaurantEntity restaurant) { this.restaurant = restaurant; }
}