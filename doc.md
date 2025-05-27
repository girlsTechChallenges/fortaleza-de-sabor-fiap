# Projeto: Fortaleza de Sabor API
## Equipe
- Nome: Equipe Fortaleza de Sabor
- RMs: [Insira os RMs dos integrantes]

---

## 1. Introdução
### Descrição do problema
Na nossa região, um grupo de restaurantes decidiu contratar estudantes para construir um sistema de gestão compartilhado, visando reduzir custos e melhorar a eficiência. O sistema permitirá que clientes escolham restaurantes com base na comida oferecida e que os restaurantes gerenciem suas operações de forma eficiente.

### Objetivo do projeto
Desenvolver um backend completo e robusto utilizando Spring Boot, com foco no gerenciamento de usuários, incluindo operações de criação, atualização, exclusão e validação de login. O projeto será configurado para rodar em um ambiente Docker, utilizando Docker Compose para orquestração dos serviços e integração com um banco de dados relacional.

---

## 2. Arquitetura do Sistema
### Descrição da Arquitetura
O projeto segue uma arquitetura modular e organizada, com as seguintes camadas principais:
- **Configuração**: Configurações globais da aplicação, como OpenAPI.
- **Controladores**: Exposição de endpoints da API.
- **Casos de Uso**: Lógica de negócios.
- **Repositórios**: Interação com o banco de dados.
- **Persistência**: Entidades e repositórios JPA.
- **DTOs e Mapeamento**: Transferência de dados e mapeamento.
- **Exceções**: Gerenciamento de erros.

### Diagrama da Arquitetura
![Diagrama de Arquitetura](diagram.png)

---

## 3. Descrição dos Endpoints da API
### Tabela de Endpoints
| Endpoint               | Método | Descrição               |
|------------------------|--------|-------------------------|
| `/api/users`           | POST   | Criar novo usuário      |
| `/api/users/{id}`      | PUT    | Atualizar usuário       |
| `/api/users/{id}`      | GET    | Buscar usuário por ID   |
| `/api/users/{id}`      | DELETE | Remover usuário         |
| `/api/auth/login`      | POST   | Validar login           |
| `/api/auth/password`   | PUT    | Alterar senha           |

### Exemplos de requisição e resposta
Descreva aqui exemplos de requisições e possíveis respostas.

---

## 4. Configuração do Projeto
### Configuração do Docker Compose
O arquivo `docker-compose.yml` orquestra a aplicação e o banco de dados PostgreSQL. Ele define os serviços necessários para execução do sistema.

### Instruções para execução local
1. Clonar o repositório:
   ```bash
   git clone https://github.com/seu-usuario/fortaleza-de-sabor.git
   cd fortaleza-de-sabor
   ```
2. Construir o projeto:
   ```bash
   ./mvnw clean install
   ```
3. Executar com Docker Compose:
   ```bash
   docker-compose up --build
   ```
4. Acessar a aplicação:
   - API: [http://localhost:8080](http://localhost:8080)
   - Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 5. Qualidade do Código
### Boas Práticas Utilizadas
- **DRY**: Código reutilizável e modular.
- **SOLID**: Princípios de design orientado a objetos.
- **Convenções do Spring Boot**: Seguindo padrões do framework.

---

## 6. Collections para Teste
### Link para a Collection do Postman
[Inclua um link ou descreva como importar as collections.]

### Descrição dos Testes Manuais
1. Importar a collection do Postman.
2. Validar os endpoints utilizando os exemplos fornecidos.

---

## 7. Repositório do Código
### URL do Repositório
[Inclua o link para o repositório onde o código está hospedado.]

---

### Notas
Fiquem à vontade para modificar e ajustar este modelo às necessidades do projeto.  
Não é necessário implementar a autenticação dos usuários mas fiquem à vontade para fazê-lo se assim desejarem.
