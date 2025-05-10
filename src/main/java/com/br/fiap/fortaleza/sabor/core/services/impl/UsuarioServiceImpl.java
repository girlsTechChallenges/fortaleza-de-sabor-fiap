package com.br.fiap.fortaleza.sabor.core.services.impl;

import com.br.fiap.fortaleza.sabor.core.entities.UsuarioEntity;
import com.br.fiap.fortaleza.sabor.core.repositories.UsuarioRepository;
import com.br.fiap.fortaleza.sabor.core.services.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<UsuarioEntity> getAll() {
        return usuarioRepository.findAll();
    }
}