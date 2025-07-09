package com.br.fiap.fortaleza.sabor.infrastructure.persistence;

import com.br.fiap.fortaleza.sabor.infrastructure.persistence.enums.TypeEntityEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "typeUser")
public class TypeUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idType;

    @Column(nullable = false)
    private String nameType;

    public TypeUserEntity(String nameType) {
        this.nameType = nameType;
    }

    public TypeUserEntity() {}

    public Long getId() {
        return idType;
    }

    public void setId(Long idType) {
        this.idType = idType;
    }

    public String getType() {
        return nameType;
    }

    public void setType(String nameType) {
        this.nameType = nameType;
    }
}