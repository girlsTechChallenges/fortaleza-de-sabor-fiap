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
    private String name;
    private String itemDescription;
    private String itemPrice;
    private Boolean availability;
    private String itemImage;
}
