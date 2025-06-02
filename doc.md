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
O projeto segue uma arquitetura em camadas, baseada em princípios de Clean Architecture e DDD (Domain-Driven Design), organizada da seguinte forma:

#### Camada de Apresentação
- **Controllers**: Exposição dos endpoints REST da API (`UserController`, `AuthController`).
- **DTOs**: Objetos de transferência de dados para request/response.
- **Exception Handlers**: Tratamento centralizado de exceções (`UserExceptionHandler`).

#### Camada de Domínio
- **Use Cases**: Implementação das regras de negócio.
  - `AuthUseCase`: Autenticação e gestão de senhas
  - `CreateUseCase`: Criação de usuários
  - `UpdateUseCase`: Atualização de usuários
  - `DeleteUseCase`: Remoção de usuários
  - `GetUseCase`: Consulta de usuários
- **Entidades**: Classes que representam o domínio (`User`, `Address`).

#### Camada de Infraestrutura
- **Gateways**: Interfaces de acesso a dados.
- **Repositórios**: Implementações JPA para persistência.
- **Mappers**: Conversão entre entidades e DTOs.

#### Banco de Dados
- PostgreSQL para persistência dos dados.

### Diagrama da Arquitetura
O diagrama abaixo ilustra a interação entre as camadas da aplicação:

![Diagrama de Arquitetura](diagram.png)

O fluxo típico de uma requisição é:
1. O cliente faz uma requisição HTTP que é recebida pelos Controllers
2. Os Controllers convertem os dados usando DTOs e Mappers
3. Os Use Cases implementam a lógica de negócio usando as Entidades
4. Os Repositories realizam as operações no banco de dados
5. O resultado volta pela mesma cadeia até o cliente

Esta arquitetura garante:
- Separação clara de responsabilidades
- Baixo acoplamento entre os componentes
- Facilidade de teste e manutenção
- Escalabilidade e flexibilidade

---

## 3. Descrição dos Endpoints da API
### Tabela de Endpoints
| Endpoint               | Método  | Descrição                        |
|------------------------|---------|----------------------------------|
| `/users`               | POST    | Criar novo usuário               |
| `/users/{id}`          | PUT     | Atualizar usuário                |
| `/users/{id}`          | GET     | Buscar usuário por ID            |
| `/users`               | GET     | Buscar todos os usuários         |
| `/users/{id}`          | DELETE  | Remover usuário                  |
| `/auth/login`          | POST    | Validar login                    |
| `/auth/password`       | PATCH   | Alterar senha                    |

### Exemplos de requisição e resposta

#### Criar Usuário
**POST** `/users`

Request:
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "login": "joaosilva",
  "senha": "senha1234",
  "dataAlteracao": "2025-05-17",
  "tipo": "DONO",
  "address": [
    {
      "rua": "Rua Alves Paulista",
      "bairro": "Paulista Nova",
      "complemento": "casa",
      "numero": 130,
      "estado": "São Paulo",
      "cidade": "São Paulo",
      "cep": 85965000
    }
  ]
}
```
Response (201 Created):
```json
{
  "nome": "João Silva",
  "login": "joaosilva",
  "email": "joao@email.com",
  "tipo": "DONO",
  "address": [
    {
      "rua": "Rua Alves Paulista",
      "bairro": "Paulista Nova",
      "complemento": "casa",
      "numero": 130,
      "estado": "São Paulo",
      "cidade": "São Paulo",
      "cep": 85965000
    }
  ]
}
```

#### Buscar Usuário por ID
**GET** `/users/{id}`

Response (202 Accepted):
```json
{
  "nome": "João Silva",
  "login": "joaosilva",
  "email": "joao@email.com",
  "tipo": "DONO",
  "address": [
    {
      "rua": "Rua Alves Paulista",
      "bairro": "Paulista Nova",
      "complemento": "casa",
      "numero": 130,
      "estado": "São Paulo",
      "cidade": "São Paulo",
      "cep": 85965000
    }
  ]
}
```

#### Buscar Todos os Usuários
**GET** `/users`

Response (200 OK):
```json
[
  {
    "nome": "João Silva",
    "login": "joaosilva",
    "email": "joao@email.com",
    "tipo": "DONO",
    "address": [
      {
        "rua": "Rua Alves Paulista",
        "bairro": "Paulista Nova",
        "complemento": "casa",
        "numero": 130,
        "estado": "São Paulo",
        "cidade": "São Paulo",
        "cep": 85965000
      }
    ]
  }
]
```

#### Atualizar Usuário
**PUT** `/users/{id}`

Request:
```json
{
  "nome": "João Silva Atualizado",
  "email": "joao@email.com",
  "login": "joaosilva",
  "senha": "novaSenha123",
  "dataAlteracao": "2025-06-01",
  "tipo": "DONO",
  "address": [
    {
      "rua": "Rua Nova",
      "bairro": "Paulista Nova",
      "complemento": "casa",
      "numero": 130,
      "estado": "São Paulo",
      "cidade": "São Paulo",
      "cep": 85965000
    }
  ]
}
```
Response (202 Accepted):
```json
{
  "nome": "João Silva Atualizado",
  "login": "joaosilva",
  "email": "joao@email.com",
  "tipo": "DONO",
  "address": [
    {
      "rua": "Rua Nova",
      "bairro": "Paulista Nova",
      "complemento": "casa",
      "numero": 130,
      "estado": "São Paulo",
      "cidade": "São Paulo",
      "cep": 85965000
    }
  ]
}
```

#### Remover Usuário
**DELETE** `/users/{id}`

Response (204 No Content):

---

#### Login
**POST** `/auth/login`

Request:
```json
{
  "email": "joao@email.com",
  "password": "senha1234"
}
```
Response (200 OK):
```json
{
  "token": "<jwt-token>",
  "id": 5
}
```

#### Alterar Senha
**PATCH** `/auth/password`

Request:
```json
{
  "email": "joao@email.com",
  "password": "novaSenha123"
}
```
Response (204 No Content):

---

## 4. Configuração do Projeto
### Configuração do Docker Compose
O arquivo `docker-compose.yml` orquestra a aplicação e o banco de dados PostgreSQL. Abaixo, cada comando e configuração do arquivo é explicado:

#### Estrutura do docker-compose.yml

```yaml
services:
  app:
    build:
      context: .           # Define o diretório de build da aplicação (raiz do projeto)
    container_name: app    # Nome do container da aplicação
    depends_on:
      - db                 # Garante que o banco de dados (db) será iniciado antes da aplicação
    environment:
      - POSTGRES_USER=postgres                # Usuário do banco de dados
      - POSTGRES_PASSWORD=postgres            # Senha do banco de dados
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/postgres  # URL de conexão do Spring para o banco
      - SPRING_DATASOURCE_USERNAME=postgres   # Usuário do banco para o Spring
      - SPRING_DATASOURCE_PASSWORD=postgres   # Senha do banco para o Spring
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update  # Configuração do Hibernate para atualizar o schema automaticamente
    ports:      - "8080:8080"         # Mapeia a porta 8080 do container para a 8080 do host

  db:
    image: postgres:latest # Imagem oficial do PostgreSQL
    container_name: db     # Nome do container do banco de dados
    environment:
      - POSTGRES_USER=postgres      # Usuário padrão do banco
      - POSTGRES_PASSWORD=postgres  # Senha padrão do banco
      - POSTGRES_DB=postgres        # Nome do banco de dados
    ports:
      - "5432:5432"        # Mapeia a porta 5432 do container para a 5432 do host
