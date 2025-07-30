package com.br.fiap.fortaleza.sabor.infrastructure.persistence.user;

import jakarta.persistence.*;

@Entity
@Table(name = "userType")
public class UserTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idType;

    @Column(nullable = false)
    private String nameType;

    public UserTypeEntity(Long idType, String nameType) {
        this.idType = idType;
        this.nameType = nameType;
    }

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UserEntity usuario;

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

}