package com.br.fiap.fortaleza.sabor.domain.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private List<MenuItem> lista_items_cardapio;
}
