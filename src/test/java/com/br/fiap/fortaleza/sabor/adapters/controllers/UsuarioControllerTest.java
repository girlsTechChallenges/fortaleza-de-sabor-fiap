//package com.br.fiap.fortaleza.sabor.adapters.controllers;
//
//import com.br.fiap.fortaleza.sabor.application.gateways.UsersRepository;
//import com.br.fiap.fortaleza.sabor.domain.usuario.Usuario;
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserRepository;
//import com.br.fiap.fortaleza.sabor.naousar.controllers.UsuarioController;
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UsuarioEntity;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Arrays;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.mockito.Mockito.*;
//
//class UsuarioControllerTest {
//
//    @Test
//    void testGetAll() throws Exception {
//        UsersRepository userRepository = (UsersRepository) mock(UserRepository.class);
//        UsuarioController usuarioController = new UsuarioController(userRepository);
//
//        Usuario usuario1 = new UsuarioEntity();
//        usuario1.setId(1L);
//        usuario1.setNome("João Silva");
//
//        UsuarioEntity usuario2 = new UsuarioEntity();
//        usuario2.setId(2L);
//        usuario2.setNome("Maria Oliveira");
//
//        when(userRepository.getAll()).thenReturn(Arrays.asList(usuario1, usuario2));
//
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
//
//        mockMvc.perform(get("/usuarios")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json("[{\"id\":1,\"nome\":\"João Silva\"},{\"id\":2,\"nome\":\"Maria Oliveira\"}]"));
//
//        verify(userRepository, times(1)).getAll();
//    }
//}