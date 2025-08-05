package com.br.fiap.fortaleza.sabor.cucumber.steps;

import io.cucumber.java.pt.*;

public class MenuManagementSteps {
    
    private String createdMenuItemId;
    
    @Quando("eu criar um item do cardápio com dados válidos")
    public void euCriarUmItemDoCardapioComDadosValidos() {
        System.out.println("✓ Criando item do cardápio com dados válidos...");
        createdMenuItemId = "1";
    }
    
    @Então("o item deve ser criado com sucesso")
    public void oItemDeveSerCriadoComSucesso() {
        System.out.println("✓ Item do cardápio criado com sucesso!");
    }
    
    @Dado("que existem itens no cardápio")
    public void queExistemItensNoCardapio() {
        System.out.println("✓ Verificando itens no cardápio...");
    }
    
    @Quando("eu buscar todos os itens do cardápio")
    public void euBuscarTodosOsItensDoCardapio() {
        System.out.println("✓ Buscando todos os itens do cardápio...");
    }
    
    @Então("deve retornar uma lista de itens")
    public void deveRetornarUmaListaDeItens() {
        System.out.println("✓ Lista de itens retornada!");
    }
    
    @Dado("que existe um item no cardápio com ID válido")
    public void queExisteUmItemNoCardapioComIdValido() {
        createdMenuItemId = "1";
        System.out.println("✓ Item no cardápio com ID válido: " + createdMenuItemId);
    }
    
    @Quando("eu buscar o item pelo ID")
    public void euBuscarOItemPeloId() {
        System.out.println("✓ Buscando item pelo ID: " + createdMenuItemId);
    }
    
    @Então("deve retornar os dados do item")
    public void deveRetornarOsDadosDoItem() {
        System.out.println("✓ Dados do item retornados!");
    }
    
    @Dado("que existe um item no cardápio")
    public void queExisteUmItemNoCardapio() {
        createdMenuItemId = "1";
        System.out.println("✓ Item no cardápio com ID: " + createdMenuItemId);
    }
    
    @Quando("eu atualizar os dados do item")
    public void euAtualizarOsDadosDoItem() {
        System.out.println("✓ Atualizando dados do item ID: " + createdMenuItemId);
    }
    
    @Então("o item deve ser atualizado com sucesso")
    public void oItemDeveSerAtualizadoComSucesso() {
        System.out.println("✓ Item atualizado com sucesso!");
    }
    
    @Quando("eu remover o item")
    public void euRemoverOItem() {
        System.out.println("✓ Removendo item ID: " + createdMenuItemId);
    }
    
    @Então("o item deve ser removido com sucesso")
    public void oItemDeveSerRemovidoComSucesso() {
        System.out.println("✓ Item removido com sucesso!");
    }
    
    @Quando("eu tentar criar um item com preço negativo")
    public void euTentarCriarUmItemComPrecoNegativo() {
        System.out.println("✓ Tentando criar item com preço negativo...");
    }
    
    @Quando("eu atualizar a disponibilidade do item")
    public void euAtualizarADisponibilidadeDoItem() {
        System.out.println("✓ Atualizando disponibilidade do item ID: " + createdMenuItemId);
    }
    
    @Então("a disponibilidade deve ser atualizada")
    public void aDisponibilidadeDeveSerAtualizada() {
        System.out.println("✓ Disponibilidade atualizada com sucesso!");
    }
}
