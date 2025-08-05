# Fortaleza de Sabor API

Uma API REST robusta para gestão de restaurantes e usuários, desenvolvida com Spring Boot 3.4.5 e Java 21, implementando Clean Architecture com Domain-Driven Design.

## 📊 Status do Projeto

✅ **Projeto Completo e Funcional**
- **70 testes implementados** (unitários, integração e E2E)
- **5 Controllers** funcionais (User, Auth, Restaurant, Menu, Type)
- **Clean Architecture** implementada com DDD
- **Documentação Swagger** completa
- **Docker ready** com PostgreSQL

## 🚀 Tecnologias Implementadas

- ☕ **Java 21** - Linguagem principal
- 🍃 **Spring Boot 3.4.5** - Framework base
- 🗄️ **PostgreSQL** - Banco de dados produção
- 💾 **H2** - Banco de dados para testes
- 🔒 **Spring Security + JWT** - Autenticação/autorização
- ✅ **Bean Validation** - Validação de entrada
- 🧪 **JUnit 5 + Mockito** - Framework de testes
- 🌐 **REST-assured** - Testes de integração
- 📦 **Maven** - Gerenciamento de dependências
- 🐳 **Docker** - Containerização
- 📖 **SpringDoc OpenAPI** - Documentação automática

## 🚀 Como Executar

### Opção 1: Docker (Recomendado)
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

### Opção 2: Executar Localmente
```bash
# 1. Compile o projeto
mvn clean install

# 2. Execute a aplicação
mvn spring-boot:run
```

## 🔗 Acesso à Aplicação

### URLs Principais:
- **API Base**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **H2 Console** (testes): http://localhost:8080/h2-console

## 🏗️ Arquitetura

O projeto implementa **Clean Architecture** com **Domain-Driven Design**:

### Controllers Implementados:
- **UserController** (`/users`) - Gestão completa de usuários
- **AuthController** (`/auth`) - Autenticação e login
- **RestaurantController** (`/restaurants`) - Gestão de restaurantes  
- **MenuItemsController** (`/cardapio`) - Gestão de cardápio
- **TypeController** (`/type-users`) - Tipos de usuários

### Estrutura de Camadas:
```
🎯 Presentation Layer (Controllers + DTOs)
    ↓
💼 Application Layer (Use Cases + Ports)
    ↓
🏛️ Domain Layer (Entities + Value Objects)
    ↓
🔧 Infrastructure Layer (Repositories + Adapters)
```

## 📊 Funcionalidades Implementadas

### 👥 Gestão de Usuários
- ✅ CRUD completo (Create, Read, Update, Delete)
- ✅ Validação de dados obrigatórios
- ✅ Gestão de endereços múltiplos
- ✅ Tipos de usuário (Cliente/Proprietário)

### 🍽️ Gestão de Restaurantes
- ✅ Cadastro de restaurantes
- ✅ Gestão de proprietários
- ✅ Endereços múltiplos
- ✅ Horários de funcionamento
- ✅ Atualização de dados

### 🔐 Sistema de Autenticação
- ✅ Login com email/senha
- ✅ Validação de credenciais
- ✅ Atualização de senhas
- ✅ Tratamento de erros

### 🍕 Gestão de Cardápio
- ✅ CRUD de itens do menu
- ✅ Controle de preços
- ✅ Gestão de disponibilidade
- ✅ Validações de dados

### ⚙️ Tipos de Usuários
- ✅ CRUD de tipos
- ✅ Gestão de permissões
- ✅ Validações específicas

## 🧪 Testes Implementados

### **Cobertura: 70 testes automatizados**

#### **Testes Unitários** (~46 testes)
- **Controllers**: 5 classes testadas
- **Use Cases**: Todos os casos de uso cobertos
- **Mappers**: 4 mappers testados
- **DTOs**: 13 DTOs com validação Bean Validation
- **Repositories**: Adapters testados

#### **Testes de Integração** (~8 testes)
- **REST-assured**: Endpoints testados com H2
- **Spring Boot Test**: Contexto completo
- **Validação**: Fluxos end-to-end

#### **Testes E2E** (~16 testes)
- **Cenários Completos**: Workflows reais
- **Cucumber BDD**: Testes em português
- **Postman Collection**: 35+ cenários

### Executar Testes:
```bash
# Todos os testes
mvn clean test

# Apenas unitários
mvn test -Punit-tests

# Apenas integração
mvn test -Pintegration-tests

# Apenas E2E
mvn test -Pe2e-tests

# E2E com Cucumber
mvn test -Pe2e-cucumber
```

## 📝 Endpoints da API

