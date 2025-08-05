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
Desenvolver um **backend completo e robusto** utilizando **Spring Boot 3.4.5** e **Java 21**, com foco em:

#### Funcionalidades Core Implementadas
- 👥 **Gestão de Usuários**: CRUD completo (Cliente/Proprietário)
- 🍽️ **Gestão de Restaurantes**: Criação, atualização e gestão
- 🔐 **Autenticação**: Login seguro com JWT
- 🍕 **Gestão de Cardápio**: CRUD completo de itens
- ⚙️ **Tipos de Usuários**: Gestão de permissões

#### Características Técnicas Implementadas
- 🏗️ **Clean Architecture** com DDD
- 🐳 **Docker** ready com multi-stage build
- 🧪 **70 testes** (unitários + integração + E2E)
- 📊 **Documentação Swagger** completa
- 🔒 **Spring Security** + JWT + Bean Validation

---

## 🏗️ 2. Arquitetura do Sistema Implementada

### Visão Arquitetural
O projeto implementa **Clean Architecture** com **Domain-Driven Design**, garantindo:

```
🎯 Camada de Apresentação (Controllers)
    ↓ (UserController, AuthController, RestaurantController, MenuItemsController, TypeController)
💼 Camada de Aplicação (Use Cases)  
    ↓ (UserUseCasePort, AuthUseCasePort, RestaurantUseCasePort, MenuItemsUseCasePort, TypeUseCasePort)
🏛️ Camada de Domínio (Entities)
    ↓ (User, Restaurant, MenuItem, Address, TypeUser, BusinessHours, Token)
🔧 Camada de Infraestrutura (Adapters)
    ↓ (Repository Adapters, Mappers, Configurations)
🗄️ Banco de Dados (PostgreSQL/H2)
```

### Controllers Implementados

#### 🎯 **UserController** (`/users`)
```java
@RestController
@RequestMapping("/users")
public class UserController implements UserControllerDocs {
    
    @PostMapping    // Criar usuário
    @GetMapping     // Listar usuários  
    @GetMapping("/{id}")  // Buscar por ID
    @PutMapping("/{id}")  // Atualizar
    @DeleteMapping("/{id}")  // Deletar
}
```

#### 🔐 **AuthController** (`/auth`)
```java
@RestController  
@RequestMapping("/auth")
public class AuthController implements AuthControllerDocs {
    
    @PostMapping("/login")     // Login com email/senha
    @PatchMapping("/password") // Atualizar senha
}
```

#### 🍽️ **RestaurantController** (`/restaurants`)
```java
@RestController
@RequestMapping("/restaurants") 
public class RestaurantController implements RestaurantControllerDocs {
    
    @PostMapping              // Criar restaurante
    @GetMapping               // Listar restaurantes
    @GetMapping("/{id}")      // Buscar por ID
    @PutMapping("/{idRestaurant}")  // Atualizar
    @DeleteMapping("/{id}")   // Deletar
    @PatchMapping("/owner/{id}")  // Atualizar proprietário
}
```

#### 🍕 **MenuItemsController** (`/cardapio`)
```java
@RestController
@RequestMapping("/cardapio")
public class MenuItemsController implements MenuItemControllerDocs {
    
    @PostMapping              // Criar item
    @GetMapping               // Listar itens
    @GetMapping("/{idMenu}")  // Buscar por ID
    @PutMapping("/{idMenu}")  // Atualizar item
    @DeleteMapping("/{idMenu}")  // Deletar item
}
```

#### ⚙️ **TypeController** (`/type-users`)
```java
@RestController
@RequestMapping("/type-users")
public class TypeController implements TypeUserControllerDocs {
    
    @PostMapping              // Criar tipo
    @GetMapping               // Listar tipos
    @GetMapping("/{id}")      // Buscar por ID
    @PutMapping("/{id}")      // Atualizar tipo
    @DeleteMapping("/{id}")   // Deletar tipo
}
```

### Fluxo de Requisição Implementado
```
1. 📥 Cliente HTTP → Controller (endpoint REST)
2. 📋 Controller → DTO Request (validação Bean Validation)
3. 💼 Controller → Use Case (regras de negócio)
4. 🔗 Use Case → Repository Port (interface)  
5. 🗄️ Repository Adapter → Database (PostgreSQL/H2)
6. 📤 Response → Cliente (JSON)
```

---

## 3. Endpoints Implementados da API

### Tabela Resumo dos Endpoints

