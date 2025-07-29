package com.br.fiap.fortaleza.sabor.domain.user;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
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

    private String nome;
    private String email;
    private String login;
    private String senha;
    private LocalDate dataAlteracao;
    private TypeUser tipo;
    private List<Address> address;

    public User(
            String nome,
            String email,
            String senha,
            TypeUser tipo,
            List<Address> address) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.address = address;
    }
}
