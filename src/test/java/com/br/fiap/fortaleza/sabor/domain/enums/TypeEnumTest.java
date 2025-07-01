package com.br.fiap.fortaleza.sabor.domain.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeEnumTest {

    @Test
    @DisplayName("Should have DONO enum value")
    void shouldHaveDonoEnumValue() {
        // When & Then
        TypeEnum dono = TypeEnum.DONO;
        
        assertNotNull(dono);
        assertEquals("DONO", dono.name());
        assertEquals("DONO", dono.toString());
    }

    @Test
    @DisplayName("Should have CLIENTE enum value")
    void shouldHaveClienteEnumValue() {
        // When & Then
        TypeEnum cliente = TypeEnum.CLIENTE;
        
        assertNotNull(cliente);
        assertEquals("CLIENTE", cliente.name());
        assertEquals("CLIENTE", cliente.toString());
    }

    @Test
    @DisplayName("Should have exactly two enum values")
    void shouldHaveExactlyTwoEnumValues() {
        // When
        TypeEnum[] values = TypeEnum.values();

        // Then
        assertEquals(2, values.length);
        assertTrue(containsValue(values, TypeEnum.DONO));
        assertTrue(containsValue(values, TypeEnum.CLIENTE));
    }

    @Test
    @DisplayName("Should be able to get enum value by name")
    void shouldBeAbleToGetEnumValueByName() {
        // When
        TypeEnum dono = TypeEnum.valueOf("DONO");
        TypeEnum cliente = TypeEnum.valueOf("CLIENTE");

        // Then
        assertEquals(TypeEnum.DONO, dono);
        assertEquals(TypeEnum.CLIENTE, cliente);
    }

    @Test
    @DisplayName("Should throw exception for invalid enum name")
    void shouldThrowExceptionForInvalidEnumName() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            TypeEnum.valueOf("INVALID");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            TypeEnum.valueOf("ADMIN");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            TypeEnum.valueOf("USER");
        });
    }

    @Test
    @DisplayName("Should throw exception for null enum name")
    void shouldThrowExceptionForNullEnumName() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            TypeEnum.valueOf(null);
        });
    }

    @Test
    @DisplayName("Should throw exception for empty enum name")
    void shouldThrowExceptionForEmptyEnumName() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            TypeEnum.valueOf("");
        });
    }

    @Test
    @DisplayName("Should be case sensitive")
    void shouldBeCaseSensitive() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            TypeEnum.valueOf("dono");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            TypeEnum.valueOf("cliente");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            TypeEnum.valueOf("Dono");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            TypeEnum.valueOf("Cliente");
        });
    }

    @Test
    @DisplayName("Should support enum comparison")
    void shouldSupportEnumComparison() {
        // Given
        TypeEnum dono1 = TypeEnum.DONO;
        TypeEnum dono2 = TypeEnum.valueOf("DONO");
        TypeEnum cliente = TypeEnum.CLIENTE;

        // When & Then
        assertEquals(dono1, dono2);
        assertNotEquals(dono1, cliente);
        assertNotEquals(dono2, cliente);
        
        assertTrue(dono1 == dono2); // Same enum instance
        assertFalse(dono1 == cliente);
    }

    @Test
    @DisplayName("Should support switch statements")
    void shouldSupportSwitchStatements() {
        // Given & When & Then
        for (TypeEnum type : TypeEnum.values()) {
            String description = switch (type) {
                case DONO -> "Proprietário do estabelecimento";
                case CLIENTE -> "Cliente do estabelecimento";
            };
            
            assertNotNull(description);
            assertFalse(description.isEmpty());
        }
    }

    @Test
    @DisplayName("Should maintain enum ordering")
    void shouldMaintainEnumOrdering() {
        // When
        TypeEnum[] values = TypeEnum.values();

        // Then
        assertEquals(TypeEnum.DONO, values[0]);
        assertEquals(TypeEnum.CLIENTE, values[1]);
        
        // Test ordinal values
        assertEquals(0, TypeEnum.DONO.ordinal());
        assertEquals(1, TypeEnum.CLIENTE.ordinal());
    }

    @Test
    @DisplayName("Should work with collections")
    void shouldWorkWithCollections() {
        // Given
        java.util.List<TypeEnum> typeList = java.util.List.of(TypeEnum.DONO, TypeEnum.CLIENTE);
        java.util.Set<TypeEnum> typeSet = java.util.Set.of(TypeEnum.DONO, TypeEnum.CLIENTE);

        // When & Then
        assertTrue(typeList.contains(TypeEnum.DONO));
        assertTrue(typeList.contains(TypeEnum.CLIENTE));
        assertEquals(2, typeList.size());
        
        assertTrue(typeSet.contains(TypeEnum.DONO));
        assertTrue(typeSet.contains(TypeEnum.CLIENTE));
        assertEquals(2, typeSet.size());
    }

    private boolean containsValue(TypeEnum[] values, TypeEnum target) {
        for (TypeEnum value : values) {
            if (value == target) {
                return true;
            }
        }
        return false;
    }
}
