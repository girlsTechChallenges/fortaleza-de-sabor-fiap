package com.br.fiap.fortaleza.sabor.infrastructure.persistence;

import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "tipos")
public class TypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeEntityEnum tipo;

    public TypeEntity(TypeEntityEnum tipo) {
        this.tipo = tipo;
    }

    public TypeEntity() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeEntityEnum getTipo() {
        return tipo;
    }

    public void setTipo(TypeEntityEnum tipo) {
        this.tipo = tipo;
    }
}