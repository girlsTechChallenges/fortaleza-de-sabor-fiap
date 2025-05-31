package com.br.fiap.fortaleza.sabor.mock;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserAuthDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
                                "03565000"
                        )
                )
        );
    }


    public static User userMockOne(){
        return new User(
                "João Silva",
                "email@email.com.br",
                "login",
                new BCryptPasswordEncoder().encode("senha1234"),
                LocalDate.of(2025,5,16),
                TypeEnum.DONO,
                List.of(new Address("rua", "bairro", "complemento",
                        0, "estado", "cidade","03565000")));
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
                        0, "estado", "cidade","03565000")));
    }

    public static UserResponseDto responseDtoMockOne(){
        return new UserResponseDto(
                "João Silva",
                "login",
                "email",
                TypeEnum.DONO,
                List.of(new AddressDto("rua", "bairro", "complemento",
                        0, "estado", "cidade","03565000")));
    }

    public static UserResponseDto responseDtoMockTwo(){
        return new UserResponseDto(
                "Maria Oliveira",
                "login",
                "email",
                TypeEnum.CLIENTE,
                List.of(new AddressDto("rua", "bairro", "complemento",
                        0, "estado", "cidade","03565000")));
    }

    public static UserAuthDto userAuthDto(){
        return new UserAuthDto("RyQrkn3JBIhPESrJcH4AT036ubAmlosR3HnwHT3NmR2GwCXEiSVo0FLW3z4MHJrbel4zKvFc_EySsAFr5xlu9kSFN5LKcvXcehawI4eN5jyRpSGrxe1MrcZnFsA4jDUT9Dmd4BqXtMw7hiP7eOKBxDeovxRoOMSI_4rHF_BAQQvahQ", 5L);
    }

}
