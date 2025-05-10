package com.br.fiap.fortaleza.sabor.core.repositories;

import com.br.fiap.fortaleza.sabor.core.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
}