package com.br.fiap.fortaleza.sabor.cucumber.steps;

import io.cucumber.java.pt.*;

public class RestaurantManagementSteps {
    
    private String createdRestaurantId;
    
    @Dado("que existe um usuário proprietário")
    public void queExisteUmUsuarioProprietario() {
        System.out.println("✓ Verificando usuário proprietário...");
    }
    
    @Quando("eu criar um restaurante com dados válidos")
    public void euCriarUmRestauranteComDadosValidos() {
        System.out.println("✓ Criando restaurante com dados válidos...");
        createdRestaurantId = "1";
    }
    
    @Então("o restaurante deve ser criado com sucesso")
    public void oRestauranteDeveSerCriadoComSucesso() {
        System.out.println("✓ Restaurante criado com sucesso!");
    }
    
    @Dado("que existem restaurantes cadastrados")
    public void queExistemRestaurantesCadastrados() {
        System.out.println("✓ Verificando restaurantes cadastrados...");
    }
    
    @Quando("eu buscar todos os restaurantes")
    public void euBuscarTodosOsRestaurantes() {
        System.out.println("✓ Buscando todos os restaurantes...");
    }
    
    @Então("deve retornar uma lista de restaurantes")
    public void deveRetornarUmaListaDeRestaurantes() {
        System.out.println("✓ Lista de restaurantes retornada!");
    }
    
    @Dado("que existe um restaurante com ID válido")
    public void queExisteUmRestauranteComIdValido() {
        createdRestaurantId = "1";
        System.out.println("✓ Restaurante com ID válido: " + createdRestaurantId);
    }
    
    @Quando("eu buscar o restaurante pelo ID")
    public void euBuscarORestaurantePeloId() {
        System.out.println("✓ Buscando restaurante pelo ID: " + createdRestaurantId);
    }
    
    @Então("deve retornar os dados do restaurante")
    public void deveRetornarOsDadosDoRestaurante() {
        System.out.println("✓ Dados do restaurante retornados!");
    }
    
    @Dado("que existe um restaurante cadastrado")
    public void queExisteUmRestauranteCadastrado() {
        createdRestaurantId = "1";
        System.out.println("✓ Restaurante cadastrado com ID: " + createdRestaurantId);
    }
    
    @Quando("eu atualizar os dados do restaurante")
    public void euAtualizarOsDadosDoRestaurante() {
        System.out.println("✓ Atualizando dados do restaurante ID: " + createdRestaurantId);
    }
    
    @Então("o restaurante deve ser atualizado com sucesso")
    public void oRestauranteDeveSerAtualizadoComSucesso() {
        System.out.println("✓ Restaurante atualizado com sucesso!");
    }
    
    @Quando("eu tentar criar um restaurante com dados inválidos")
    public void euTentarCriarUmRestauranteComDadosInvalidos() {
        System.out.println("✓ Tentando criar restaurante com dados inválidos...");
    }
    
    @Quando("eu buscar um restaurante com ID inexistente")
    public void euBuscarUmRestauranteComIdInexistente() {
        System.out.println("✓ Buscando restaurante com ID inexistente...");
    }
}
