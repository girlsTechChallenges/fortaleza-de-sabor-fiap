package com.br.fiap.fortaleza.sabor.infrastructure.persistence.user;

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

    @Column(name = "change_date")
    private LocalDate changeDate;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private TypeEntity type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario" , referencedColumnName = "id" )
    private List<AddressEntity> enderecos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    public UserEntity() {}

    private UserEntity(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.login = builder.login;
        this.password = builder.password;
        this.changeDate = builder.changeDate;
        this.type = builder.type;
        this.enderecos = builder.enderecos;
        this.restaurant = builder.restaurant;
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

    public void setNome(String name) {
        this.name = name;
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

    public LocalDate getDataAlteracao() {
        return changeDate;
    }

    @PrePersist
    @PreUpdate
    public void setDataAlteracao() {
        this.changeDate = LocalDate.now();
    }

    public TypeEntity getTipo() {
        return type;
    }

    public void setTipo(TypeEntity type) {
        this.type = type;
    }

    public List<AddressEntity> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<AddressEntity> enderecos) {
        this.enderecos = enderecos;
    }

    public String getSenha() {
        return password;
    }

    public void setSenha(String password) {
        this.password = password;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String email;
        private String login;
        private String password;
        private LocalDate changeDate;
        private TypeEntity type;
        private List<AddressEntity> enderecos;
        private RestaurantEntity restaurant;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder changeDate(LocalDate changeDate) {
            this.changeDate = changeDate;
            return this;
        }

        public Builder type(TypeEntity type) {
            this.type = type;
            return this;
        }

        public Builder enderecos(List<AddressEntity> enderecos) {
            this.enderecos = enderecos;
            return this;
        }

        public Builder restaurant(RestaurantEntity restaurant) {
            this.restaurant = restaurant;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }
}