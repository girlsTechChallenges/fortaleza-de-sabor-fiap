package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.model.address.Address;
import com.br.fiap.fortaleza.sabor.domain.model.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.request.*;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.response.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.UserEntity;
import com.br.fiap.fortaleza.sabor.utils.TestConstants;
import com.br.fiap.fortaleza.sabor.utils.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserMapper Tests")
class UserMapperTest {

    @Mock
    private TypeUserMapper typeUserMapper;

    @InjectMocks
    private UserMapper userMapper;

    private UserRequestDto userRequestDto;
    private User userDomain;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userRequestDto = createUserRequestDto();
        userDomain = TestDataBuilder.createValidUser();
        userEntity = TestDataBuilder.createValidUserEntity();
    }

    @Test
    @DisplayName("Should convert UserRequestDto to User domain successfully")
    void shouldConvertUserRequestDtoToUserDomainSuccessfully() {
        // Arrange
        String expectedTypeUser = "CLIENTE";

        // Act
        User result = userMapper.toUserDomain(userRequestDto);

        // Assert
        assertNotNull(result, "Converted user should not be null");
        assertEquals(userRequestDto.nome(), result.getNome(), "Name should match");
        assertEquals(userRequestDto.email(), result.getEmail(), "Email should match");
        assertEquals(userRequestDto.login(), result.getLogin(), "Login should match");
        assertEquals(userRequestDto.senha(), result.getSenha(), "Password should match");
        assertEquals(userRequestDto.dataAlteracao(), result.getDataAlteracao(), "Birth date should match");
        assertEquals(expectedTypeUser, result.getTipo(), "Type should match mapped value");
        
        assertNotNull(result.getAddress(), "Address should not be null");
        assertEquals(1, result.getAddress().size(), "Should have one address");
        
        Address convertedAddress = result.getAddress().get(0);
        AddressDto originalAddress = userRequestDto.address().get(0);
        assertEquals(originalAddress.rua(), convertedAddress.getRua(), "Street should match");
        assertEquals(originalAddress.bairro(), convertedAddress.getBairro(), "Neighborhood should match");
        assertEquals(originalAddress.numero(), convertedAddress.getNumero(), "Number should match");
    }

    @Test
    @DisplayName("Should convert User domain to UserEntity successfully")
    void shouldConvertUserDomainToUserEntitySuccessfully() {
        // Arrange
        TypeEntity mockTypeEntity = new TypeEntity(1L, "DONO");
        when(typeUserMapper.toTypeEntity(any())).thenReturn(mockTypeEntity);
        
        // Act
        UserEntity result = userMapper.toUserEntity(userDomain);

        // Assert
        assertNotNull(result, "Converted entity should not be null");
        assertEquals(userDomain.getNome(), result.getNome(), "Name should match");
        assertEquals(userDomain.getEmail(), result.getEmail(), "Email should match");
        assertEquals(userDomain.getLogin(), result.getLogin(), "Login should match");
        assertEquals(userDomain.getSenha(), result.getSenha(), "Password should match");
        assertEquals(userDomain.getDataAlteracao(), result.getDataAlteracao(), "Birth date should match");
        
        assertNotNull(result.getEnderecos(), "Address list should not be null");
        assertEquals(1, result.getEnderecos().size(), "Should have one address");
        
        AddressEntity convertedAddress = result.getEnderecos().get(0);
        Address originalAddress = userDomain.getAddress().get(0);
        assertEquals(originalAddress.getRua(), convertedAddress.getRua(), "Street should match");
        assertEquals(originalAddress.getBairro(), convertedAddress.getBairro(), "Neighborhood should match");
    }

    @Test
    @DisplayName("Should convert UserEntity to User domain successfully")
    void shouldConvertUserEntityToUserDomainSuccessfully() {
        // Act
        User result = userMapper.toUserDomain(userEntity);

        // Assert
        assertNotNull(result, "Converted domain should not be null");
        assertEquals(userEntity.getNome(), result.getNome(), "Name should match");
        assertEquals(userEntity.getEmail(), result.getEmail(), "Email should match");
        assertEquals(userEntity.getLogin(), result.getLogin(), "Login should match");
        assertEquals(userEntity.getSenha(), result.getSenha(), "Password should match");
        assertEquals(userEntity.getDataAlteracao(), result.getDataAlteracao(), "Birth date should match");
        assertEquals("DONO", result.getTipo(), "Type should match mapped value");
        
        assertNotNull(result.getAddress(), "Address should not be null");
    }

    @Test
    @DisplayName("Should convert User domain to UserResponseDto successfully")
    void shouldConvertUserDomainToUserResponseDtoSuccessfully() {
        // Act
        UserResponseDto result = userMapper.toUserResponseDto(userDomain);

        // Assert
        assertNotNull(result, "Converted DTO should not be null");
        assertEquals(userDomain.getNome(), result.name(), "Name should match");
        assertEquals(userDomain.getEmail(), result.email(), "Email should match");
        assertEquals(userDomain.getLogin(), result.login(), "Login should match");
        assertNotNull(result.type(), "Type should not be null");
        assertNotNull(result.address(), "Address should not be null");
    }

    private UserRequestDto createUserRequestDto() {
        return new UserRequestDto(
            TestConstants.VALID_USER_NAME,
            TestConstants.VALID_USER_EMAIL,
            TestConstants.VALID_USER_LOGIN,
            TestConstants.VALID_USER_PASSWORD,
            TestConstants.VALID_USER_DATE,
            "CLIENTE",
            List.of(new AddressDto(
                TestConstants.VALID_STREET,
                TestConstants.VALID_NEIGHBORHOOD,
                TestConstants.VALID_COMPLEMENT,
                TestConstants.VALID_NUMBER,
                TestConstants.VALID_STATE,
                TestConstants.VALID_CITY,
                TestConstants.VALID_CEP
            ))
        );
    }
}

