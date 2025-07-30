package com.br.fiap.fortaleza.sabor.domain.address;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;
    private String district;
    private String complement;
    private int number;
    private String state;
    private String city;
    private String postCode;
}
