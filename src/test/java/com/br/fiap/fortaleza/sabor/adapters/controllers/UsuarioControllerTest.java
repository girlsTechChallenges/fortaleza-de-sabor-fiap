package com.br.fiap.fortaleza.sabor.adapters.controllers;

import com.br.fiap.fortaleza.sabor.core.entities.UsuarioEntity;
import com.br.fiap.fortaleza.sabor.core.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Test
    void testGetAll() throws Exception {
        UsuarioService usuarioService = mock(UsuarioService.class);
        UsuarioController usuarioController = new UsuarioController(usuarioService);

        UsuarioEntity usuario1 = new UsuarioEntity();
        usuario1.setId(1L);
        usuario1.setNome("João Silva");

        UsuarioEntity usuario2 = new UsuarioEntity();
        usuario2.setId(2L);
        usuario2.setNome("Maria Oliveira");

        when(usuarioService.getAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();

        mockMvc.perform(get("/usuarios")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"nome\":\"João Silva\"},{\"id\":2,\"nome\":\"Maria Oliveira\"}]"));

        verify(usuarioService, times(1)).getAll();
    }
}