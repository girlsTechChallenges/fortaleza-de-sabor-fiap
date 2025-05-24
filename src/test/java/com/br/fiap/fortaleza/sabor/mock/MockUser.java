package com.br.fiap.fortaleza.sabor.mock;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;

import java.time.LocalDate;
import java.util.List;

public class MockUser {

    public static UserEntity getUserEntityMock() {
        return new UserEntity(
                "João Silva",
                "email",
                "login",
                "123",
                LocalDate.of(2025, 5, 16),
                TypeEntityEnum.CLIENTE,
                List.of(
                        new AddressEntity(
                                "rua",
                                "bairro",
                                "complemento",
                                0,
                                "estado",
                                "cidade",
                                123456
                        )
                )
        );
    }


    public static User userMockOne(){
        return new User(
                "João Silva",
                "email",
                "login",
                "senha",
                LocalDate.of(2025,5,16),
                TypeEnum.DONO,
                List.of(new Address("rua", "bairro", "complemento",
                        0, "estado", "cidade",0)));
    }

    public static User userMockTwo(){
        return new User(
                "Maria Oliveira",
                "email",
                "login",
                "senha",
                LocalDate.of(2025,5,16),
                TypeEnum.CLIENTE,
                List.of(new Address("rua", "bairro", "complemento",
                        0, "estado", "cidade",0)));
    }

    public static UserResponseDto responseDtoMockOne(){
        return new UserResponseDto(
                "João Silva",
                "login",
                "email",
                TypeEnum.DONO,
                List.of(new AddressDto("rua", "bairro", "complemento",
                        0, "estado", "cidade",0)));
    }

    public static UserResponseDto responseDtoMockTwo(){
        return new UserResponseDto(
                "Maria Oliveira",
                "login",
                "email",
                TypeEnum.CLIENTE,
                List.of(new AddressDto("rua", "bairro", "complemento",
                        0, "estado", "cidade",0)));
    }
}
