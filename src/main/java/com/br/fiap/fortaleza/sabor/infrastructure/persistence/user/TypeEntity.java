package com.br.fiap.fortaleza.sabor.infrastructure.persistence.user;

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
    private TypeEntityEnum type;

    public TypeEntity(TypeEntityEnum type) {
        this.type = type;
    }

    public TypeEntity() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeEntityEnum getType() {
        return type;
    }

    public void setType(TypeEntityEnum typo) {
        this.type = typo;
    }
}