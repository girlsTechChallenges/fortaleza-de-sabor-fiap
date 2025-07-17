package com.br.fiap.fortaleza.sabor.infrastructure.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    private SecurityConfig securityConfig;

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    @BeforeEach
    void setUp() {
        publicKey = mock(RSAPublicKey.class);
        privateKey = mock(RSAPrivateKey.class);

        securityConfig = new SecurityConfig();

        // Usando reflexão para injetar os mocks nos campos privados @Value
        try {
            var publicKeyField = SecurityConfig.class.getDeclaredField("key");
            publicKeyField.setAccessible(true);
            publicKeyField.set(securityConfig, publicKey);

            var privateKeyField = SecurityConfig.class.getDeclaredField("priv");
            privateKeyField.setAccessible(true);
            privateKeyField.set(securityConfig, privateKey);
        } catch (Exception e) {
            fail("Failed to inject keys: " + e.getMessage());
        }
    }


    @Test
    void testBCryptPasswordEncoder() {
        BCryptPasswordEncoder encoder = securityConfig.bCryptPasswordEncoder();
        assertNotNull(encoder);
    }

}
