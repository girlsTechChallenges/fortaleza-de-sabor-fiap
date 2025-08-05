# language: pt
Funcionalidade: Gerenciamento de Restaurantes
  Como um proprietário de restaurante
  Eu quero gerenciar meu restaurante
  Para oferecer serviços aos clientes

  Contexto:
    Dado que a API está rodando
    E que existe um usuário proprietário

  Cenário: Criar restaurante com sucesso
    Quando eu criar um restaurante com dados válidos
    Então o restaurante deve ser criado com sucesso
    E deve retornar status 201

  Cenário: Listar todos os restaurantes
    Dado que existem restaurantes cadastrados
    Quando eu buscar todos os restaurantes
    Então deve retornar uma lista de restaurantes
    E deve retornar status 200

  Cenário: Buscar restaurante por ID
    Dado que existe um restaurante com ID válido
    Quando eu buscar o restaurante pelo ID
    Então deve retornar os dados do restaurante
    E deve retornar status 200

  Cenário: Atualizar dados do restaurante
    Dado que existe um restaurante cadastrado
    Quando eu atualizar os dados do restaurante
    Então o restaurante deve ser atualizado com sucesso
    E deve retornar status 200

  Cenário: Tentar criar restaurante com dados inválidos
    Quando eu tentar criar um restaurante com dados inválidos
    Então deve retornar erro de validação
    E deve retornar status 400

  Cenário: Buscar restaurante inexistente
    Quando eu buscar um restaurante com ID inexistente
    Então deve retornar erro de não encontrado
    E deve retornar status 404
