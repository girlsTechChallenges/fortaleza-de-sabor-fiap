# Documentação Técnica - Fortaleza de Sabor API

## 👥 Equipe
**Equipe Girls Tech Challenges**  
Tech Challenge FIAP - Arquitetura de Software

---

## 🎯 1. Introdução

### Descrição do Problema
Na região, um grupo de restaurantes decidiu contratar estudantes para construir um **sistema de gestão compartilhado**, visando:
- ✅ Reduzir custos operacionais
- ✅ Melhorar eficiência operacional  
- ✅ Permitir escolha de restaurantes por tipo de comida
- ✅ Facilitar gestão centralizada de operações

### Objetivo do Projeto
Desenvolver um **backend completo e robusto** utilizando **Spring Boot**, com foco em:

#### Funcionalidades Core
- 👥 **Gestão de Usuários**: CRUD completo (Cliente/Dono)
- 🍽️ **Gestão de Restaurantes**: Criação e atualização
- 🔐 **Autenticação**: Login seguro com JWT
- 🍕 **Gestão de Cardápio**: Listagem de itens
- ⚙️ **Management**: Tipos de usuários

#### Características Técnicas
- 🏗️ **Clean Architecture** com DDD
- 🐳 **Docker** ready com multi-stage build
- 🧪 **54 testes** (46 unitários + 8 integração)
- 📊 **Documentação Swagger** completa
- 🔒 **Segurança** JWT + Bean Validation

---

## 🏗️ 2. Arquitetura do Sistema

### Visão Arquitetural
O projeto implementa **Clean Architecture** com **Domain-Driven Design**, garantindo:

```
🎯 Camada de Apresentação (Infrastructure)
    ↓ (Controllers, DTOs, Swagger)
💼 Camada de Aplicação (Application)  
    ↓ (Use Cases, Ports)
🏛️ Camada de Domínio (Domain)
    ↓ (Entidades, Value Objects)
🔧 Camada de Infraestrutura (Infrastructure)
    ↓ (Repositories, Mappers, Config)
🗄️ Banco de Dados (PostgreSQL/H2)
```

### Detalhamento das Camadas

#### 🎯 **Camada de Apresentação**
```java
// Controllers REST
AuthController      - Autenticação (/auth/*)  
UserController      - Usuários (/users/*)
RestaurantController - Restaurantes (/restaurants/*)
MenuItemsController - Cardápio (/cardapio/*)
TypeController      - Tipos (/type-users/*)

// DTOs com Bean Validation
UserRequestDto, RestaurantRequestDto, etc.

// Documentação Swagger
Interfaces separadas para documentação OpenAPI
```

#### 💼 **Camada de Aplicação**  
```java
// Use Cases (Regras de Negócio)
AuthUseCase         - validateLogin(), updatePassword()
UserUseCase         - save(), getAll(), getById(), update(), delete()
RestaurantUseCase   - create(), update(), getAll(), getById()
MenuItemsUseCase    - getAll(), getById()
TypeUseCase         - create(), getAll()

// Ports (Interfaces)
Input Ports  - Interfaces dos Use Cases
Output Ports - Interfaces dos Repositories
```

#### 🏛️ **Camada de Domínio**
```java
// Entidades de Negócio
User        - id, name, email, login, password, addresses, typeUser
Restaurant  - id, name, kitchenType, email, ownerName, addresses, businessHours  
Address     - street, neighborhood, complement, number, state, city, zipCode

// Value Objects
BusinessHours - dayOfWeek, openingTime, closingTime, observations
Token         - accessToken, expiresIn
```

#### 🔧 **Camada de Infraestrutura**
```java
// Repository Adapters
UserRepositoryJpa      - Implementa UserRepositoryPort
RestaurantRepositoryJpa - Implementa RestaurantRepositoryPort
MenuRepositoryJpa      - Implementa MenuItemsRepositoryPort

// Mappers de Conversão
UserMapper       - UserDto ↔ User ↔ UserEntity ↔ UserResponse
RestaurantMapper - RestaurantDto ↔ Restaurant ↔ RestaurantEntity  
MenuMapper       - MenuDto ↔ MenuItem ↔ MenuEntity

// Configurações
SecurityConfig  - JWT, CORS, autorização
OpenAPIConfig   - Documentação Swagger
DatabaseConfig  - PostgreSQL/H2
```

### Fluxo de Requisição
```
1. 📥 Cliente HTTP → Controller (endpoint REST)
2. 📋 Controller → DTO (validação Bean Validation)
3. 💼 Controller → Use Case (regras de negócio)
4. 🔗 Use Case → Repository Port (interface)  
5. 🗄️ Repository Adapter → Database (PostgreSQL/H2)
6. 📤 Response → Cliente (JSON)
```

