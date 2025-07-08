package com.br.fiap.fortaleza.sabor.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "items_cardapio")
public class MenuItemsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_item_cardapio;

    @ManyToOne
    @JoinColumn(name = "id_cardapio")
    private MenuEntity cardapio;

    private String nome;

    private String descricao_item;

    private String preco_item;

    private Boolean disponibilidade;

    private String imagem_item;

    public MenuItemsEntity() {}

    public MenuItemsEntity(Long id_item_cardapio, MenuEntity cardapio, String nome, String descricao_item, String preco_item, Boolean disponibilidade, String imagem_item) {
        this.id_item_cardapio = id_item_cardapio;
        this.cardapio = cardapio;
        this.nome = nome;
        this.descricao_item = descricao_item;
        this.preco_item = preco_item;
        this.disponibilidade = disponibilidade;
        this.imagem_item = imagem_item;
    }

    public Long getId_item_cardapio() {
        return id_item_cardapio;
    }

    public void setId_item_cardapio(Long id_item_cardapio) {
        this.id_item_cardapio = id_item_cardapio;
    }

    public MenuEntity getCardapio() {
        return cardapio;
    }

    public void setCardapio(MenuEntity cardapio) {
        this.cardapio = cardapio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao_item() {
        return descricao_item;
    }

    public void setDescricao_item(String descricao_item) {
        this.descricao_item = descricao_item;
    }

    public String getPreco_item() {
        return preco_item;
    }

    public void setPreco_item(String preco_item) {
        this.preco_item = preco_item;
    }

    public Boolean getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(Boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getImagem_item() {
        return imagem_item;
    }

    public void setImagem_item(String imagem_item) {
        this.imagem_item = imagem_item;
    }
}
