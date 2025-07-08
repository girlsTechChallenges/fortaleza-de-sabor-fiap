package com.br.fiap.fortaleza.sabor.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeUserRepository extends JpaRepository<TypeUserEntity, Long> {

    Optional<TypeUserEntity> getByNomeTipo(String nome_tipo);

}