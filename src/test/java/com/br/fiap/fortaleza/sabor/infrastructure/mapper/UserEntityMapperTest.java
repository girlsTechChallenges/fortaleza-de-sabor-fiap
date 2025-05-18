package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.mock.MockUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserEntityMapperTest {

    private final UserEntityMapper mapper = new UserEntityMapper();

    @Test
    @DisplayName("Class Mapper -  ToUserEntity")
    public void testToUserEntity() {
        // GIVEN
        var user = MockUser.userMockOne();
        // WHEN
        UserEntity userEntity = mapper.toUserEntity(user);
        // THEN
        assertEquals(user.getNome(), userEntity.getNome());
        assertEquals(user.getEmail(), userEntity.getEmail());
        assertEquals(user.getLogin(), userEntity.getLogin());
        assertNotNull(userEntity.getEnderecos());
        assertEquals(user.getAddress().size(), userEntity.getEnderecos().size());
    }

    @Test
    @DisplayName("Class Mapper -  ToUserDomain")
    public void testToUserDomain() {
        // GIVEN
        var userEntity = MockUser.getUserEntityMock();
        // WHEN
        User user = mapper.toUserDomain(userEntity);
        // THEN
        assertEquals(userEntity.getNome(), user.getNome());
        assertEquals(userEntity.getEmail(), user.getEmail());
        assertEquals(userEntity.getLogin(), user.getLogin());
        assertNotNull(user.getAddress());
        assertEquals(userEntity.getEnderecos().size(), user.getAddress().size());
    }

    @Test
    @DisplayName("Class Mapper - FromRequestDto")
    public void testToUserDomainFromRequestDto() {
        // GIVEN
        var userRequestDto = MockUser.userRequestDtoMock();
        // WHEN
        User user = mapper.toUserDomain(userRequestDto);
        // THEN
        assertEquals(userRequestDto.nome(), user.getNome());
        assertEquals(userRequestDto.email(), user.getEmail());
        assertEquals(userRequestDto.login(), user.getLogin());
        assertNotNull(user.getAddress());
        assertEquals(userRequestDto.address().size(), user.getAddress().size());
    }

    @Test
    @DisplayName("Class Mapper - ResponseDto")
    public void testToUserResponseDto() {
        // GIVEN
        var user = MockUser.responseDtoMockOne();
        // WHEN
        UserResponseDto userResponseDto = mapper.toUserResponseDto(MockUser.userMockOne());
        // THEN
        assertEquals(user.nome(), userResponseDto.nome());
        assertEquals(user.email(), userResponseDto.email());
        assertEquals(user.login(), userResponseDto.login());
        assertNotNull(userResponseDto.address());
        assertEquals(user.address().size(), userResponseDto.address().size());
    }
}