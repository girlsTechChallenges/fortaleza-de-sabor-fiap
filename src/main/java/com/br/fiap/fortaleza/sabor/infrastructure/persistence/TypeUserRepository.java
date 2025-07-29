package com.br.fiap.fortaleza.sabor.infrastructure.persistence;

import com.br.fiap.fortaleza.sabor.infrastructure.persistence.user.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeUserRepository  extends JpaRepository<TypeEntity, Long> {
}
