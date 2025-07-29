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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tipo_id", referencedColumnName = "id")
    private TypeEntity tipo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario" , referencedColumnName = "id" )
    private List<AddressEntity> enderecos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id")
    private RestaurantEntity restaurante;

    public UserEntity() {}

    public UserEntity(Long id, String nome, String email, String login, String senha, LocalDate dataAlteracao, TypeEntity tipo, List<AddressEntity> enderecos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.dataAlteracao = dataAlteracao;
        this.tipo = tipo;
        this.enderecos = enderecos;
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
}