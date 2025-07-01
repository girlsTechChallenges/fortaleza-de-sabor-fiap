package com.br.fiap.fortaleza.sabor.domain.user;

import com.br.fiap.fortaleza.sabor.domain.address.Address;
import com.br.fiap.fortaleza.sabor.domain.enums.TypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Should create User with all parameters")
    void shouldCreateUserWithAllParameters() {
        // Given
        Long id = 1L;
        String nome = "João Silva";
        String email = "joao@email.com";
        String login = "joao123";
        String senha = "senha123";
        LocalDate dataAlteracao = LocalDate.of(2024, 1, 15);
        TypeEnum tipo = TypeEnum.CLIENTE;
        Address address = new Address("Rua A", "Centro", "Apto 1", 100, "SP", "São Paulo", "01234-567");
        List<Address> addresses = List.of(address);

        // When
        User user = new User();
        user.setId(id);
        user.setNome(nome);
        user.setEmail(email);
        user.setLogin(login);
        user.setSenha(senha);
        user.setDataAlteracao(dataAlteracao);
        user.setTipo(tipo);
        user.setAddress(addresses);

        // Then
        assertEquals(id, user.getId());
        assertEquals(nome, user.getNome());
        assertEquals(email, user.getEmail());
        assertEquals(login, user.getLogin());
        assertEquals(senha, user.getSenha());
        assertEquals(dataAlteracao, user.getDataAlteracao());
        assertEquals(tipo, user.getTipo());
        assertEquals(addresses, user.getAddress());
        assertEquals(1, user.getAddress().size());
        assertEquals(address, user.getAddress().get(0));
    }

    @Test
    @DisplayName("Should create User with constructor without id")
    void shouldCreateUserWithConstructorWithoutId() {
        // Given
        String nome = "Maria Santos";
        String email = "maria@email.com";
        String senha = "senha456";
        TypeEnum tipo = TypeEnum.DONO;
        Address address = new Address("Rua B", "Vila Nova", "Casa 2", 200, "RJ", "Rio de Janeiro", "20000-000");
        List<Address> addresses = new ArrayList<>(List.of(address));

        // When
        User user = new User(nome, email, senha, tipo, addresses);

        // Then
        assertNull(user.getId());
        assertEquals(nome, user.getNome());
        assertEquals(email, user.getEmail());
        assertEquals(senha, user.getSenha());
        assertEquals(tipo, user.getTipo());
        assertEquals(addresses, user.getAddress());
        assertEquals(LocalDate.now(), user.getDataAlteracao()); // Should be set to now
    }

    @Test
    @DisplayName("Should create User with constructor with id")
    void shouldCreateUserWithConstructorWithId() {
        // Given
        Long id = 2L;
        String nome = "Pedro Costa";
        String email = "pedro@email.com";
        String senha = "senha789";
        TypeEnum tipo = TypeEnum.CLIENTE;
        Address address = new Address("Rua C", "Jardim", "Sala 3", 300, "MG", "Belo Horizonte", "30000-000");
        List<Address> addresses = new ArrayList<>(List.of(address));

        // When
        User user = new User(id, nome, email, senha, tipo, addresses);

        // Then
        assertEquals(id, user.getId());
        assertEquals(nome, user.getNome());
        assertEquals(email, user.getEmail());
        assertEquals(senha, user.getSenha());
        assertEquals(tipo, user.getTipo());
        assertEquals(addresses, user.getAddress());
        assertEquals(LocalDate.now(), user.getDataAlteracao()); // Should be set to now
    }

    @Test
    @DisplayName("Should create User with no-args constructor")
    void shouldCreateUserWithNoArgsConstructor() {
        // When
        User user = new User();

        // Then
        assertNull(user.getId());
        assertNull(user.getNome());
        assertNull(user.getEmail());
        assertNull(user.getLogin());
        assertNull(user.getSenha());
        assertNull(user.getDataAlteracao());
        assertNull(user.getTipo());
        assertNull(user.getAddress());
    }

    @Test
    @DisplayName("Should allow modification of all fields")
    void shouldAllowModificationOfAllFields() {
        // Given
        User user = new User();
        
        Long newId = 3L;
        String newNome = "Ana Oliveira";
        String newEmail = "ana@email.com";
        String newLogin = "ana456";
        String newSenha = "newpassword";
        LocalDate newDataAlteracao = LocalDate.of(2024, 12, 25);
        TypeEnum newTipo = TypeEnum.DONO;
        Address newAddress = new Address("Rua Nova", "Centro Novo", "Apto Novo", 999, "ES", "Vitória", "29000-000");
        List<Address> newAddresses = new ArrayList<>(List.of(newAddress));

        // When
        user.setId(newId);
        user.setNome(newNome);
        user.setEmail(newEmail);
        user.setLogin(newLogin);
        user.setSenha(newSenha);
        user.setDataAlteracao(newDataAlteracao);
        user.setTipo(newTipo);
        user.setAddress(newAddresses);

        // Then
        assertEquals(newId, user.getId());
        assertEquals(newNome, user.getNome());
        assertEquals(newEmail, user.getEmail());
        assertEquals(newLogin, user.getLogin());
        assertEquals(newSenha, user.getSenha());
        assertEquals(newDataAlteracao, user.getDataAlteracao());
        assertEquals(newTipo, user.getTipo());
        assertEquals(newAddresses, user.getAddress());
    }

    @Test
    @DisplayName("Should handle multiple addresses")
    void shouldHandleMultipleAddresses() {
        // Given
        Address address1 = new Address("Rua 1", "Bairro 1", "Comp 1", 1, "SP", "São Paulo", "01000-000");
        Address address2 = new Address("Rua 2", "Bairro 2", "Comp 2", 2, "RJ", "Rio de Janeiro", "20000-000");
        Address address3 = new Address("Rua 3", "Bairro 3", "Comp 3", 3, "MG", "Belo Horizonte", "30000-000");
        List<Address> addresses = new ArrayList<>(List.of(address1, address2, address3));

        // When
        User user = new User("Teste User", "teste@email.com", "senha", TypeEnum.CLIENTE, addresses);

        // Then
        assertEquals(3, user.getAddress().size());
        assertTrue(user.getAddress().contains(address1));
        assertTrue(user.getAddress().contains(address2));
        assertTrue(user.getAddress().contains(address3));
    }

    @Test
    @DisplayName("Should handle empty address list")
    void shouldHandleEmptyAddressList() {
        // Given
        List<Address> emptyAddresses = new ArrayList<>();

        // When
        User user = new User("Empty User", "empty@email.com", "senha", TypeEnum.DONO, emptyAddresses);

        // Then
        assertNotNull(user.getAddress());
        assertTrue(user.getAddress().isEmpty());
    }

    @Test
    @DisplayName("Should allow address list modification after creation")
    void shouldAllowAddressListModificationAfterCreation() {
        // Given
        Address initialAddress = new Address("Rua Initial", "Bairro Initial", "Comp Initial", 1, "SP", "São Paulo", "01000-000");
        List<Address> addresses = new ArrayList<>(List.of(initialAddress));
        User user = new User("Test User", "test@email.com", "senha", TypeEnum.CLIENTE, addresses);

        // When
        Address newAddress = new Address("Rua Nova", "Bairro Novo", "Comp Novo", 2, "RJ", "Rio de Janeiro", "20000-000");
        user.getAddress().add(newAddress);

        // Then
        assertEquals(2, user.getAddress().size());
        assertTrue(user.getAddress().contains(initialAddress));
        assertTrue(user.getAddress().contains(newAddress));
    }
}