### 👤 Gestão de Usuários (`/users`)
| Método | Endpoint     | Descrição           |
|--------|--------------|---------------------|
| POST   | /users       | Criar usuário       |
| GET    | /users       | Listar usuários     |
| GET    | /users/{id}  | Buscar por ID       |
| PUT    | /users/{id}  | Atualizar usuário   |
| DELETE | /users/{id}  | Remover usuário     |

### 🍽️ Gestão de Restaurantes (`/restaurants`)
| Método | Endpoint                      | Descrição                    |
|--------|-------------------------------|------------------------------|
| POST   | /restaurants                  | Criar restaurante            |
| GET    | /restaurants                  | Listar restaurantes          |
| GET    | /restaurants/{id}             | Buscar por ID                |
| PUT    | /restaurants/{idRestaurant}   | Atualizar restaurante        |
| DELETE | /restaurants/{id}             | Remover restaurante          |
| PATCH  | /restaurants/owner/{id}       | Atualizar proprietário       |

### 🍕 Gestão de Cardápio (`/cardapio`)
| Método | Endpoint              | Descrição                 |
|--------|-----------------------|---------------------------|
| POST   | /cardapio             | Criar item de menu        |
| GET    | /cardapio             | Listar itens              |
| GET    | /cardapio/{idMenu}    | Buscar por ID             |
| PUT    | /cardapio/{idMenu}    | Atualizar item            |
| DELETE | /cardapio/{idMenu}    | Remover item              |

### 🔐 Autenticação (`/auth`)
| Método | Endpoint         | Descrição           |
|--------|------------------|---------------------|
| POST   | /auth/login      | Login               |
| PATCH  | /auth/password   | Alterar senha       |

### ⚙️ Tipos de Usuários (`/type-users`)
| Método | Endpoint            | Descrição              |
|--------|---------------------|------------------------|
| POST   | /type-users         | Criar tipo             |
| GET    | /type-users         | Listar tipos           |
| GET    | /type-users/{id}    | Buscar por ID          |
| PUT    | /type-users/{id}    | Atualizar tipo         |
| DELETE | /type-users/{id}    | Remover tipo           |

## 🧪 Testes Automatizados via Postman

O projeto disponibiliza uma collection Postman completa para automação dos testes de API:

**Arquivo**: `collections/Fortaleza_de_Sabor_API.postman_collection.json`

### Como utilizar:
1. **Importe** o arquivo `.json` no Postman
2. **Configure** a variável `baseUrl` para `http://localhost:8080`
3. **Execute** os testes individualmente ou via "Run Collection"

### Cenários Cobertos (35+ testes):
- ✅ **Validação** de campos obrigatórios e formatos
- ✅ **Fluxos** completos de CRUD
- ✅ **Autenticação** e autorização
- ✅ **Casos de erro** e exceções
- ✅ **Cenários extremos** e edge cases

## 📚 Documentação Técnica

- 📖 **[ARCHITECTURE.md](ARCHITECTURE.md)** - Arquitetura detalhada e diagramas
- 📄 **[DOC.md](DOC.md)** - Documentação técnica e exemplos
- 🧪 **[DOCUMENTACAO_COMPLETA_TESTES.md](DOCUMENTACAO_COMPLETA_TESTES.md)** - Estratégia de testes

## 🛠️ Configuração de Desenvolvimento

### Requisitos:
- Java 21+
- Maven 3.8+
- Docker (opcional)
- PostgreSQL (local ou Docker)

### Configuração Local:
```bash
# Configurar PostgreSQL
# application.properties já configurado

# Instalar dependências
mvn clean install

# Executar aplicação
mvn spring-boot:run
```

### Perfis Disponíveis:
- **default** - Produção (PostgreSQL)
- **test** - Testes (H2 em memória)
- **e2e** - E2E (H2 em memória)

## 🏆 Qualidade do Código

### Métricas Implementadas:
- ✅ **Clean Architecture** - Separação de responsabilidades
- ✅ **SOLID Principles** - Aplicados em toda base de código
- ✅ **DDD** - Domain-Driven Design
- ✅ **Bean Validation** - Validação robusta
- ✅ **Exception Handling** - Tratamento global de erros
- ✅ **Swagger Documentation** - API documentada
- ✅ **Test Coverage** - Testes em múltiplas camadas

### Padrões Aplicados:
- **Repository Pattern** - Abstração de dados
- **Mapper Pattern** - Conversão entre camadas
- **DTO Pattern** - Transferência de dados
- **Use Case Pattern** - Lógica de negócio
- **Builder Pattern** - Construção de objetos



## 👨‍💻 Desenvolvido por

**Equipe Girls Tech Challenges**
- Tech Challenge FIAP - Arquitetura de Software
- Implementação: Clean Architecture + DDD
- Linguagem: Java 21 + Spring Boot 3.4.5

---

**Projeto pronto para produção com documentação completa, testes automatizados e arquitetura empresarial.** 🍽️✨







