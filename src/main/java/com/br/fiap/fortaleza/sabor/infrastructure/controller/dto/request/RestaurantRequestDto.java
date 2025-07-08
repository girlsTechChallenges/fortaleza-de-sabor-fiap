package com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request;

import java.util.List;

public record RestaurantRequestDto (
        String name,
        String kitchenType,
        String email,
        List<AddressDto> address,
        List<BusinessHoursDto> businessHours) {
}
