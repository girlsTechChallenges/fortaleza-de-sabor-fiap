package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response;

import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.BusinessHoursDto;

import java.util.List;

public record RestaurantFullDto(
        Long id,
        String name,
        String kitchenType,
        String email,
        String owner,
        List<AddressDto> address,
        List<BusinessHoursDto> businessHours) {
}
