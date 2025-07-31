//package com.br.fiap.fortaleza.sabor.mock;
//
//import com.br.fiap.fortaleza.sabor.domain.address.Address;
//import com.br.fiap.fortaleza.sabor.domain.user.TypeUser;
//import com.br.fiap.fortaleza.sabor.domain.user.User;
//import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
//import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserAuthDto;
//import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.TypeUserResponse;
//import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserResponseDto;
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.AddressEntity;
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public class MockUser {
//
//    public static UserEntity getUserEntityMock() {
//        return new UserEntity(
//                1L,
//                "João Silva",
//                "email",
//                "login",
//                "123",
//                LocalDate.of(2025, 5, 16),
//                new TypeEntity(1L, "DONO"),
//                List.of(
//                        new AddressEntity(
//                                "rua",
//                                "bairro",
//                                "complemento",
//                                0,
//                                "estado",
//                                "cidade",
//                                "03565000"
//                        )
//                )
//        );
//    }
//
//
//    public static User userMockOne(){
//        return new User(
//                "João Silva",
//                "email@email.com.br",
//                "login",
//                new BCryptPasswordEncoder().encode("senha1234"),
//                LocalDate.of(2025,5,16),
//                new TypeUser(1L, "DONO"),
//                List.of(new Address("rua", "bairro", "complemento",
//                        0, "estado", "cidade","03565000")));
//    }
//
//    public static User userMockTwo(){
//        return new User(
//                "Maria Oliveira",
//                "email",
//                "login",
//                "senha",
//                LocalDate.of(2025,5,16),
//                new TypeUser(1L, "CLIENTE"),
//                List.of(new Address("rua", "bairro", "complemento",
//                        0, "estado", "cidade","03565000")));
//    }
//
//    public static UserResponseDto responseDtoMockOne(){
//        return new UserResponseDto(
//                "João Silva",
//                "login",
//                "email",
//                new TypeUserResponse(1L, "DONO"),
//                List.of(new AddressDto("rua", "bairro", "complemento",
//                        0, "estado", "cidade","03565000")));
//    }
//
//    public static UserResponseDto responseDtoMockTwo(){
//        return new UserResponseDto(
//                "Maria Oliveira",
//                "login",
//                "email",
//                new TypeUserResponse(1L, "CLIENTE"),
//                List.of(new AddressDto("rua", "bairro", "complemento",
//                        0, "estado", "cidade","03565000")));
//    }
//
//    public static UserAuthDto userAuthDto(){
//        return new UserAuthDto("RyQrkn3JBIhPESrJcH4AT036ubAmlosR3HnwHT3NmR2GwCXEiSVo0FLW3z4MHJrbel4zKvFc_EySsAFr5xlu9kSFN5LKcvXcehawI4eN5jyRpSGrxe1MrcZnFsA4jDUT9Dmd4BqXtMw7hiP7eOKBxDeovxRoOMSI_4rHF_BAQQvahQ", 5L);
//    }
//
//}
