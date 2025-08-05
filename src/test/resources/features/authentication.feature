# language: pt
Funcionalidade: Autenticação de Usuários
  Como um usuário do sistema
  Eu quero fazer login
  Para acessar as funcionalidades do sistema

  Contexto:
    Dado que a API está rodando

  Cenário: Login com credenciais válidas
    Dado que existe um usuário cadastrado no sistema
    Quando eu fazer login com credenciais válidas
    Então deve retornar um token de autenticação
    E deve retornar status 200

  Cenário: Login com credenciais inválidas
    Quando eu tentar fazer login com credenciais inválidas
    Então deve retornar erro de autenticação
    E deve retornar status 401

  Cenário: Login com formato de dados inválido
    Quando eu tentar fazer login com dados malformados
    Então deve retornar erro de validação
    E deve retornar status 400
