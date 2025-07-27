package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.typeUser.TypeUser;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.typeUser.TypeUserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserEntityMapperTest {

    private UserEntityMapper mapper;

    private TypeUserEntityMapper typeMapper;

    @BeforeEach
    void setUp() {
        mapper = new UserEntityMapper(typeMapper);
    }

    @Test
    @DisplayName("Should map UserRequestDto to User.")
    void shouldMapUserRequestDtoToUser() {
        TypeUser typeUser = new TypeUser("DONO");
        UserRequestDto dto = new UserRequestDto(
                "Nome Teste", "email@test.com", "loginTeste", "senha",
                LocalDate.of(2025, 5, 21), typeUser,
                List.of(new AddressDto("Rua A", "Bairro B", "Comp", 10, "Cidade C", "Estado E", "03565000"))
        );

        User user = mapper.toUserDomain(dto);

        assertNotNull(user);
        assertEquals("Nome Teste", user.getNome());
        assertEquals(typeUser, user.getTipo());
        assertEquals("Rua A", user.getAddress().getFirst().getRua());
    }

    @Test
    @DisplayName("Should map UpdateRequestDto to User.")
    void shouldMapUpdateRequestDtoToUser() {
        TypeUser typeUser = new TypeUser("CLIENTE");
        UpdateRequestDto dto = new UpdateRequestDto(
                "Nome Update", "update@email.com", "novaSenha", typeUser,
                List.of(new AddressDto("Rua Z", "Bairro X", "Ap 101", 50, "Cidade Y", "Estado Z", "03565000"))
        );

        User user = mapper.updateToUserDomain(dto);

        assertNotNull(user);
        assertEquals("Nome Update", user.getNome());
        assertEquals("update@email.com", user.getEmail());
        assertEquals("novaSenha", user.getSenha());
        assertEquals(typeUser, user.getTipo());
    }

    @Test
    @DisplayName("Should map User to UserEntity.")
    void shouldMapUserToUserEntity() {
        // Arrange
        TypeUserEntityMapper typeMapper = mock(TypeUserEntityMapper.class);
        UserEntityMapper mapper = new UserEntityMapper(typeMapper); // injetando dependência

        TypeUser typeUser = new TypeUser("DONO");
        TypeUserEntity typeUserEntity = new TypeUserEntity("DONO");

        when(typeMapper.toTypeUserEntity(typeUser)).thenReturn(typeUserEntity); // comportamento simulado

        Address address = new Address("Rua Teste", "Bairro", "Comp", 42, "Estado", "Cidade", "00000000");
        User user = new User("Maria", "maria@test.com", "maria123", "senha", LocalDate.of(2020, 1, 1), typeUser, List.of(address));

        // Act
        UserEntity entity = mapper.toUserEntity(user);

        // Assert
        assertEquals("Maria", entity.getNome());
        assertEquals("maria@test.com", entity.getEmail());
        assertEquals("maria123", entity.getLogin());
        assertEquals("senha", entity.getSenha());
        assertEquals(typeUserEntity.getType(), entity.getTipo().getType());
        assertEquals("Rua Teste", entity.getEnderecos().get(0).getRua());
    }

    @Test
    @DisplayName("Should map User to UserResponseDto")
    void shouldMapUserToUserResponseDto() {
        TypeUser typeUser = new TypeUser("CLIENTE");
        Address address = new Address("Rua R", "Bairro B", "Comp", 10, "Estado", "Cidade", "03565000");
        User user = new User("Maria", "maria@email.com", "maria123", "123", LocalDate.now(), typeUser, List.of(address));

        UserResponseDto dto = mapper.toUserResponseDto(user);

        assertEquals("Maria", dto.nome());
        assertEquals(typeUser, dto.tipo());
        assertEquals("Rua R", dto.address().getFirst().rua());
    }

    @Test
    @DisplayName("Should map optional User to UserResponseDto.")
    void shouldMapOptionalUserToUserResponseDto() {
        TypeUser typeUser = new TypeUser("DONO");
        Address address = new Address("Rua Q", "Bairro C", "Comp", 1, "Estado", "Cidade", "03565000");
        User user = new User("Carlos", "carlos@email.com", "carlos123", "123", LocalDate.now(), typeUser, List.of(address));

        UserResponseDto dto = mapper.getUserByIdToUserResponseDto(Optional.of(user));

        assertEquals("Carlos", dto.nome());
        assertEquals("Rua Q", dto.address().getFirst().rua());
    }

    @Test
    @DisplayName("Should throw when optional User is empty.")
    void shouldThrowWhenOptionalUserIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> mapper.getUserByIdToUserResponseDto(Optional.empty()));
    }

    @Test
    @DisplayName("Should map list of Address to list of AddressEntity.")
    void shouldMapListAddressToListAddressEntity() {
        Address address = new Address("Rua S", "Bairro S", "Comp", 7, "Estado", "Cidade", "03565000");
        List<AddressEntity> entities = mapper.toAddressEntityList(List.of(address));

        assertEquals(1, entities.size());
        assertEquals("Rua S", entities.getFirst().getRua());
    }
}