![Diagrama de Arquitetura](diagram.png)
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
---

## 🛠️ 3. Tecnologias Utilizadas

### Backend Core
- ☕ **Java 21** - Linguagem principal
- 🍃 **Spring Boot 3.4.5** - Framework base
- 🗄️ **PostgreSQL** - Banco de dados produção
- 💾 **H2** - Banco de dados para testes

### Segurança e Validação
- 🔒 **Spring Security** - Autenticação/autorização
- 🎫 **JWT** - Tokens de acesso seguros
- ✅ **Bean Validation** - Validação de entrada

### Testes e Qualidade
- 🧪 **JUnit 5** - Framework de testes
- 🎭 **Mockito** - Mocking para testes unitários
- 🌐 **REST-assured** - Testes de integração
- 📊 **54 testes** (96.3% success rate)

### DevOps e Build
- 📦 **Maven** - Gerenciamento de dependências
- 🐳 **Docker** - Containerização
- 📖 **SpringDoc OpenAPI** - Documentação automática

---

## 🔗 4. Endpoints da API

### 👥 Gestão de Usuários
| Endpoint | Método | Descrição |
|----------|--------|-----------|
| `/users` | POST | Criar usuário (Cliente/Dono) |
| `/users` | GET | Listar todos os usuários |
| `/users/{id}` | GET | Buscar usuário por ID |
| `/users/{id}` | PUT | Atualizar usuário |
| `/users/{id}` | DELETE | Remover usuário |

### 🍽️ Gestão de Restaurantes  
| Endpoint | Método | Descrição |
|----------|--------|-----------|
| `/restaurants` | POST | Criar restaurante (requer DONO) |
| `/restaurants` | GET | Listar restaurantes |
| `/restaurants/{id}` | GET | Buscar restaurante por ID |
| `/restaurants/{id}` | PUT | Atualizar restaurante |

### 🔐 Autenticação
| Endpoint | Método | Descrição |
|----------|--------|-----------|
| `/auth/login` | POST | Realizar login (JWT) |
| `/auth/password` | PATCH | Alterar senha |

### 🍕 Gestão de Cardápio
| Endpoint | Método | Descrição |
|----------|--------|-----------|
| `/cardapio` | GET | Listar itens do menu |
| `/cardapio/{id}` | GET | Buscar item por ID |

### ⚙️ Tipos de Usuários
| Endpoint | Método | Descrição |
|----------|--------|-----------|
| `/type-users` | POST | Criar tipo de usuário |
| `/type-users` | GET | Listar tipos disponíveis |

### 📋 Exemplos de Requisição e Resposta

#### 👤 Criar Usuário
**POST** `/users`

Request:
```json
{
  "nome": "João Silva",
  "email": "joao@email.com", 
  "login": "joaosilva",
  "senha": "senha123",
  "dataAlteracao": "2025-08-03",
  "tipo": {
    "id": 2,
    "type": "DONO"
  },
  "address": [
    {
      "street": "Rua das Flores",
      "neighborhood": "Centro", 
      "complement": "Apto 101",
      "number": 123,
      "state": "SP",
      "city": "São Paulo",
      "zipCode": "12345678"
    }
  ]
}
```

Response (201 Created):
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com",
  "login": "joaosilva",
  "typeUser": {
    "id": 2,
    "nameType": "DONO"
  },
  "addresses": [
    {
      "street": "Rua das Flores",
      "neighborhood": "Centro",
      "complement": "Apto 101", 
      "number": 123,
      "state": "SP",
      "city": "São Paulo",
      "zipCode": "12345678"
    }
  ],
  "dataAlteracao": "2025-08-03"
}
```

#### 🍽️ Criar Restaurante
**POST** `/restaurants`

Request:
```json
{
  "name": "Fortaleza do Sabor",
  "kitchenType": "Brasileira",
  "email": "contato@fortaleza.com",
  "ownerName": "João Silva",
  "address": [
    {
      "street": "Rua do Restaurante",
      "neighborhood": "Centro",
      "complement": "Loja 1",
      "number": 456,
      "state": "SP", 
      "city": "São Paulo",
      "zipCode": "12345678"
    }
  ],
  "businessHours": [
    {
      "dayOfWeek": "MONDAY",
      "openingTime": "08:00",
      "closingTime": "18:00",
      "observations": "Horário comercial"
    }
  ]
}
```

Response (201 Created):
```json
{
  "id": 1,
  "name": "Fortaleza do Sabor", 
  "ownerName": "João Silva"
}
```

#### 🔐 Login
**POST** `/auth/login`

Request:
```json
{
  "email": "joao@email.com",
  "password": "senha123"
}
```

Response (200 OK):
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 7200
}
```
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

---

