# language: pt
Funcionalidade: Gerenciamento de Cardápio
  Como um proprietário de restaurante
  Eu quero gerenciar o cardápio
  Para oferecer opções aos clientes

  Contexto:
    Dado que a API está rodando

  Cenário: Criar item do cardápio com sucesso
    Quando eu criar um item do cardápio com dados válidos
    Então o item deve ser criado com sucesso
    E deve retornar status 201

  Cenário: Listar todos os itens do cardápio
    Dado que existem itens no cardápio
    Quando eu buscar todos os itens do cardápio
    Então deve retornar uma lista de itens
    E deve retornar status 200

  Cenário: Buscar item do cardápio por ID
    Dado que existe um item no cardápio com ID válido
    Quando eu buscar o item pelo ID
    Então deve retornar os dados do item
    E deve retornar status 200

  Cenário: Atualizar item do cardápio
    Dado que existe um item no cardápio
    Quando eu atualizar os dados do item
    Então o item deve ser atualizado com sucesso
    E deve retornar status 200

  Cenário: Remover item do cardápio
    Dado que existe um item no cardápio
    Quando eu remover o item
    Então o item deve ser removido com sucesso
    E deve retornar status 204

  Cenário: Tentar criar item com preço negativo
    Quando eu tentar criar um item com preço negativo
    Então deve retornar erro de validação
    E deve retornar status 400

  Cenário: Atualizar disponibilidade do item
    Dado que existe um item no cardápio
    Quando eu atualizar a disponibilidade do item
    Então a disponibilidade deve ser atualizada
    E deve retornar status 200
