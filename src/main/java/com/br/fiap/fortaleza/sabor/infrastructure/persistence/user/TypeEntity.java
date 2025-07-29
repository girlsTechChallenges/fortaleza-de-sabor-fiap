package com.br.fiap.fortaleza.sabor.infrastructure.persistence.user;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos")
public class TypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_tipo")
    private String nameType;

    public TypeEntity(Long id, String nameType) {
        this.id = id;
        this.nameType = nameType;
    }

    public TypeEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }
}