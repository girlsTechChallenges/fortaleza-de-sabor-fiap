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

    private String name;

    @Column(name = "tipo_cozinha")
    private String typeKitchen;

    @OneToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private UserEntity owner;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<AddressEntity> address;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<BusinessHoursEntity> businessHours;

    public RestaurantEntity() {}

    public RestaurantEntity(String name, String typeKitchen, UserEntity owner, List<AddressEntity> address, List<BusinessHoursEntity> businessHours) {
        this.name = name;
        this.typeKitchen = typeKitchen;
        this.owner = owner;
        this.address = address;
        this.businessHours = businessHours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeKitchen() {
        return typeKitchen;
    }

    public void setTypeKitchen(String typeKitchen) {
        this.typeKitchen = typeKitchen;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
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
}
