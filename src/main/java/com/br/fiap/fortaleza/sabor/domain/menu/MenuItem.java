package com.br.fiap.fortaleza.sabor.domain.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    private String nome;
    private String descricao_item;
    private String preco_item;
    private boolean disponibilidade;
    private String imagem_item;
}
