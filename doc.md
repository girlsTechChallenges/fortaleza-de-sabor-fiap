### Objetivo do Projeto
Desenvolver um **backend completo e robusto** utilizando **Spring Boot**.
Consulte funcionalidades, arquitetura, tecnologias e cobertura de testes em:
- [RESUMO_PROJETO.md](RESUMO_PROJETO.md)
- [architecture.md](architecture.md)

## 🏗️ 2. Arquitetura do Sistema

Consulte detalhes completos sobre arquitetura, camadas, fluxos e diagramas em:
- [architecture.md](architecture.md)

## 3. Endpoints e Exemplos

Consulte a lista completa de endpoints, exemplos de payloads e detalhes técnicos em:
- [RESUMO_PROJETO.md](RESUMO_PROJETO.md)
- [architecture.md](architecture.md)

## 🛠️ Tecnologias Utilizadas

Consulte a lista de tecnologias, dependências e detalhes de configuração em:
- [architecture.md](architecture.md)

## 🚀 4. Configuração e Execução

Consulte instruções detalhadas de execução (Docker, build local, variáveis de ambiente) em:
- [RESUMO_PROJETO.md](RESUMO_PROJETO.md)

## 🏆 5. Qualidade e Testes

Consulte cobertura, comandos, métricas e detalhes de execução em:
- [DOCUMENTACAO_COMPLETA_TESTES.md](DOCUMENTACAO_COMPLETA_TESTES.md)

### 📮 Collection Postman
- Arquivo: `collections/Fortaleza_de_Sabor_API.postman_collection.json`

### 7. Collections para Teste
A collection para testes está disponível em:
- Local: `/collections/Fortaleza_de_Sabor_API.postman_collection.json`

> Arquivos como `Fortaleza_de_Sabor_API_Completa.postman_collection.json` não existem na raiz do projeto neste momento. Caso sejam criados, atualizar esta documentação.

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
- ✅ Permitir escolha de restaurantes por type de comida
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
MenuItemsUseCase    - save(), getAll(), getById(), update(), delete()
TypeUseCase         - create(), getAll(), getById(), update(), delete()

// Ports (Interfaces)
Input Ports  - Interfaces dos Use Cases (ex: AuthUseCasePort, UserUseCasePort, etc.)
Output Ports - Interfaces dos Repositories (ex: AuthRepositoryPort, UserRepositoryPort, etc.)
```

#### 🏛️ **Camada de Domínio**
```java
// Entidades de Negócio
User        - id, name, email, login, password, addresses, typeUser
Restaurant  - id, name, kitchenType, email, ownerName, addresses, businessHours
MenuItem    - id, name, description, price, restaurantId
TypeUser    - id, nameType
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


---

## 3. Descrição dos Endpoints da API
### Endpoints Disponíveis

#### 👤 Gestão de Usuários (`/users`)
| Método | Endpoint         | Descrição                |
|--------|------------------|--------------------------|
| POST   | /users           | Criar usuário            |
| GET    | /users           | Listar todos os usuários |
| GET    | /users/{id}      | Buscar usuário por ID    |
| PUT    | /users/{id}      | Atualizar usuário        |
| DELETE | /users/{id}      | Remover usuário          |

#### 🍽️ Gestão de Restaurantes (`/restaurants`)
| Método | Endpoint                   | Descrição                        |
|--------|----------------------------|----------------------------------|
| POST   | /restaurants               | Criar restaurant                |
| GET    | /restaurants               | Listar restaurantes              |
| GET    | /restaurants/{id}          | Buscar restaurant por ID        |
| PUT    | /restaurants/{idRestaurant}| Atualizar restaurant            |
| DELETE | /restaurants/{id}          | Remover restaurant              |
| PATCH  | /restaurants/owner/{id}    | Atualizar proprietário           |

#### 🍕 Gestão de Cardápio (`/cardapio`)
| Método | Endpoint            | Descrição                |
|--------|---------------------|--------------------------|
| POST   | /cardapio           | Criar item de menu       |
| GET    | /cardapio           | Listar itens do menu     |
| GET    | /cardapio/{idMenu}  | Buscar item por ID       |
| PUT    | /cardapio/{idMenu}  | Atualizar item           |
| DELETE | /cardapio/{idMenu}  | Remover item             |

#### ⚙️ Tipos de Usuários (`/type-users`)
| Método | Endpoint            | Descrição                |
|--------|---------------------|--------------------------|
| POST   | /type-users         | Criar type de usuário    |
| GET    | /type-users         | Listar tipos             |
| GET    | /type-users/{id}    | Buscar type por ID       |
| PUT    | /type-users/{id}    | Atualizar type           |
| DELETE | /type-users/{id}    | Remover type             |

#### 🔐 Autenticação (`/auth`)
| Método | Endpoint         | Descrição                |
|--------|------------------|--------------------------|
| POST   | /auth/login      | Login                    |
| PATCH  | /auth/password   | Alterar password            |

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

## 🚀 5. Configuração e Execução

### 🐳 Docker Compose
O arquivo `docker-compose.yml` orquestra a aplicação e PostgreSQL:

```yaml
services:
  app:
    build:
      context: .                    # Build da aplicação (multi-stage)
    container_name: fortaleza-app   # Name do container
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
    container_name: fortaleza-db    # Name do container
    environment:
      - POSTGRES_USER=postgres      # Usuário do banco
      - POSTGRES_PASSWORD=postgres  # Password do banco
      - POSTGRES_DB=postgres        # Name do database
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


### 🧪 Execução dos Testes Automatizados

O projeto possui cobertura completa de testes unitários e de integração. Para executar os testes:

**Todos os testes (unitários + integração):**
```bash
mvn clean test
```


Relatórios de execução são gerados em `/target/surefire-reports/`.
Consulte detalhes de cobertura, comandos e métricas em [`DOCUMENTACAO_COMPLETA_TESTES.md`](DOCUMENTACAO_COMPLETA_TESTES.md).

---


## 🏆 6. Qualidade e Testes

### 📊 Métricas e Cobertura
- **54 testes automatizados** (unitários e integração)
- **Cobertura total dos principais fluxos de negócio e controllers**
- **Taxa de sucesso**: 96,3% (última execução)
- **Relatórios**: `/target/surefire-reports/`

### 🧪 Estratégia de Testes
O projeto adota uma abordagem de testes em múltiplos níveis:

- **Testes Unitários**: Cobrem regras de negócio (Use Cases), mapeamentos e validações.
- **Testes de Integração**: Validam fluxos completos dos endpoints REST, integração com banco H2 e autenticação.
- **Testes de Controladores**: Simulam requisições HTTP e validam respostas e tratamento de erros.
- **Cobertura de DTOs e Mappers**: Garante integridade na conversão de dados entre camadas.

### 🎯 Boas Práticas
- Clean Architecture e SOLID
- Validação centralizada (Bean Validation)
- Tratamento global de exceções
- Segurança JWT e autenticação
- Documentação automática (Swagger/OpenAPI)

Consulte detalhes completos de cobertura, comandos e exemplos em [`DOCUMENTACAO_COMPLETA_TESTES.md`](DOCUMENTACAO_COMPLETA_TESTES.md).

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
- Arquivo: `collections/Fortaleza_de_Sabor_API.postman_collection.json`

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
- `AuthUseCaseTest`: Validação de autenticação e operações de password
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
- Padronização do name do serviço de banco (`db`)

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


### 7. Collections para Teste
A collection para testes está disponível em:
- Local: `/collections/Fortaleza_de_Sabor_API.postman_collection.json`

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


