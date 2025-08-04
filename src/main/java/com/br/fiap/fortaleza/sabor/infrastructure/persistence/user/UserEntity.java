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

    private String nome;

    private String email;

    private String login;

    private String senha;

    @Column(name = "data_alteracao")
    private LocalDate dataAlteracao;

    @ManyToOne
    @JoinColumn(name = "tipo_id", referencedColumnName = "id")
    private TypeEntity tipo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario" , referencedColumnName = "id" )
    private List<AddressEntity> enderecos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id")
    private RestaurantEntity restaurante;

    public UserEntity() {}

    private UserEntity(Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.email = builder.email;
        this.login = builder.login;
        this.senha = builder.senha;
        this.dataAlteracao = builder.dataAlteracao;
        this.tipo = builder.tipo;
        this.enderecos = builder.enderecos;
        this.restaurante = builder.restaurante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        return dataAlteracao;
    }

    @PrePersist
    @PreUpdate
    public void setDataAlteracao() {
        this.dataAlteracao = LocalDate.now();
    }

    public TypeEntity getTipo() {
        return tipo;
    }

    public void setTipo(TypeEntity tipo) {
        this.tipo = tipo;
    }

    public List<AddressEntity> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<AddressEntity> enderecos) {
        this.enderecos = enderecos;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public static class Builder {
        private Long id;
        private String nome;
        private String email;
        private String login;
        private String senha;
        private LocalDate dataAlteracao;
        private TypeEntity tipo;
        private List<AddressEntity> enderecos;
        private RestaurantEntity restaurante;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
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

        public Builder senha(String senha) {
            this.senha = senha;
            return this;
        }

        public Builder dataAlteracao(LocalDate dataAlteracao) {
            this.dataAlteracao = dataAlteracao;
            return this;
        }

        public Builder tipo(TypeEntity tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder enderecos(List<AddressEntity> enderecos) {
            this.enderecos = enderecos;
            return this;
        }

        public Builder restaurante(RestaurantEntity restaurante) {
            this.restaurante = restaurante;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }
}