package com.br.fiap.fortaleza.sabor.domain.userType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserType {

    private Long idType;
    private String nameType;

    public UserType(String nameType) {
        this.nameType = nameType;
    }
}
