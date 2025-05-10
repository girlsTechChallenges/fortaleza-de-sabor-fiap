package com.br.fiap.fortaleza.sabor.core.services;

import com.br.fiap.fortaleza.sabor.core.entities.UsuarioEntity;
import com.br.fiap.fortaleza.sabor.core.repositories.UsuarioRepository;
import com.br.fiap.fortaleza.sabor.core.services.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @Test
    void testGetAll() {
        UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
        UsuarioServiceImpl usuarioService = new UsuarioServiceImpl(usuarioRepository);

        UsuarioEntity usuario1 = new UsuarioEntity();
        usuario1.setId(1L);
        usuario1.setNome("João Silva");

        UsuarioEntity usuario2 = new UsuarioEntity();
        usuario2.setId(2L);
        usuario2.setNome("Maria Oliveira");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<UsuarioEntity> usuarios = usuarioService.getAll();

        assertEquals(2, usuarios.size());
        assertEquals("João Silva", usuarios.get(0).getNome());
        assertEquals("Maria Oliveira", usuarios.get(1).getNome());

        verify(usuarioRepository, times(1)).findAll();
    }
}