| Controller | Base Path | Endpoints | Funcionalidades |
|------------|-----------|-----------|-----------------|
| **UserController** | `/users` | 5 endpoints | CRUD completo de usuários |
| **AuthController** | `/auth` | 2 endpoints | Login e atualização de senha |
| **RestaurantController** | `/restaurants` | 6 endpoints | CRUD + gestão de proprietários |
| **MenuItemsController** | `/cardapio` | 5 endpoints | CRUD completo de cardápio |
| **TypeController** | `/type-users` | 5 endpoints | CRUD de tipos de usuários |

### Detalhamento dos Endpoints

#### 👤 Gestão de Usuários (`/users`)
| Método | Endpoint         | Descrição                | Status Codes |
|--------|------------------|--------------------------|--------------|
| POST   | /users           | Criar usuário            | 201, 400, 409, 500 |
| GET    | /users           | Listar todos os usuários | 200, 500 |
| GET    | /users/{id}      | Buscar usuário por ID    | 200, 404, 500 |
| PUT    | /users/{id}      | Atualizar usuário        | 200, 400, 404, 500 |
| DELETE | /users/{id}      | Remover usuário          | 204, 404, 500 |

#### 🍽️ Gestão de Restaurantes (`/restaurants`)
| Método | Endpoint                   | Descrição                        | Status Codes |
|--------|----------------------------|----------------------------------|--------------|
| POST   | /restaurants               | Criar restaurante                | 201, 400, 409, 500 |
| GET    | /restaurants               | Listar restaurantes              | 200, 500 |
| GET    | /restaurants/{id}          | Buscar restaurante por ID        | 200, 404, 500 |
| PUT    | /restaurants/{idRestaurant}| Atualizar restaurante            | 202, 400, 404, 409, 500 |
| DELETE | /restaurants/{id}          | Remover restaurante              | 204, 404, 500 |
| PATCH  | /restaurants/owner/{id}    | Atualizar proprietário           | 200, 404, 422, 500 |

#### 🍕 Gestão de Cardápio (`/cardapio`)
| Método | Endpoint            | Descrição                | Status Codes |
|--------|---------------------|--------------------------|--------------|
| POST   | /cardapio           | Criar item de menu       | 201, 400, 409, 500 |
| GET    | /cardapio           | Listar itens do menu     | 200, 404, 500 |
| GET    | /cardapio/{idMenu}  | Buscar item por ID       | 202, 404, 500 |
| PUT    | /cardapio/{idMenu}  | Atualizar item           | 202, 400, 404, 500 |
| DELETE | /cardapio/{idMenu}  | Remover item             | 204, 404, 500 |

#### ⚙️ Tipos de Usuários (`/type-users`)
| Método | Endpoint            | Descrição                | Status Codes |
|--------|---------------------|--------------------------|--------------|
| POST   | /type-users         | Criar tipo de usuário    | 201, 400, 409, 500 |
| GET    | /type-users         | Listar tipos             | 200, 500 |
| GET    | /type-users/{id}    | Buscar tipo por ID       | 200, 404, 500 |
| PUT    | /type-users/{id}    | Atualizar tipo           | 202, 400, 404, 409, 500 |
| DELETE | /type-users/{id}    | Remover tipo             | 204, 404, 500 |

#### 🔐 Autenticação (`/auth`)
| Método | Endpoint         | Descrição                | Status Codes |
|--------|------------------|--------------------------|--------------|
| POST   | /auth/login      | Login                    | 200, 401, 404, 500 |
| PATCH  | /auth/password   | Alterar senha            | 204, 400, 404, 500 |

## 🛠️ Tecnologias Implementadas

### Backend Core
- ☕ **Java 21** - Linguagem principal
- 🍃 **Spring Boot 3.4.5** - Framework base
- 🗄️ **PostgreSQL** - Banco de dados produção
- 💾 **H2** - Banco de dados para testes
- 🔄 **Lombok** - Redução de boilerplate

### Segurança e Validação
- 🔒 **Spring Security** - Autenticação/autorização
- 🎫 **Spring OAuth2 Resource Server** - JWT tokens
- ✅ **Bean Validation** - Validação de entrada
- 🛡️ **CORS** - Configuração de segurança

### Persistência e Dados
- 🏪 **Spring Data JPA** - Acesso a dados
- 🔄 **Hibernate** - ORM
- 📊 **H2 Console** - Interface para testes
- 🐳 **Docker Compose** - Orquestração de serviços