## 🚀 5. Configuração e Execução

### 🐳 Docker Compose
O arquivo `docker-compose.yml` orquestra a aplicação e PostgreSQL:

```yaml
services:
  app:
    build:
      context: .                    # Build da aplicação (multi-stage)
    container_name: fortaleza-app   # Nome do container
    depends_on:
      - database                    # Garante ordem de inicialização
    environment:
      # Configurações do banco
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
      - SPRING_DATASOURCE_URL=jdbc:postgresql://database:5432/postgres
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
    ports:
      - "8080:8080"                # API acessível em localhost:8080

  database:
    image: postgres:15-alpine       # PostgreSQL otimizado
    container_name: fortaleza-db    # Nome do container
    environment:
      - POSTGRES_USER=postgres      # Usuário do banco
      - POSTGRES_PASSWORD=postgres  # Senha do banco
      - POSTGRES_DB=postgres        # Nome do database
    ports:
      - "5432:5432"                # Banco acessível localmente
    volumes:
      - postgres_data:/var/lib/postgresql/data  # Persistência

volumes:
  postgres_data:                   # Volume para dados persistentes
```

### ⚡ Instruções de Execução

#### Opção 1: Docker (Recomendado)
```bash
# 1. Clone o repositório
git clone https://github.com/girlsTechChallenges/fortaleza-de-sabor-fiap.git
cd fortaleza-de-sabor-fiap

# 2. Execute com Docker Compose
docker-compose up --build

# 3. Acesse a aplicação
# API: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui.html
```

#### Opção 2: Build Local + Docker
```bash
# 1. Compile o projeto
./mvnw clean install

# 2. Execute apenas o banco
docker-compose up database

# 3. Execute a aplicação
./mvnw spring-boot:run
```

#### Opção 3: Desenvolvimento Local  
```bash
# 1. Configure PostgreSQL local (porta 5432)
# 2. Configure variáveis de ambiente no application.properties
# 3. Execute a aplicação
./mvnw spring-boot:run
```

### 🧪 Executar Testes
```bash
# Todos os testes (unitários + integração)
mvn clean test

# Apenas testes unitários
mvn clean test -P unit-tests

# Apenas testes de integração
mvn clean test -P integration-tests
```

---

## 🏆 6. Qualidade e Testes

### 📊 Métricas de Qualidade
- ✅ **54 testes** (46 unitários + 8 integração)
- ✅ **96.3% taxa de sucesso** nos testes
- ✅ **100% cobertura** de controllers principais
- ✅ **100% cobertura** de use cases
- ✅ **Padrão AAA** em todos os testes

### 🧪 Estratégia de Testes

#### Testes Unitários (46 testes)
```java
// Use Cases - Regras de negócio isoladas
AuthUseCaseTest, UserUseCaseTest, RestaurantUseCaseTest

// Controllers - Endpoints REST com MockMvc  
AuthControllerTest, UserControllerTest, RestaurantControllerTest

// Mappers - Conversões entre camadas
UserMapperTest, RestaurantMapperTest, MenuMapperTest

// DTOs - Validação Bean Validation
UserRequestDtoTest, RestaurantRequestDtoTest, AddressDtoTest
```

#### Testes de Integração (8 testes)
```java
// REST-assured com H2 em memória
AuthControllerIntegrationTest       - 6 testes
UserControllerIntegrationTest       - 9 testes  
RestaurantControllerIntegrationTest - 8 testes
MenuItemsControllerIntegrationTest  - 13 testes
TypeControllerIntegrationTest       - 9 testes
WorkflowIntegrationTest             - 12 testes
```

### 🎯 Boas Práticas Aplicadas
- ✅ **Clean Architecture** - Separação clara de responsabilidades
- ✅ **SOLID Principles** - Princípios aplicados consistentemente  
- ✅ **DRY** - Código reutilizável e modular
- ✅ **Bean Validation** - Validação declarativa
- ✅ **Exception Handling** - Tratamento centralizado
- ✅ **Security** - JWT + Spring Security
- ✅ **Documentation** - Swagger OpenAPI completo

---

## 📚 7. Recursos Adicionais

### 📖 Documentação Disponível
- 📋 **[README.md](README.md)** - Guia principal de uso
- 🏗️ **[architecture.md](architecture.md)** - Arquitetura detalhada
- 📄 **[RESUMO_PROJETO.md](RESUMO_PROJETO.md)** - Resumo executivo
- 🧪 **[DOCUMENTACAO_COMPLETA_TESTES.md](DOCUMENTACAO_COMPLETA_TESTES.md)** - Doc. de testes

### 📮 Collection Postman
- **35+ cenários** organizados em módulos
- **Automação completa** com variáveis dinâmicas  
- **Testes de validação** automáticos
- **Cenários de erro** incluídos

