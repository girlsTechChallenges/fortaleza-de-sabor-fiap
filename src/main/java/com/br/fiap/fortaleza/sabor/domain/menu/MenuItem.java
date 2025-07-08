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
    private Boolean disponibilidade;
    private String imagem_item;

//    public MenuItem(
//            String nome,
//            String descricao_item,
//            String preco_item,
//            Boolean disponibilidade,
//            String imagem_item) {
//        this.nome = nome;
//        this.descricao_item = descricao_item;
//        this.preco_item = preco_item;
//        this.disponibilidade = disponibilidade;
//        this.imagem_item = imagem_item;
//    }
}
