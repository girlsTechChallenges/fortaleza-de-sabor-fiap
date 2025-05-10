package com.br.fiap.fortaleza.sabor.core.services;

import com.br.fiap.fortaleza.sabor.core.entities.UsuarioEntity;

import java.util.List;

public interface UsuarioService {
    List<UsuarioEntity> getAll();
}