```

#### Explicação dos principais comandos e parâmetros:
- `version`: Define a versão do Docker Compose utilizada.
- `services`: Define os serviços que serão orquestrados (app e db).
- `build.context`: Diretório onde está o Dockerfile da aplicação.
- `container_name`: Nome do container para facilitar identificação.
- `depends_on`: Garante a ordem de inicialização dos serviços.
- `environment`: Variáveis de ambiente passadas para os containers (credenciais, URLs, configurações do Spring e do banco).
- `ports`: Mapeamento de portas entre o host e o container.
- `image`: Imagem Docker a ser utilizada (no caso do banco, a oficial do PostgreSQL).

### Instruções para execução local
1. Clonar o repositório:
   ```powershell
   git clone https://github.com/seu-usuario/fortaleza-de-sabor-fiap.git
   cd fortaleza-de-sabor-fiap
   ```
2. Construir o projeto:
   ```powershell
   ./mvnw clean install
   ```
3. Executar com Docker Compose:
   ```powershell
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

### Testes Implementados
O projeto possui uma cobertura completa de testes unitários, incluindo:

#### Testes de Use Cases
- `AuthUseCaseTest`: Validação de autenticação e operações de senha
- `CreateUseCaseTest`: Criação de novos usuários
- `DeleteUseCaseTest`: Remoção de usuários
- `GetUseCaseTest`: Busca e listagem de usuários
- `UpdateUseCaseTest`: Atualização de dados de usuários

#### Testes de Controllers
- `AuthControllerTest`: Endpoints de autenticação
- `UserControllerTest`: Endpoints CRUD de usuários

#### Testes de Infraestrutura
- `UserExceptionHandlerTest`: Tratamento de exceções
- `UserRepositoryJpaTest`: Operações de persistência
- `UserEntityMapperTest`: Mapeamento entre DTOs e entidades

### Artefatos Gerados
Após a build, o projeto gera os seguintes artefatos:

#### JARs
- Executável: `fortaleza.sabor-0.0.1-SNAPSHOT.jar`
- Original: `fortaleza.sabor-0.0.1-SNAPSHOT.jar.original`

#### Relatórios e Configurações
- Relatórios de testes: `/target/surefire-reports/`
- Arquivos de propriedades:
  - Produção: `/target/classes/application.properties`
  - Testes: `/target/test-classes/application-test.properties`
- Scripts SQL: `/target/test-classes/data.sql`
- Chaves de aplicação: 
  - `/target/classes/app.key`
  - `/target/classes/app.pub`

---

## 6. Collections para Teste
### Link para a Collection do Postman
A collection para testes está disponível em:
- Local: `/collections/collection-phase-one`

### Descrição dos Testes Manuais
1. Importar a collection disponível no diretório `collections`
2. Executar os endpoints seguindo a ordem:
   - Criar usuário (POST `/users`)
   - Login (POST `/auth/login`)
   - Demais operações CRUD

---

## 7. Repositório do Código
### URL do Repositório
[https://github.com/girlsTechChallenges/fortaleza-de-sabor-fiap.git](https://github.com/girlsTechChallenges/fortaleza-de-sabor-fiap.git)

---

### Notas
Fiquem à vontade para modificar e ajustar este modelo às necessidades do projeto.
Não é necessário implementar a autenticação dos usuários mas fiquem à vontade para fazê-lo se assim desejarem.
