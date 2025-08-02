package com.br.fiap.fortaleza.sabor.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeUserRepositoryAdapter extends JpaRepository<TypeEntity, Long> {

    Optional<TypeEntity> findByNameType(String name);
}
