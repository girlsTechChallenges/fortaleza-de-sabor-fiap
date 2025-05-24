package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserEntityMapperTest {

    private UserEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserEntityMapper();
    }

    @Test
    @DisplayName("Should map UserRequestDto to User.")
    void shouldMapUserRequestDtoToUser() {
        UserRequestDto dto = new UserRequestDto(
                "Nome Teste", "email@test.com", "loginTeste", "senha",
                LocalDate.of(2025, 5, 21), TypeEnum.DONO,
                List.of(new AddressDto("Rua A", "Bairro B", "Comp", 10, "Cidade C", "Estado E", 123456))
        );

        User user = mapper.toUserDomain(dto);

        assertNotNull(user);
        assertEquals("Nome Teste", user.getNome());
        assertEquals(TypeEnum.DONO, user.getTipo());
        assertEquals("Rua A", user.getAddress().getFirst().getRua());
    }

    @Test
    @DisplayName("Should map UpdateRequestDto to User.")
    void shouldMapUpdateRequestDtoToUser() {
        UpdateRequestDto dto = new UpdateRequestDto(
                "Nome Update", "update@email.com", "novaSenha", TypeEnum.CLIENTE,
                List.of(new AddressDto("Rua Z", "Bairro X", "Ap 101", 50, "Cidade Y", "Estado Z", 654321))
        );

        User user = mapper.updateToUserDomain(dto);

        assertNotNull(user);
        assertEquals("Nome Update", user.getNome());
        assertEquals("update@email.com", user.getEmail());
        assertEquals("novaSenha", user.getSenha());
        assertEquals(TypeEnum.CLIENTE, user.getTipo());
    }

    @Test
    @DisplayName("Should map User to UserEntity")
    void shouldMapUserToUserEntity() {
        Address address = new Address("Rua B", "Bairro C", "Comp", 99, "Estado F", "Cidade D", 112233);
        User user = new User("Nome", "email", "login", "senha", LocalDate.now(), TypeEnum.DONO, List.of(address));

        UserEntity entity = mapper.toUserEntity(user);

        assertEquals(user.getNome(), entity.getNome());
        assertEquals(TypeEntityEnum.DONO, entity.getTipo());
        assertEquals("Rua B", entity.getEnderecos().getFirst().getRua());
    }

    @Test
    @DisplayName("Should map UserEntity to User.")
    void shouldMapUserEntityToUser() {
        AddressEntity address = new AddressEntity("Rua 1", "Bairro 2", "Comp", 1, "Estado", "Cidade", 101010);
        UserEntity entity = new UserEntity("João", "joao@email.com", "joao123", "123", LocalDate.now(), TypeEntityEnum.CLIENTE, List.of(address));

        User user = mapper.toUserDomain(entity);

        assertEquals("João", user.getNome());
        assertEquals(TypeEnum.CLIENTE, user.getTipo());
        assertEquals("Rua 1", user.getAddress().getFirst().getRua());
    }

    @Test
    @DisplayName("Should map User to UserResponseDto")
    void shouldMapUserToUserResponseDto() {
        Address address = new Address("Rua R", "Bairro B", "Comp", 10, "Estado", "Cidade", 123000);
        User user = new User("Maria", "maria@email.com", "maria123", "123", LocalDate.now(), TypeEnum.CLIENTE, List.of(address));

        UserResponseDto dto = mapper.toUserResponseDto(user);

        assertEquals("Maria", dto.nome());
        assertEquals(TypeEnum.CLIENTE, dto.tipo());
        assertEquals("Rua R", dto.address().getFirst().rua());
    }

    @Test
    @DisplayName("Should map optional User to UserResponseDto.")
    void shouldMapOptionalUserToUserResponseDto() {
        Address address = new Address("Rua Q", "Bairro C", "Comp", 1, "Estado", "Cidade", 1010);
        User user = new User("Carlos", "carlos@email.com", "carlos123", "123", LocalDate.now(), TypeEnum.DONO, List.of(address));

        UserResponseDto dto = mapper.updateToUserResponseDto(Optional.of(user));

        assertEquals("Carlos", dto.nome());
        assertEquals("Rua Q", dto.address().getFirst().rua());
    }

    @Test
    @DisplayName("Should throw when optional User is empty.")
    void shouldThrowWhenOptionalUserIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> mapper.updateToUserResponseDto(Optional.empty()));
    }

    @Test
    @DisplayName("Should map list of Address to list of AddressEntity.")
    void shouldMapListAddressToListAddressEntity() {
        Address address = new Address("Rua S", "Bairro S", "Comp", 7, "Estado", "Cidade", 444444);
        List<AddressEntity> entities = mapper.toAddressEntityList(List.of(address));

        assertEquals(1, entities.size());
        assertEquals("Rua S", entities.getFirst().getRua());
    }
}
