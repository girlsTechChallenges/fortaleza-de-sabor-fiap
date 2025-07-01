package com.br.fiap.fortaleza.sabor.domain.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    @DisplayName("Should create Token with all parameters constructor")
    void shouldCreateTokenWithAllParametersConstructor() {
        // Given
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        Long expiresIn = 3600L;

        // When
        Token token = new Token(accessToken, expiresIn);

        // Then
        assertEquals(accessToken, token.getAccessToken());
        assertEquals(expiresIn, token.getExpiresIn());
    }

    @Test
    @DisplayName("Should create Token with no-args constructor")
    void shouldCreateTokenWithNoArgsConstructor() {
        // When
        Token token = new Token();

        // Then
        assertNull(token.getAccessToken());
        assertNull(token.getExpiresIn());
    }

    @Test
    @DisplayName("Should allow modification of all fields")
    void shouldAllowModificationOfAllFields() {
        // Given
        Token token = new Token();
        
        String newAccessToken = "newAccessTokenValue123";
        Long newExpiresIn = 7200L;

        // When
        token.setAccessToken(newAccessToken);
        token.setExpiresIn(newExpiresIn);

        // Then
        assertEquals(newAccessToken, token.getAccessToken());
        assertEquals(newExpiresIn, token.getExpiresIn());
    }

    @Test
    @DisplayName("Should handle null access token")
    void shouldHandleNullAccessToken() {
        // Given & When
        Token token = new Token(null, 3600L);

        // Then
        assertNull(token.getAccessToken());
        assertEquals(3600L, token.getExpiresIn());
    }

    @Test
    @DisplayName("Should handle null expires in")
    void shouldHandleNullExpiresIn() {
        // Given & When
        Token token = new Token("someToken", null);

        // Then
        assertEquals("someToken", token.getAccessToken());
        assertNull(token.getExpiresIn());
    }

    @Test
    @DisplayName("Should handle both null values")
    void shouldHandleBothNullValues() {
        // Given & When
        Token token = new Token(null, null);

        // Then
        assertNull(token.getAccessToken());
        assertNull(token.getExpiresIn());
    }

    @Test
    @DisplayName("Should handle empty string access token")
    void shouldHandleEmptyStringAccessToken() {
        // Given & When
        Token token = new Token("", 1800L);

        // Then
        assertEquals("", token.getAccessToken());
        assertEquals(1800L, token.getExpiresIn());
    }

    @Test
    @DisplayName("Should handle zero expires in")
    void shouldHandleZeroExpiresIn() {
        // Given & When
        Token token = new Token("validToken", 0L);

        // Then
        assertEquals("validToken", token.getAccessToken());
        assertEquals(0L, token.getExpiresIn());
    }

    @Test
    @DisplayName("Should handle negative expires in")
    void shouldHandleNegativeExpiresIn() {
        // Given & When
        Token token = new Token("expiredToken", -1L);

        // Then
        assertEquals("expiredToken", token.getAccessToken());
        assertEquals(-1L, token.getExpiresIn());
    }

    @Test
    @DisplayName("Should handle very large expires in")
    void shouldHandleVeryLargeExpiresIn() {
        // Given
        Long largeExpiresIn = Long.MAX_VALUE;

        // When
        Token token = new Token("longLivedToken", largeExpiresIn);

        // Then
        assertEquals("longLivedToken", token.getAccessToken());
        assertEquals(largeExpiresIn, token.getExpiresIn());
    }

    @Test
    @DisplayName("Should handle typical JWT token")
    void shouldHandleTypicalJwtToken() {
        // Given
        String jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJCYWNrZW5kRm9ydGFsZXphU2Fib3IiLCJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNzA4NTAwMDAwLCJleHAiOjE3MDg1MDMwMDB9.signature";
        Long typicalExpiresIn = 300L; // 5 minutes

        // When
        Token token = new Token(jwtToken, typicalExpiresIn);

        // Then
        assertEquals(jwtToken, token.getAccessToken());
        assertEquals(typicalExpiresIn, token.getExpiresIn());
    }

    @Test
    @DisplayName("Should allow token update after creation")
    void shouldAllowTokenUpdateAfterCreation() {
        // Given
        Token token = new Token("initialToken", 1800L);
        
        String updatedToken = "updatedToken123";
        Long updatedExpiresIn = 3600L;

        // When
        token.setAccessToken(updatedToken);
        token.setExpiresIn(updatedExpiresIn);

        // Then
        assertEquals(updatedToken, token.getAccessToken());
        assertEquals(updatedExpiresIn, token.getExpiresIn());
    }

    @Test
    @DisplayName("Should allow setting token to null after creation")
    void shouldAllowSettingTokenToNullAfterCreation() {
        // Given
        Token token = new Token("someToken", 3600L);

        // When
        token.setAccessToken(null);
        token.setExpiresIn(null);

        // Then
        assertNull(token.getAccessToken());
        assertNull(token.getExpiresIn());
    }
}
