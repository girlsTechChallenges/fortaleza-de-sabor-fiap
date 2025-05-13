package com.br.fiap.fortaleza.sabor.infrastructure.persistence;

import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "data_alteracao")
    private LocalDate dataAlteracao;

    @Enumerated(EnumType.STRING)
    private TypeEntityEnum tipo;

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> enderecos;

    private String senha;

    public UserEntity() {}

    public UserEntity(String nome, String email, String login, LocalDate dataAlteracao, TypeEntityEnum tipo, List<AddressEntity> enderecos, String senha) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.dataAlteracao = dataAlteracao;
        this.tipo = tipo;
        this.enderecos = enderecos;
        this.senha = senha;
    }

    // Getters and Setters
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

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public TypeEntityEnum getTipo() {
        return tipo;
    }

    public void setTipo(TypeEntityEnum tipo) {
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