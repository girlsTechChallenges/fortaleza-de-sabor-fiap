# language: pt
Funcionalidade: Gerenciamento de Usuários
  Como um administrador do sistema
  Eu quero gerenciar usuários
  Para manter o controle de acesso ao sistema

  Contexto:
    Dado que a API está rodando
    E que existe um tipo de usuário "CUSTOMER"

  Cenário: Criar usuário com sucesso
    Quando eu criar um usuário com dados válidos
    Então o usuário deve ser criado com sucesso
    E deve retornar status 201

  Cenário: Listar todos os usuários
    Dado que existem usuários cadastrados
    Quando eu buscar todos os usuários
    Então deve retornar uma lista de usuários
    E deve retornar status 200

  Cenário: Buscar usuário por ID
    Dado que existe um usuário com ID válido
    Quando eu buscar o usuário pelo ID
    Então deve retornar os dados do usuário
    E deve retornar status 200

  Cenário: Atualizar usuário existente
    Dado que existe um usuário cadastrado
    Quando eu atualizar os dados do usuário
    Então o usuário deve ser atualizado com sucesso
    E deve retornar status 200

  Cenário: Remover usuário existente
    Dado que existe um usuário cadastrado
    Quando eu remover o usuário
    Então o usuário deve ser removido com sucesso
    E deve retornar status 204

  Cenário: Tentar criar usuário com dados inválidos
    Quando eu tentar criar um usuário com email inválido
    Então deve retornar erro de validação
    E deve retornar status 400

  Cenário: Buscar usuário inexistente
    Quando eu buscar um usuário com ID inexistente
    Então deve retornar erro de não encontrado
    E deve retornar status 404
