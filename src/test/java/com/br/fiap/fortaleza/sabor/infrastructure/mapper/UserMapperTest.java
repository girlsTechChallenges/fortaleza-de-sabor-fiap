package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.domain.token.Token;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.AddressDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UpdateRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserAuthDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserRequestDto;
import com.br.fiap.fortaleza.sabor.infrastructure.controller.dto.UserResponseDto;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    @DisplayName("Should convert UserRequestDto to User domain")
    void shouldConvertUserRequestDtoToUser() {
        // Given
        AddressDto addressDto = new AddressDto(
                "Rua das Flores", "Centro", "Apto 101", 123,
                "SP", "São Paulo", "01234-567"
        );
        
        UserRequestDto userRequestDto = new UserRequestDto(
                "João Silva", "joao@email.com", "joao123", "senha123",
                null, TypeEnum.CLIENTE, List.of(addressDto)
        );

        // When
        User user = userMapper.toUser(userRequestDto);

        // Then
        assertNotNull(user);
        assertNull(user.getId()); // Should be ignored
        assertEquals("João Silva", user.getNome());
        assertEquals("joao@email.com", user.getEmail());
        assertEquals("joao123", user.getLogin());
        assertEquals("senha123", user.getSenha());
        assertEquals(TypeEnum.CLIENTE, user.getTipo());
        assertEquals(LocalDate.now(), user.getDataAlteracao()); // Should be set to now
        
        assertNotNull(user.getAddress());
        assertEquals(1, user.getAddress().size());
        Address address = user.getAddress().get(0);
        assertEquals("Rua das Flores", address.getRua());
        assertEquals("Centro", address.getBairro());
        assertEquals("01234-567", address.getCep());
    }

    @Test
    @DisplayName("Should convert User domain to UserResponseDto")
    void shouldConvertUserToUserResponseDto() {
        // Given
        Address address = new Address(
                "Rua das Flores", "Centro", "Apto 101", 123,
                "SP", "São Paulo", "01234-567"
        );
        
        User user = new User(
                "Maria Silva", "maria@email.com", "senha123",
                TypeEnum.DONO, List.of(address)
        );
        user.setLogin("maria123");

        // When
        UserResponseDto responseDto = userMapper.toResponseDto(user);

        // Then
        assertNotNull(responseDto);
        assertEquals("Maria Silva", responseDto.nome());
        assertEquals("maria123", responseDto.login());
        assertEquals("maria@email.com", responseDto.email());
        assertEquals(TypeEnum.DONO, responseDto.tipo());
        
        assertNotNull(responseDto.address());
        assertEquals(1, responseDto.address().size());
        AddressDto addressDto = responseDto.address().get(0);
        assertEquals("Rua das Flores", addressDto.rua());
        assertEquals("Centro", addressDto.bairro());
        assertEquals("01234-567", addressDto.cep());
    }    @Test
    @DisplayName("Should update User from UpdateRequestDto")
    void shouldUpdateUserFromDto() {
        // Given
        Address existingAddress = new Address(
                "Rua Antiga", "Bairro Antigo", "Casa", 456,
                "RJ", "Rio de Janeiro", "20000-000"
        );
        
        User existingUser = new User(
                "Nome Antigo", "antigo@email.com", "senhaAntiga",
                TypeEnum.CLIENTE, new ArrayList<>(List.of(existingAddress))
        );
        existingUser.setLogin("loginAntigo");
        existingUser.setDataAlteracao(LocalDate.of(2024, 1, 1));

        AddressDto newAddressDto = new AddressDto(
                "Rua Nova", "Bairro Novo", "Apto 202", 789,
                "SP", "São Paulo", "01000-000"
        );
        
        UpdateRequestDto updateDto = new UpdateRequestDto(
                "Nome Novo", "novo@email.com", "senhaNova",
                TypeEnum.DONO, List.of(newAddressDto)
        );

        // When
        userMapper.updateUserFromDto(updateDto, existingUser);

        // Then
        assertEquals("Nome Novo", existingUser.getNome());
        assertEquals("novo@email.com", existingUser.getEmail());
        assertEquals("senhaNova", existingUser.getSenha());
        assertEquals(TypeEnum.DONO, existingUser.getTipo());
        assertEquals("loginAntigo", existingUser.getLogin()); // Should remain unchanged (ignored)
        assertEquals(LocalDate.now(), existingUser.getDataAlteracao()); // Should be updated to now
        
        assertNotNull(existingUser.getAddress());
        assertEquals(1, existingUser.getAddress().size());
        Address updatedAddress = existingUser.getAddress().get(0);
        assertEquals("Rua Nova", updatedAddress.getRua());
        assertEquals("Bairro Novo", updatedAddress.getBairro());
        assertEquals("01000-000", updatedAddress.getCep());
    }

    @Test
    @DisplayName("Should convert AddressDto to Address domain")
    void shouldConvertAddressDtoToAddress() {
        // Given
        AddressDto addressDto = new AddressDto(
                "Av. Paulista", "Bela Vista", "Conjunto 1001", 1000,
                "SP", "São Paulo", "01310-100"
        );

        // When
        Address address = userMapper.toAddress(addressDto);

        // Then
        assertNotNull(address);
        assertEquals("Av. Paulista", address.getRua());
        assertEquals("Bela Vista", address.getBairro());
        assertEquals("Conjunto 1001", address.getComplemento());
        assertEquals(1000, address.getNumero());
        assertEquals("SP", address.getEstado());
        assertEquals("São Paulo", address.getCidade());
        assertEquals("01310-100", address.getCep());
    }

    @Test
    @DisplayName("Should convert Address domain to AddressDto")
    void shouldConvertAddressToAddressDto() {
        // Given
        Address address = new Address(
                "Rua Oscar Freire", "Jardins", "Loja 5", 500,
                "SP", "São Paulo", "01426-001"
        );

        // When
        AddressDto addressDto = userMapper.toAddressDto(address);

        // Then
        assertNotNull(addressDto);
        assertEquals("Rua Oscar Freire", addressDto.rua());
        assertEquals("Jardins", addressDto.bairro());
        assertEquals("Loja 5", addressDto.complemento());
        assertEquals(500, addressDto.numero());
        assertEquals("SP", addressDto.estado());
        assertEquals("São Paulo", addressDto.cidade());
        assertEquals("01426-001", addressDto.cep());
    }

    @Test
    @DisplayName("Should convert Token to UserAuthDto")
    void shouldConvertTokenToUserAuthDto() {
        // Given
        Token token = new Token("abc123token", 3600L);

        // When
        UserAuthDto authDto = userMapper.toTokenResponseDto(token);

        // Then
        assertNotNull(authDto);
        assertEquals("abc123token", authDto.accessToken());
        assertEquals(3600L, authDto.expiresIn());
    }

    @Test
    @DisplayName("Should convert UserEntity to User domain")
    void shouldConvertUserEntityToUserDomain() {
        // Given
        AddressEntity addressEntity = new AddressEntity(
                "Rua Entity", "Bairro Entity", "Complemento Entity", 999,
                "RJ", "Rio de Janeiro", "22000-000"
        );
        
        UserEntity userEntity = new UserEntity(
                "Entity User", "entity@email.com", "entityLogin", "entitySenha",
                LocalDate.of(2024, 6, 15), TypeEntityEnum.DONO, List.of(addressEntity)
        );
        userEntity.setId(1L);

        // When
        User user = userMapper.toUserDomain(userEntity);

        // Then
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Entity User", user.getNome());
        assertEquals("entity@email.com", user.getEmail());
        assertEquals("entityLogin", user.getLogin());
        assertEquals("entitySenha", user.getSenha());
        assertEquals(TypeEnum.DONO, user.getTipo()); // Converted from TypeEntityEnum
        assertEquals(LocalDate.of(2024, 6, 15), user.getDataAlteracao());
        
        assertNotNull(user.getAddress());
        assertEquals(1, user.getAddress().size());
        Address address = user.getAddress().get(0);
        assertEquals("Rua Entity", address.getRua());
        assertEquals("Bairro Entity", address.getBairro());
        assertEquals("22000-000", address.getCep());
    }    @Test
    @DisplayName("Should convert User domain to UserEntity")
    void shouldConvertUserDomainToUserEntity() {
        // Given
        Address address = new Address(
                "Rua Domain", "Bairro Domain", "Complemento Domain", 888,
                "MG", "Belo Horizonte", "30000-000"
        );
        
        User user = new User(
                "Domain User", "domain@email.com", "domainSenha",
                TypeEnum.CLIENTE, new ArrayList<>(List.of(address))
        );
        user.setId(2L);
        user.setLogin("domainLogin");
        user.setDataAlteracao(LocalDate.of(2024, 8, 20));

        // When
        UserEntity userEntity = userMapper.toUserEntity(user);

        // Then
        assertNotNull(userEntity);
        assertNull(userEntity.getId()); // Should be ignored
        assertEquals("Domain User", userEntity.getNome());
        assertEquals("domain@email.com", userEntity.getEmail());
        assertEquals("domainLogin", userEntity.getLogin());
        assertEquals("domainSenha", userEntity.getSenha());
        assertEquals(TypeEntityEnum.CLIENTE, userEntity.getTipo()); // Converted from TypeEnum
        assertEquals(LocalDate.of(2024, 8, 20), userEntity.getDataAlteracao());
        
        assertNotNull(userEntity.getEnderecos());
        assertEquals(1, userEntity.getEnderecos().size());
        AddressEntity addressEntity = userEntity.getEnderecos().get(0);
        assertEquals("Rua Domain", addressEntity.getRua());
        assertEquals("Bairro Domain", addressEntity.getBairro());
        assertEquals("30000-000", addressEntity.getCep());
    }

    @Test
    @DisplayName("Should convert Address to AddressEntity ignoring id")
    void shouldConvertAddressToAddressEntity() {
        // Given
        Address address = new Address(
                "Rua Teste", "Bairro Teste", "Complemento Teste", 777,
                "PR", "Curitiba", "80000-000"
        );

        // When
        AddressEntity addressEntity = userMapper.toAddressEntity(address);

        // Then
        assertNotNull(addressEntity);
        assertNull(addressEntity.getId()); // Should be ignored
        assertEquals("Rua Teste", addressEntity.getRua());
        assertEquals("Bairro Teste", addressEntity.getBairro());
        assertEquals("Complemento Teste", addressEntity.getComplemento());
        assertEquals(777, addressEntity.getNumero());
        assertEquals("PR", addressEntity.getEstado());
        assertEquals("Curitiba", addressEntity.getCidade());
        assertEquals("80000-000", addressEntity.getCep());
    }

    @Test
    @DisplayName("Should convert AddressEntity to Address")
    void shouldConvertAddressEntityToAddress() {
        // Given
        AddressEntity addressEntity = new AddressEntity(
                "Rua Entity Test", "Bairro Entity Test", "Complemento Entity Test", 666,
                "RS", "Porto Alegre", "90000-000"
        );
        addressEntity.setId(5L);

        // When
        Address address = userMapper.toAddress(addressEntity);

        // Then
        assertNotNull(address);
        assertEquals("Rua Entity Test", address.getRua());
        assertEquals("Bairro Entity Test", address.getBairro());
        assertEquals("Complemento Entity Test", address.getComplemento());
        assertEquals(666, address.getNumero());
        assertEquals("RS", address.getEstado());
        assertEquals("Porto Alegre", address.getCidade());
        assertEquals("90000-000", address.getCep());
    }    @Test
    @DisplayName("Should handle null values gracefully")
    void shouldHandleNullValues() {
        // When/Then - Should not throw exceptions for null inputs
        assertDoesNotThrow(() -> {
            User user = userMapper.toUser(null);
            UserResponseDto responseDto = userMapper.toResponseDto(null);
            Address address1 = userMapper.toAddress((AddressDto) null);
            AddressDto addressDto = userMapper.toAddressDto(null);
            UserAuthDto authDto = userMapper.toTokenResponseDto(null);
            User userDomain = userMapper.toUserDomain(null);
            UserEntity userEntity = userMapper.toUserEntity(null);
            AddressEntity addressEntity = userMapper.toAddressEntity(null);
            Address address2 = userMapper.toAddress((AddressEntity) null);
            
            assertNull(user);
            assertNull(responseDto);
            assertNull(address1);
            assertNull(addressDto);
            assertNull(authDto);
            assertNull(userDomain);
            assertNull(userEntity);
            assertNull(addressEntity);
            assertNull(address2);
        });
    }
}
