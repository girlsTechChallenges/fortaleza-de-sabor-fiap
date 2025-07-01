package com.br.fiap.fortaleza.sabor.domain.address;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    @DisplayName("Should create Address with all parameters constructor")
    void shouldCreateAddressWithAllParametersConstructor() {
        // Given
        String rua = "Rua das Flores";
        String bairro = "Centro";
        String complemento = "Apto 101";
        int numero = 123;
        String estado = "SP";
        String cidade = "São Paulo";
        String cep = "01234-567";

        // When
        Address address = new Address(rua, bairro, complemento, numero, estado, cidade, cep);

        // Then
        assertEquals(rua, address.getRua());
        assertEquals(bairro, address.getBairro());
        assertEquals(complemento, address.getComplemento());
        assertEquals(numero, address.getNumero());
        assertEquals(estado, address.getEstado());
        assertEquals(cidade, address.getCidade());
        assertEquals(cep, address.getCep());
    }

    @Test
    @DisplayName("Should create Address with no-args constructor")
    void shouldCreateAddressWithNoArgsConstructor() {
        // When
        Address address = new Address();

        // Then
        assertNull(address.getRua());
        assertNull(address.getBairro());
        assertNull(address.getComplemento());
        assertEquals(0, address.getNumero());
        assertNull(address.getEstado());
        assertNull(address.getCidade());
        assertNull(address.getCep());
    }

    @Test
    @DisplayName("Should allow modification of all fields")
    void shouldAllowModificationOfAllFields() {
        // Given
        Address address = new Address();
        
        String newRua = "Av. Paulista";
        String newBairro = "Bela Vista";
        String newComplemento = "Conjunto 1001";
        int newNumero = 1000;
        String newEstado = "SP";
        String newCidade = "São Paulo";
        String newCep = "01310-100";

        // When
        address.setRua(newRua);
        address.setBairro(newBairro);
        address.setComplemento(newComplemento);
        address.setNumero(newNumero);
        address.setEstado(newEstado);
        address.setCidade(newCidade);
        address.setCep(newCep);

        // Then
        assertEquals(newRua, address.getRua());
        assertEquals(newBairro, address.getBairro());
        assertEquals(newComplemento, address.getComplemento());
        assertEquals(newNumero, address.getNumero());
        assertEquals(newEstado, address.getEstado());
        assertEquals(newCidade, address.getCidade());
        assertEquals(newCep, address.getCep());
    }

    @Test
    @DisplayName("Should handle null values properly")
    void shouldHandleNullValuesProperly() {
        // Given
        Address address = new Address(null, null, null, 0, null, null, null);

        // Then
        assertNull(address.getRua());
        assertNull(address.getBairro());
        assertNull(address.getComplemento());
        assertEquals(0, address.getNumero());
        assertNull(address.getEstado());
        assertNull(address.getCidade());
        assertNull(address.getCep());
    }

    @Test
    @DisplayName("Should handle empty strings")
    void shouldHandleEmptyStrings() {
        // Given
        String emptyString = "";
        Address address = new Address(emptyString, emptyString, emptyString, 0, emptyString, emptyString, emptyString);

        // Then
        assertEquals(emptyString, address.getRua());
        assertEquals(emptyString, address.getBairro());
        assertEquals(emptyString, address.getComplemento());
        assertEquals(0, address.getNumero());
        assertEquals(emptyString, address.getEstado());
        assertEquals(emptyString, address.getCidade());
        assertEquals(emptyString, address.getCep());
    }

    @Test
    @DisplayName("Should handle negative number")
    void shouldHandleNegativeNumber() {
        // Given
        int negativeNumber = -999;

        // When
        Address address = new Address("Rua", "Bairro", "Comp", negativeNumber, "Estado", "Cidade", "CEP");

        // Then
        assertEquals(negativeNumber, address.getNumero());
    }

    @Test
    @DisplayName("Should handle very large number")
    void shouldHandleVeryLargeNumber() {
        // Given
        int largeNumber = Integer.MAX_VALUE;

        // When
        Address address = new Address("Rua", "Bairro", "Comp", largeNumber, "Estado", "Cidade", "CEP");

        // Then
        assertEquals(largeNumber, address.getNumero());
    }

    @Test
    @DisplayName("Should create address with typical Brazilian address format")
    void shouldCreateAddressWithTypicalBrazilianAddressFormat() {
        // Given & When
        Address address = new Address(
                "Rua Quinze de Novembro", 
                "Centro Histórico", 
                "Sala 205", 
                1500, 
                "RS", 
                "Porto Alegre", 
                "90010-101"
        );

        // Then
        assertEquals("Rua Quinze de Novembro", address.getRua());
        assertEquals("Centro Histórico", address.getBairro());
        assertEquals("Sala 205", address.getComplemento());
        assertEquals(1500, address.getNumero());
        assertEquals("RS", address.getEstado());
        assertEquals("Porto Alegre", address.getCidade());
        assertEquals("90010-101", address.getCep());
    }

    @Test
    @DisplayName("Should create address without complement")
    void shouldCreateAddressWithoutComplement() {
        // Given & When
        Address address = new Address(
                "Rua das Acácias", 
                "Jardim Botânico", 
                null, 
                42, 
                "RJ", 
                "Rio de Janeiro", 
                "22461-000"
        );

        // Then
        assertEquals("Rua das Acácias", address.getRua());
        assertEquals("Jardim Botânico", address.getBairro());
        assertNull(address.getComplemento());
        assertEquals(42, address.getNumero());
        assertEquals("RJ", address.getEstado());
        assertEquals("Rio de Janeiro", address.getCidade());
        assertEquals("22461-000", address.getCep());
    }
}
