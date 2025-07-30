package com.br.fiap.fortaleza.sabor.infrastructure.persistence.user;

import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "enderecos_usuarios")
public class AddressEntity {

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

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UserEntity usuario;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private RestaurantEntity restaurante;


    public AddressEntity(String street, String district, String complement, int number, String estado, String city, String postCode) {
        this.street = street;
        this.district = district;
        this.complement = complement;
        this.number = number;
        this.state = estado;
        this.city = city;
        this.postCode = postCode;
    }

    public AddressEntity() {
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

    public void setStreet(String rua) {
        this.street = rua;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String bairro) {
        this.district = bairro;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complemento) {
        this.complement = complemento;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int numero) {
        this.number = numero;
    }

    public String getState() {
        return state;
    }

    public void setState(String estado) {
        this.state = estado;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String cidade) {
        this.city = cidade;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String cep) {
        this.postCode = cep;
    }

    public RestaurantEntity getRestaurante() {return restaurante; }

    public void setRestaurante(RestaurantEntity restaurante) { this.restaurante = restaurante; }
}