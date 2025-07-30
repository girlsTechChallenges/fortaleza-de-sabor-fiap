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

    private String name;
    private String email;
    private String login;
    private String password;
    private LocalDate changeDate;
    private TypeEnum type;
    private List<Address> address;

    public User(
            String name,
            String email,
            String password,
            TypeEnum type,
            List<Address> address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.address = address;
    }
}
