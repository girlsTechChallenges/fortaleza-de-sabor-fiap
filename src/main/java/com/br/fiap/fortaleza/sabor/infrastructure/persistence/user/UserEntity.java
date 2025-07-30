package com.br.fiap.fortaleza.sabor.infrastructure.persistence.user;

import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.restaurant.RestaurantEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String login;

    private String password;

    @Column(name = "data_alteracao")
    private LocalDate changeDate;

    @Enumerated(EnumType.STRING)
    private TypeEntityEnum type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario" , referencedColumnName = "id" )
    private List<AddressEntity> addresses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id")
    private RestaurantEntity restaurant;

    public UserEntity() {}

    public UserEntity(Long id, String name, String email, String login, String senha, LocalDate dataAlteracao, TypeEntityEnum tipo, List<AddressEntity> addresses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = senha;
        this.changeDate = dataAlteracao;
        this.type = tipo;
        this.addresses = addresses;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDate getChangeDate() {
        return changeDate;
    }

    @PrePersist
    @PreUpdate
    public void setDataAlteracao() {
        this.changeDate = LocalDate.now();
    }

    public TypeEntityEnum getType() {
        return type;
    }

    public void setType(TypeEntityEnum tipo) {
        this.type = tipo;
    }

    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressEntity> enderecos) {
        this.addresses = enderecos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }
}