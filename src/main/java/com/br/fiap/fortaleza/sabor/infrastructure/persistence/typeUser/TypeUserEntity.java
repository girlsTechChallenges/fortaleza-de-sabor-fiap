package com.br.fiap.fortaleza.sabor.infrastructure.persistence.typeUser;

import com.br.fiap.fortaleza.sabor.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "typeUser")
public class TypeUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idType;

    @Column(nullable = false)
    private String nameType;

    public TypeUserEntity(Long idType, String nameType) {
        this.idType = idType;
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