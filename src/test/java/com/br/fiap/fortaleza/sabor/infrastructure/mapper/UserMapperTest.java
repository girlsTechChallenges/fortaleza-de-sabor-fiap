package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.mock.MockUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase
public class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    @DisplayName("Class Mapper - ToUserEntity")
    public void testToUserEntity() {
        User user = MockUser.userMockOne();
        UserEntity entity = mapper.toUserEntity(user);
        assertNotNull(entity);
        assertEquals(user.getNome(), entity.getNome());
        assertEquals(user.getEmail(), entity.getEmail());
        assertEquals(user.getLogin(), entity.getLogin());
        assertEquals(user.getTipo().name(), entity.getTipo().name());
        assertEquals(user.getAddress().size(), entity.getEnderecos().size());
    }

    @Test
    @DisplayName("Class Mapper - ToUserDomain")
    public void testToUserDomain() {
        UserEntity entity = MockUser.getUserEntityMock();
        User user = mapper.toUserDomain(entity);
        assertNotNull(user);
        assertEquals(entity.getNome(), user.getNome());
        assertEquals(entity.getEmail(), user.getEmail());
        assertEquals(entity.getLogin(), user.getLogin());
        assertEquals(entity.getTipo().name(), user.getTipo().name());
        assertEquals(entity.getEnderecos().size(), user.getAddress().size());
    }

    @Test
    @DisplayName("Class Mapper - FromRequestDto")
    public void testToUserDomainFromRequestDto() {
        UserRequestDto dto = MockUser.userRequestDtoMock();
        User user = mapper.toUserDomain(dto);
        assertNotNull(user);
        assertEquals(dto.nome(), user.getNome());
        assertEquals(dto.email(), user.getEmail());
        assertEquals(dto.login(), user.getLogin());
        assertEquals(dto.tipo().name(), user.getTipo().name());
        assertEquals(dto.address().size(), user.getAddress().size());
    }

    @Test
    @DisplayName("Class Mapper - ResponseDto")
    public void testToUserResponseDto() {
        User user = MockUser.userMockOne();
        UserResponseDto dto = mapper.toUserResponseDto(user);
        assertNotNull(dto);
        assertEquals(user.getNome(), dto.nome());
        assertEquals(user.getEmail(), dto.email());
        assertEquals(user.getLogin(), dto.login());
        assertEquals(user.getTipo().name(), dto.tipo().name());
        assertEquals(user.getAddress().size(), dto.address().size());
    }
}