//package com.br.fiap.fortaleza.sabor.core.repositories;
//
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UsuarioEntity;
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TipoEnum;
//import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataJpaTest
//class UsersRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    void testFindAll() {
//        userRepository.deleteAllInBatch();
//
//        UsuarioEntity usuario1 = new UsuarioEntity();
//        usuario1.setId(null);
//        usuario1.setNome("João Silva");
//        usuario1.setEmail("joao.silva@example.com");
//        usuario1.setLogin("joaosilva");
//        usuario1.setSenha("senha123");
//        usuario1.setDataAlteracao(LocalDate.now());
//        usuario1.setTipo(TipoEnum.DONO);
//
//        UsuarioEntity usuario2 = new UsuarioEntity();
//        usuario2.setId(null);
//        usuario2.setNome("Maria Oliveira");
//        usuario2.setEmail("maria.oliveira@example.com");
//        usuario2.setLogin("mariaoliveira");
//        usuario2.setSenha("senha456");
//        usuario2.setDataAlteracao(LocalDate.now());
//        usuario2.setTipo(TipoEnum.CLIENTE);
//
//        usuario1 = new UsuarioEntity();
//        usuario1.setId(null);
//        usuario1.setNome("João Silva");
//        usuario1.setEmail("joao.silva@example.com");
//        usuario1.setLogin("joaosilva");
//        usuario1.setSenha("senha123");
//        usuario1.setDataAlteracao(LocalDate.now());
//        usuario1.setTipo(TipoEnum.DONO);
//
//        usuario2 = new UsuarioEntity();
//        usuario2.setId(null);
//        usuario2.setNome("Maria Oliveira");
//        usuario2.setEmail("maria.oliveira@example.com");
//        usuario2.setLogin("mariaoliveira");
//        usuario2.setSenha("senha456");
//        usuario2.setDataAlteracao(LocalDate.now());
//        usuario2.setTipo(TipoEnum.CLIENTE);
//
//        userRepository.save(usuario1);
//        userRepository.save(usuario2);
//
//        List<UsuarioEntity> usuarios = userRepository.findAll();
//
//        assertEquals(2, usuarios.size());
//        assertEquals("João Silva", usuarios.get(0).getNome());
//        assertEquals("Maria Oliveira", usuarios.get(1).getNome());
//    }
//}