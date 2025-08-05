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
    private String name;
    private String email;
    private String login;
    private String password;
    private LocalDate changeDate;
    private String type;
    private List<Address> address;

    public User(
            String name,
            String email,
            String password,
            String type,
            List<Address> address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.address = address;
    }
}
