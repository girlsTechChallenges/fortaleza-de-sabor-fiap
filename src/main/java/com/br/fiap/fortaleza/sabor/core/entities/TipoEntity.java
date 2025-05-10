package com.br.fiap.fortaleza.sabor.core.entities;

import com.br.fiap.fortaleza.sabor.core.entities.enums.TipoEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "tipos")
public class TipoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEnum tipo;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoEnum tipo) {
        this.tipo = tipo;
    }
}