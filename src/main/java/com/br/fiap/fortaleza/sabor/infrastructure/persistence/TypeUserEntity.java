package com.br.fiap.fortaleza.sabor.infrastructure.persistence;

import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "typeUser")
public class TypeUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipos;

    @Column(nullable = false)
    private String nomeTipo;

    public TypeUserEntity(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }

    public TypeUserEntity() {}

    // Getters and Setters
    public Long getId() {
        return idTipos;
    }

    public void setId(Long idTipos) {
        this.idTipos = idTipos;
    }

    public String getTipo() {
        return nomeTipo;
    }

    public void setTipo(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }
}