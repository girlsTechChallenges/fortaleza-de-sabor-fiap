package com.br.fiap.fortaleza.sabor.infrastructure.persistence.user;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "userType")
public class UserTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idType;

    @Column(nullable = false)
    private String nameType;

    @OneToMany(mappedBy = "tipo")
    private List<UserEntity> usuarios;

    public UserTypeEntity(Long idType, String nameType) {
        this.idType = idType;
        this.nameType = nameType;
    }

    public UserTypeEntity() {}

    public UserTypeEntity(String dono) {
    }

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

    public List<UserEntity> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UserEntity> usuarios) {
        this.usuarios = usuarios;
    }
}