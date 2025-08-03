package com.br.fiap.fortaleza.sabor.domain.model.user;

import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@ToString
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
    private String tipo;
    private List<Address> address;

    public User(
            String nome,
            String email,
            String senha,
            String tipo,
            List<Address> address) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
        this.address = address;
    }
}