### Testes e Qualidade
- 🧪 **JUnit 5** - Framework de testes
- 🎭 **Mockito** - Mocking para testes unitários
- 🌐 **REST-assured** - Testes de integração
- 🥒 **Cucumber** - Testes BDD
- 📊 **70 testes** implementados

### Documentação e DevOps
- 📖 **SpringDoc OpenAPI** - Documentação automática
- 📦 **Maven** - Gerenciamento de dependências
- 🐳 **Docker** - Containerização
- ⚙️ **Spring Actuator** - Monitoramento

### Dependências Principais (pom.xml)
```xml
<dependencies>
    <!-- Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    </dependency>
    
    <!-- Database -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>
    
    <!-- Documentation -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.7.0</version>
    </dependency>
    
    <!-- Testing -->
    <dependency>
        <groupId>io.cucumber</groupId>
        <artifactId>cucumber-java</artifactId>
        <version>7.14.0</version>
    </dependency>
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.3.2</version>
    </dependency>
</dependencies>
```

## 🚀 4. Configuração e Execução

### 🐳 Docker Compose (Recomendado)
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
mvn clean install

# 2. Execute apenas o banco
docker-compose up database

# 3. Execute a aplicação
mvn spring-boot:run
```

#### Opção 3: Desenvolvimento Local  
```bash
# 1. Configure PostgreSQL local (porta 5432)
# 2. Configure variáveis de ambiente no application.properties
# 3. Execute a aplicação
mvn spring-boot:run
```

### 🔧 Configurações de Ambiente

#### **application.properties** (Produção)
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server Configuration  
server.port=8080

# Actuator
management.endpoints.web.exposure.include=health,info
```

#### **application-test.properties** (Testes)
```properties
# H2 Database for Testing
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# H2 Console
spring.h2.console.enabled=true
```

## 🏆 5. Qualidade e Testes Implementados

### 📊 Métricas e Cobertura Implementada
- **70 testes automatizados** (unitários + integração + E2E)
- **Cobertura total** dos principais fluxos de negócio e controllers
- **Taxa de sucesso**: 100% (última execução)
- **Relatórios**: `/target/surefire-reports/`, `/target/cucumber-html-reports/`

### 🧪 Estratégia de Testes Implementada

#### **Testes Unitários** (~46 testes)
- **Controllers**: 5 classes testadas (UserController, AuthController, etc.)
- **Use Cases**: Todos os casos de uso implementados testados
- **Mappers**: 4 mappers testados (conversão entre camadas)
- **DTOs**: 13 DTOs com validação Bean Validation
- **Repository Adapters**: Persistência testada

#### **Testes de Integração** (~8 testes)
- **REST-assured**: Endpoints testados com H2 em memória
- **Spring Boot Test**: Contexto completo da aplicação
- **Fluxos Completos**: Validação end-to-end
- **Base Classes**: `BaseIntegrationTest` para reutilização

#### **Testes E2E** (~16 testes)
- **Cenários Completos**: Workflows reais da aplicação
- **Múltiplos Controllers**: Testes integrados
- **Validação Completa**: Desde requisição até resposta

### 🥒 Testes BDD com Cucumber

#### **Features Implementadas** (em português)
- **`user_management.feature`** - Gestão de usuários
- **`authentication.feature`** - Autenticação e login
- **`restaurant_management.feature`** - Gestão de restaurantes
- **`menu_management.feature`** - Gestão de cardápio

#### **Step Definitions Implementadas**
- **`BaseSteps`** - Configuração e setup
- **`UserManagementSteps`** - Steps de usuários
- **`AuthenticationSteps`** - Steps de autenticação
- **`RestaurantManagementSteps`** - Steps de restaurantes
- **`MenuManagementSteps`** - Steps de cardápio

### 🎯 Comandos de Execução de Testes

#### **Perfis Maven Implementados**
```bash
# Todos os testes (padrão)
mvn clean test

# Apenas testes unitários
mvn test -Punit-tests

# Apenas testes de integração
mvn test -Pintegration-tests

# Apenas testes E2E
mvn test -Pe2e-tests

# Testes E2E com Cucumber
mvn test -Pe2e-cucumber
```

#### **Relatórios de Testes Gerados**
- **Surefire Reports**: `target/surefire-reports/index.html`
- **Cucumber HTML**: `target/cucumber-html-reports/index.html`
- **Cucumber JSON**: `target/cucumber-json-reports/cucumber.json`

