package com.br.fiap.fortaleza.sabor.domain.user;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDate dataAlteracao;
    private TypeEnum tipo;
    private List<Address> address;

    public User(String nome, String email, String senha, TypeEnum tipo, List<Address> address) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.address = address;
        this.dataAlteracao = LocalDate.now();
    }

    public User(Long id, String nome, String email, String senha, TypeEnum tipo, List<Address> address) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.address = address;
        this.dataAlteracao = LocalDate.now();
    }
}
