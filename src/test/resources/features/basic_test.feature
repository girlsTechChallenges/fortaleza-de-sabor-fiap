# language: pt
Funcionalidade: Teste Básico Cucumber
  Como um desenvolvedor
  Eu quero validar se o Cucumber está funcionando
  Para garantir que a configuração está correta

  Cenário: Verificar configuração básica
    Dado que a API está rodando
    Quando eu criar um usuário com dados válidos
    Então o usuário deve ser criado com sucesso
    E deve retornar status 201
