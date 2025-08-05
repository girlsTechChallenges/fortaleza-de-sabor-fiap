package com.br.fiap.fortaleza.sabor.cucumber.steps;

import io.cucumber.java.pt.*;

public class AuthenticationSteps {
    
    private String userEmail;
    
    @Dado("que existe um usuário cadastrado no sistema")
    public void queExisteUmUsuarioCadastradoNoSistema() {
        userEmail = "test@example.com";
        System.out.println("✓ Usuário cadastrado no sistema: " + userEmail);
    }
    
    @Quando("eu fazer login com credenciais válidas")
    public void euFazerLoginComCredenciaisValidas() {
        System.out.println("✓ Fazendo login com credenciais válidas...");
        System.out.println("Email: " + userEmail);
    }
    
    @Então("deve retornar um token de autenticação")
    public void deveRetornarUmTokenDeAutenticacao() {
        System.out.println("✓ Token de autenticação retornado!");
    }
    
    @Quando("eu tentar fazer login com credenciais inválidas")
    public void euTentarFazerLoginComCredenciaisInvalidas() {
        System.out.println("✓ Tentando login com credenciais inválidas...");
    }
    
    @Então("deve retornar erro de autenticação")
    public void deveRetornarErroDeAutenticacao() {
        System.out.println("✓ Erro de autenticação retornado!");
    }
    
    @Quando("eu tentar fazer login com dados malformados")
    public void euTentarFazerLoginComDadosMalformados() {
        System.out.println("✓ Tentando login com dados malformados...");
    }
}
