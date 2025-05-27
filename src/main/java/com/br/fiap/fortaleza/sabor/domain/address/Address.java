package com.br.fiap.fortaleza.sabor.domain.address;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String rua;
    private String bairro;
    private String complemento;
    private int numero;
    private String estado;
    private String cidade;
    private String cep;
}
