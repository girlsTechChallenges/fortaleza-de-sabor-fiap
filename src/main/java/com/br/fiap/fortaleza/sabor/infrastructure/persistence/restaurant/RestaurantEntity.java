package com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant;

import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "restaurantes")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String name;

    @Column(name = "tipo_cozinha", nullable = false)
    private String kitchenType;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> address;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BusinessHoursEntity> businessHours;

    @OneToOne(optional = false)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private UserEntity user;

    public RestaurantEntity(String name, String kitchenType, List<AddressEntity> address, List<BusinessHoursEntity> businessHours, UserEntity user) {
        this.name = name;
        this.kitchenType = kitchenType;
        this.address = address;
        this.businessHours = businessHours;
        this.user = user;
    }

    public RestaurantEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }

    public List<AddressEntity> getAddress() {
        return address;
    }

    public void setAddress(List<AddressEntity> address) {
        this.address = address;
    }

    public List<BusinessHoursEntity> getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(List<BusinessHoursEntity> businessHours) {
        this.businessHours = businessHours;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
