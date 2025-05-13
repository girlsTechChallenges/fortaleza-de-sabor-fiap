package com.br.fiap.fortaleza.sabor.domain.user;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;

import java.time.LocalDate;
import java.util.List;

public class User {

    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDate dataAlteracao;
    private TypeEnum tipo;
    private List<Address> address;

    public User(String nome, String email, String login, String senha, LocalDate dataAlteracao, TypeEnum tipo, List<Address> address) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.dataAlteracao = dataAlteracao;
        this.tipo = tipo;
        this.address = address;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDate dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public TypeEnum getTipo() {
        return tipo;
    }

    public void setTipo(TypeEnum tipo) {
        this.tipo = tipo;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", dataAlteracao=" + dataAlteracao +
                ", tipo=" + tipo +
                ", address=" + address +
                '}';
    }

}
