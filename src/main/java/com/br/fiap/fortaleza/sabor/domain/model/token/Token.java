package com.br.fiap.fortaleza.sabor.domain.model.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String accessToken;
    private Long expiresIn;
}
