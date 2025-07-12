package com.br.fiap.fortaleza.sabor.domain.restaurant;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Restaurant {

    private Long id;
    private String name;
    private String kitchenType;
    private String email;
    private String owner;
    private List<Address> address;
    private List<BusinessHours> businessHours;

}