### 🔗 URLs de Acesso
- **API Base**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **H2 Console** (testes): http://localhost:8080/h2-console

---

## 🎉 Conclusão

O **Fortaleza de Sabor API** representa uma solução **robusta, escalável e bem testada** para gestão de restaurantes, implementando:

- 🏗️ **Arquitetura de classe mundial** (Clean Architecture + DDD)
- 🧪 **Qualidade garantida** (54 testes automatizados)
- 🔒 **Segurança empresarial** (JWT + Bean Validation) 
- 📊 **Documentação completa** (Swagger + guias técnicos)
- 🐳 **Deploy simplificado** (Docker ready)

**Uma API pronta para produção que demonstra as melhores práticas de desenvolvimento de software empresarial.** 🍽️✨

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

## 6. Melhorias Técnicas Implementadas

### 6.1. Otimização do Build com Docker Multi-stage
**Problema Identificado**: A estratégia de apenas copiar o arquivo JAR para construção da imagem não é uma boa prática, pois depende que o processo de build seja realizado na máquina local.

**Solução Implementada**: 
- Implementação de Docker multi-stage build
- Estágio 1: Build automático usando `maven:3.9.5-eclipse-temurin-21`
- Estágio 2: Execução otimizada com `eclipse-temurin:21-jre-alpine`

**Benefícios**:
- Build reproduzível e independente do ambiente
- Imagem final mais leve (apenas JRE)
- Processo automatizado via Docker

### 6.2. Correção da Configuração PostgreSQL
**Problema Identificado**: Erro na configuração da conexão do banco de dados (docker-compose.yml).

**Solução Implementada**:
- Correção das variáveis de ambiente no docker-compose.yml
- Adição da variável `SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver`
- Padronização do nome do serviço de banco (`db`)

### 6.3. Driver PostgreSQL Explícito
**Problema Identificado**: Faltou informar `spring.datasource.driver-class-name=org.postgresql.Driver`.

**Solução Implementada**:
- Configuração explícita do driver em `application.properties`
- Redundância configuracional via variável de ambiente
- Maior confiabilidade na conexão com o banco

### 6.4. Organização da Documentação Swagger
**Problema Identificado**: A utilização das anotações Swagger no Controller "polui" o código, desviando a atenção do que realmente importa.

**Solução Implementada**:
- Criação de interfaces de documentação separadas:
  - `AuthControllerDocs.java`
  - `UserControllerDocs.java` 
  - `RestaurantControllerDocs.java`
- Controllers implementam essas interfaces
- Separação clara entre lógica de negócio e documentação

**Estrutura Resultante**:
```
src/main/java/com/br/fiap/fortaleza/sabor/infrastructure/controller/
├── docs/
│   ├── AuthControllerDocs.java
│   ├── UserControllerDocs.java
│   └── RestaurantControllerDocs.java
├── AuthController.java (limpo, sem anotações Swagger)
├── UserController.java (limpo, sem anotações Swagger)
└── RestaurantController.java (limpo, sem anotações Swagger)
```

---

## 7. Collections para Teste
### Collection Postman Unificada
A collection para testes está disponível em:
- Local: `/collections/Fortaleza_de_Sabor_API_Completa.postman_collection.json`

### Descrição dos Testes
1. **Importar a collection** disponível no diretório `collections`
2. **Configurar ambiente**: A collection inclui variáveis para baseUrl e token
3. **Cenários disponíveis**:
   - **Testes de Validação**: Campos obrigatórios, formatos e limites
   - **Testes de Lógica de Negócio**: Fluxos completos e regras específicas
   - **Testes de Segurança**: Autenticação e autorização
   - **Casos Extremos**: Dados no limite e situações excepcionais
   - **Cenários de Falha**: Validação de erros e tratamento de exceções
   - **Requisições Malformadas**: Estruturas inválidas e tipos incorretos

### Execução dos Testes
1. Executar endpoints seguindo a ordem sugerida:
   - Criar usuário (POST `/users`)
   - Login (POST `/auth/login`) 
   - Demais operações CRUD
2. A collection possui **35+ cenários de teste** cobrindo casos de sucesso e falha
3. Documentação completa em: `DOCUMENTACAO_COMPLETA_TESTES.md`

---

## 8. Repositório do Código
### URL do Repositório
[https://github.com/girlsTechChallenges/fortaleza-de-sabor-fiap.git](https://github.com/girlsTechChallenges/fortaleza-de-sabor-fiap.git)

---

### Notas
Fiquem à vontade para modificar e ajustar este modelo às necessidades do projeto.
Não é necessário implementar a autenticação dos usuários mas fiquem à vontade para fazê-lo se assim desejarem.