### 🎭 Boas Práticas Implementadas
- ✅ **Clean Architecture** e SOLID
- ✅ **Bean Validation** centralizada
- ✅ **Exception Handling** global
- ✅ **JWT Security** e autenticação
- ✅ **Swagger Documentation** automática
- ✅ **Docker** ready para produção

## 📚 6. Recursos Adicionais Implementados

### 📖 Documentação Swagger Completa
- **URL**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **Controllers Documentados**: 5 controllers com interfaces separadas
- **DTOs Documentados**: Request e Response com exemplos

### 📮 Collection Postman Implementada
- **Arquivo**: `collections/Fortaleza_de_Sabor_API.postman_collection.json`
- **35+ cenários** organizados em módulos
- **Automação completa** com variáveis dinâmicas  
- **Testes de validação** automáticos
- **Cenários de erro** incluídos

#### **Como utilizar a Collection:**
1. **Importar** o arquivo `.json` no Postman
2. **Configurar** a variável `baseUrl` para `http://localhost:8080`
3. **Executar** os testes individualmente ou via "Run Collection"

#### **Cenários Cobertos na Collection:**
- ✅ **Validação** de campos obrigatórios e formatos
- ✅ **Fluxos completos** de CRUD
- ✅ **Autenticação** e autorização
- ✅ **Casos de erro** e exceções
- ✅ **Cenários extremos** e edge cases

### 🔗 URLs de Acesso Implementadas
- **API Base**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **H2 Console** (testes): http://localhost:8080/h2-console
- **Actuator Health**: http://localhost:8080/actuator/health

### 📄 Documentação Técnica Disponível
- 📖 **[README.md](README.md)** - Guia principal de uso
- 🏗️ **[ARCHITECTURE.md](ARCHITECTURE.md)** - Arquitetura detalhada
- 🧪 **[DOCUMENTACAO_COMPLETA_TESTES.md](DOCUMENTACAO_COMPLETA_TESTES.md)** - Documentação de testes

### 🛠️ Configurações de Desenvolvimento

#### **Requisitos Implementados:**
- ✅ Java 21+
- ✅ Maven 3.8+
- ✅ Docker (opcional)
- ✅ PostgreSQL (local ou Docker)

#### **Perfis Configurados:**
- **default** - Produção (PostgreSQL)
- **test** - Testes (H2 em memória)
- **e2e** - E2E (H2 em memória)

---

## 🎉 Conclusão

O **Fortaleza de Sabor API** representa uma solução **robusta, escalável e bem testada** para gestão de restaurantes, implementando:

### ✨ **Características Técnicas Implementadas**
- 🏗️ **Clean Architecture** com DDD aplicada
- 🧪 **70 testes automatizados** funcionais
- 🔒 **Spring Security** com JWT implementado
- 📊 **Swagger Documentation** completa
- 🐳 **Docker** ready para produção

### 🚀 **Funcionalidades Implementadas**
- 👥 **Gestão de Usuários** - CRUD completo
- 🔐 **Sistema de Autenticação** - Login e segurança
- 🍽️ **Gestão de Restaurantes** - CRUD + proprietários
- 🍕 **Gestão de Cardápio** - CRUD de itens
- ⚙️ **Tipos de Usuários** - Controle de permissões

### 📊 **Qualidade Garantida**
- ✅ **Controllers**: 5/5 implementados e testados
- ✅ **Use Cases**: Todos os casos de uso cobertos
- ✅ **Mappers**: Conversões entre camadas testadas
- ✅ **DTOs**: Validação Bean Validation implementada
- ✅ **Integration**: Fluxos end-to-end validados

**Uma API pronta para produção que demonstra as melhores práticas de desenvolvimento de software empresarial com Spring Boot 3.4.5 e Java 21.** 🍽️✨

---

## 8. Repositório e Código Fonte

### URL do Repositório
[https://github.com/girlsTechChallenges/fortaleza-de-sabor-fiap.git](https://github.com/girlsTechChallenges/fortaleza-de-sabor-fiap.git)

### Branch Principal
- **main** - Código estável e funcional
- **feature/e2e** - Implementações e melhorias

### Estrutura do Código
```
src/main/java/com/br/fiap/fortaleza/sabor/
├── application/     # Use Cases e Ports
├── domain/          # Entidades de Domínio
└── infrastructure/  # Controllers, DTOs, Mappers, Adapters
```


