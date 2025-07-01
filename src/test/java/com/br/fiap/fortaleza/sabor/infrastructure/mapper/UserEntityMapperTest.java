package com.br.fiap.fortaleza.sabor.infrastructure.mapper;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import com.br.fiap.fortaleza.sabor.domain.user.User;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.AddressEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class UserEntityMapperTest {

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Test
    @DisplayName("Should convert UserEntity to User domain")
    void shouldConvertUserEntityToUserDomain() {
        // Given
        AddressEntity addressEntity = new AddressEntity(
                "Rua Entity Test", "Bairro Entity", "Apto 301", 150,
                "SP", "São Paulo", "01234-567"
        );
        addressEntity.setId(1L);
        
        UserEntity userEntity = new UserEntity(
                "Entity User", "entity@test.com", "entityLogin", "entityPassword",
                LocalDate.of(2024, 5, 10), TypeEntityEnum.CLIENTE, List.of(addressEntity)
        );
        userEntity.setId(1L);

        // When
        User user = userEntityMapper.toUserDomain(userEntity);

        // Then
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("Entity User", user.getNome());
        assertEquals("entity@test.com", user.getEmail());
        assertEquals("entityLogin", user.getLogin());
        assertEquals("entityPassword", user.getSenha());
        assertEquals(TypeEnum.CLIENTE, user.getTipo());
        assertEquals(LocalDate.of(2024, 5, 10), user.getDataAlteracao());
        
        assertNotNull(user.getAddress());
        assertEquals(1, user.getAddress().size());
        Address address = user.getAddress().get(0);
        assertEquals("Rua Entity Test", address.getRua());
        assertEquals("Bairro Entity", address.getBairro());
        assertEquals("01234-567", address.getCep());
    }

    @Test
    @DisplayName("Should convert User domain to UserEntity")
    void shouldConvertUserDomainToUserEntity() {
        // Given
        Address address = new Address(
                "Rua Domain Test", "Bairro Domain", "Casa 5", 75,
                "RJ", "Rio de Janeiro", "20000-000"
        );
        
        User user = new User(
                "Domain User", "domain@test.com", "domainPassword",
                TypeEnum.DONO, List.of(address)
        );
        user.setId(2L);
        user.setLogin("domainLogin");
        user.setDataAlteracao(LocalDate.of(2024, 7, 15));

        // When
        UserEntity userEntity = userEntityMapper.toUserEntity(user);

        // Then
        assertNotNull(userEntity);
        assertNull(userEntity.getId()); // Should be ignored
        assertEquals("Domain User", userEntity.getNome());
        assertEquals("domain@test.com", userEntity.getEmail());
        assertEquals("domainLogin", userEntity.getLogin());
        assertEquals("domainPassword", userEntity.getSenha());
        assertEquals(TypeEntityEnum.DONO, userEntity.getTipo());
        assertEquals(LocalDate.of(2024, 7, 15), userEntity.getDataAlteracao());
        
        assertNotNull(userEntity.getEnderecos());
        assertEquals(1, userEntity.getEnderecos().size());
        AddressEntity addressEntity = userEntity.getEnderecos().get(0);
        assertEquals("Rua Domain Test", addressEntity.getRua());
        assertEquals("Bairro Domain", addressEntity.getBairro());
        assertEquals("20000-000", addressEntity.getCep());
    }

    @Test
    @DisplayName("Should convert list of UserEntity to list of User domain")
    void shouldConvertUserEntityListToUserDomainList() {
        // Given
        AddressEntity address1 = new AddressEntity(
                "Rua 1", "Bairro 1", "Comp 1", 100,
                "SP", "São Paulo", "01000-000"
        );
        
        AddressEntity address2 = new AddressEntity(
                "Rua 2", "Bairro 2", "Comp 2", 200,
                "RJ", "Rio de Janeiro", "20000-000"
        );
        
        UserEntity entity1 = new UserEntity(
                "User 1", "user1@test.com", "login1", "pass1",
                LocalDate.of(2024, 1, 1), TypeEntityEnum.CLIENTE, List.of(address1)
        );
        entity1.setId(1L);
        
        UserEntity entity2 = new UserEntity(
                "User 2", "user2@test.com", "login2", "pass2",
                LocalDate.of(2024, 2, 2), TypeEntityEnum.DONO, List.of(address2)
        );
        entity2.setId(2L);
        
        List<UserEntity> entities = List.of(entity1, entity2);

        // When
        List<User> users = userEntityMapper.toUserDomainList(entities);

        // Then
        assertNotNull(users);
        assertEquals(2, users.size());
        
        User user1 = users.get(0);
        assertEquals("User 1", user1.getNome());
        assertEquals(TypeEnum.CLIENTE, user1.getTipo());
        
        User user2 = users.get(1);
        assertEquals("User 2", user2.getNome());
        assertEquals(TypeEnum.DONO, user2.getTipo());
    }

    @Test
    @DisplayName("Should convert list of User domain to list of UserEntity")
    void shouldConvertUserDomainListToUserEntityList() {
        // Given
        Address address1 = new Address(
                "Rua 1", "Bairro 1", "Comp 1", 100,
                "SP", "São Paulo", "01000-000"
        );
        
        Address address2 = new Address(
                "Rua 2", "Bairro 2", "Comp 2", 200,
                "RJ", "Rio de Janeiro", "20000-000"
        );
        
        User user1 = new User(
                "User 1", "user1@test.com", "pass1",
                TypeEnum.CLIENTE, List.of(address1)
        );
        user1.setLogin("login1");
        
        User user2 = new User(
                "User 2", "user2@test.com", "pass2",
                TypeEnum.DONO, List.of(address2)
        );
        user2.setLogin("login2");
        
        List<User> users = List.of(user1, user2);

        // When
        List<UserEntity> entities = userEntityMapper.toUserEntityList(users);

        // Then
        assertNotNull(entities);
        assertEquals(2, entities.size());
        
        UserEntity entity1 = entities.get(0);
        assertEquals("User 1", entity1.getNome());
        assertEquals(TypeEntityEnum.CLIENTE, entity1.getTipo());
        
        UserEntity entity2 = entities.get(1);
        assertEquals("User 2", entity2.getNome());
        assertEquals(TypeEntityEnum.DONO, entity2.getTipo());
    }

    @Test
    @DisplayName("Should handle null values gracefully")
    void shouldHandleNullValues() {
        // When/Then - Should not throw exceptions for null inputs
        assertDoesNotThrow(() -> {
            User user = userEntityMapper.toUserDomain(null);
            UserEntity entity = userEntityMapper.toUserEntity(null);
            List<User> users = userEntityMapper.toUserDomainList(null);
            List<UserEntity> entities = userEntityMapper.toUserEntityList(null);
            
            assertNull(user);
            assertNull(entity);
            assertNull(users);
            assertNull(entities);
        });
    }

    @Test
    @DisplayName("Should handle empty lists")
    void shouldHandleEmptyLists() {
        // Given
        List<UserEntity> emptyEntityList = List.of();
        List<User> emptyUserList = List.of();

        // When
        List<User> users = userEntityMapper.toUserDomainList(emptyEntityList);
        List<UserEntity> entities = userEntityMapper.toUserEntityList(emptyUserList);

        // Then
        assertNotNull(users);
        assertTrue(users.isEmpty());
        
        assertNotNull(entities);
        assertTrue(entities.isEmpty());
    }
}
