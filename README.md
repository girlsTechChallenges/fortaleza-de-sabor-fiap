# Fortaleza de Sabor API

Fortaleza de Sabor é uma API desenvolvida para gerenciar o restaurante Fortaleza de Sabor. Este repositório contém o### Endpoints

### Restaurantes
- **POST** `/restaurants` - Criar restaurante
- **PUT** `/restaurants/{id}` - Atualizar restaurante

### Users
- **POST** `/users` - Criar usuário
- **PUT** `/users/{id}` - Atualizar usuário
- **GET** `/users/{id}` - Buscar usuário por ID
- **GET** `/users` - Listar usuários
- **DELETE** `/users/{id}` - Remover usuário

### Authentication
- **POST** `/auth/login` - Realizar login
- **PATCH** `/auth/password` - Alterar senhae do projeto, que utiliza o framework Spring Boot para criar uma aplicação robusta e escalável.

---

## Arquitetura do Projeto

O projeto segue uma arquitetura em camadas, baseada em princípios de Clean Architecture e DDD (Domain-Driven Design). Para uma visualização detalhada da arquitetura, consulte o [diagrama de arquitetura](diagram.png).

### Principais Camadas:

1. **Camada de Apresentação**:
   - Controllers: Exposição dos endpoints REST da API
   - Controller Docs: Interfaces de documentação Swagger (separadas dos controllers)
   - DTOs: Objetos de transferência de dados
   - Exception Handlers: Tratamento de exceções

2. **Camada de Domínio**:
   - Use Cases: Implementação das regras de negócio
   - Entities: Classes que representam o domínio

3. **Camada de Infraestrutura**:
   - Gateways: Interfaces de acesso a dados
   - Repositories: Implementações JPA
   - Mappers: Conversão entre DTOs e entidades

---

## Tecnologias Utilizadas

- **Java 21**: Linguagem principal do projeto
- **Spring Boot 3.4.5**: Framework para desenvolvimento
- **PostgreSQL**: Banco de dados relacional
- **Hibernate**: Implementação JPA
- **SpringDoc OpenAPI**: Documentação da API
- **H2 Database**: Banco para testes
- **JUnit 5**: Framework de testes
- **Maven**: Gerenciamento de dependências
- **Docker**: Containerização

---

## Estrutura do Projeto

```plaintext
.
├── src/
│   ├── main/
│   │   ├── java/com/br/fiap/fortaleza/sabor/
│   │   │   ├── application/usecase/    # Casos de uso
│   │   │   ├── domain/                 # Entidades de domínio
│   │   │   └── infrastructure/
│   │   │       ├── config/             # Configurações
│   │   │       ├── controller/         # Controllers REST
│   │   │       │   └── docs/          # Interfaces de documentação Swagger
│   │   │       ├── dto/               # DTOs
│   │   │       ├── mapper/            # Mappers
│   │   │       └── persistence/       # Entidades JPA
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── app.key
│   │       └── app.pub
│   └── test/
│       ├── java/                      # Testes unitários
│       └── resources/
│           ├── application-test.properties
│           └── data.sql
├── target/                           # Artefatos gerados
│   ├── fortaleza.sabor-0.0.1-SNAPSHOT.jar
│   └── surefire-reports/            # Relatórios de testes
├── collections/                      # Collection Postman
│   └── Fortaleza_de_Sabor_API_Completa.postman_collection.json
├── DOCUMENTACAO_COMPLETA_TESTES.md  # Documentação consolidada de testes
├── RESUMO_PROJETO.md                # Resumo executivo do projeto
├── architecture.md                  # Documentação da arquitetura
├── doc.md                          # Documentação técnica detalhada
├── docker-compose.yml               # Configuração Docker
└── README.md
```

---

## Execução do Projeto

### Pré-requisitos
- Java 21
- Docker Desktop
- Git

### Passos para Execução

1. **Clone o repositório**:
   ```powershell
   git clone https://github.com/seu-usuario/fortaleza-de-sabor-fiap.git
   cd fortaleza-de-sabor-fiap
   ```

2. **Execute com Docker (Recomendado - Build Automático)**:
   ```powershell
   docker-compose up --build
   ```
   
   > **Nota**: O Docker agora utiliza **multi-stage build**, automatizando a compilação do projeto sem necessidade de build local.

3. **Alternativa - Build Manual + Docker**:
   ```powershell
   ./mvnw clean install
   docker-compose up
   ```

4. **Acesse a aplicação**:
   - API: [http://localhost:8080](http://localhost:8080)
   - Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Melhorias Implementadas

### 🐳 **Docker Multi-stage Build**
- **Problema**: Dependência de build local da máquina
- **Solução**: Build automatizado dentro do container Docker
- **Benefício**: Deploy reproduzível e independente do ambiente

### 🔧 **Configuração PostgreSQL Otimizada**
- **Problema**: Erro na configuração de conexão do banco
- **Solução**: Variáveis de ambiente corrigidas no docker-compose.yml
- **Benefício**: Conexão estável e configurável

### 🚀 **Driver PostgreSQL Explícito**
- **Problema**: Driver class implícito causando inconsistências
- **Solução**: `spring.datasource.driver-class-name=org.postgresql.Driver`
- **Benefício**: Conexão mais confiável e explícita

### 📚 **Swagger Organizado**
- **Solução**: Interfaces de documentação separadas (`docs/`)
- **Benefício**: Código mais limpo e documentação centralizada

### 📁 **Nova Estrutura de Documentação**
```
src/main/java/com/br/fiap/fortaleza/sabor/infrastructure/controller/docs/
├── AuthControllerDocs.java          # Documentação de autenticação
├── UserControllerDocs.java          # Documentação de usuários
└── RestaurantControllerDocs.java    # Documentação de restaurantes
```

---

## Endpoints

### Users
- **POST** `/users` - Criar usuário
- **PUT** `/users/{id}` - Atualizar usuário
- **GET** `/users/{id}` - Buscar usuário por ID
- **GET** `/users` - Listar usuários
- **DELETE** `/users/{id}` - Remover usuário

### Restaurants
- **POST** `/restaurants` - Criar restaurante
- **PUT** `/restaurants/{id}` - Atualizar restaurante

### Authentication
- **POST** `/auth/login` - Realizar login
- **PATCH** `/auth/password` - Alterar senha

---

## Testes

### Cobertura de Testes Unitários
O projeto possui **cobertura abrangente** de testes unitários para todas as camadas:

#### 📊 **Estatísticas Atuais**
- **Total de Classes de Teste**: 34 classes (incluindo utilitárias)
- **Total de Cenários de Teste**: 150+ cenários
- **Taxa de Sucesso**: 100% (todos os testes passando)
- **Cobertura de Código**: Cobertura completa de todas as camadas arquiteturais

#### 🏗️ **Camadas Testadas**
- **Use Cases**: Testes de regras de negócio com 11 classes
- **Controllers**: Testes de endpoints REST (5 classes)
- **Repositories**: Testes de persistência customizada
- **Mappers**: Testes de integridade de conversões (4 classes)
- **DTOs**: Testes de validação Bean Validation (9 classes)
- **Exception Handlers**: Testes de tratamento de exceções

#### 🔍 **Tipos de Teste Implementados**
- **Testes de Sucesso**: Cenários ideais de funcionamento
- **Testes de Casos Extremos**: Dados nulos, vazios, mínimos e máximos
- **Testes de Exceções**: Validação de tratamento de erros
- **Testes de Validação**: Bean Validation em DTOs
- **Testes de Integridade**: Conversões entre camadas preservando dados

#### 📋 **Classes de Teste Principais**

##### **Classes Utilitárias**
- `TestConstants` - Constantes padronizadas
- `TestDataBuilder` - Factory methods para objetos de teste

##### **Use Cases (11 classes)**
- `CreateUserUseCaseTest` - Criação com encoding de senha
- `UpdateUserUseCaseTest` - Atualização com validações
- `DeleteUserUseCaseTest` - Exclusão com verificações
- `GetUserUseCaseTest` - Busca com tratamento de null
- `AuthUserUseCaseTest` - Autenticação de usuários
- `CreateMenuItemUseCaseTest` - Criação de itens de menu
- `UpdateMenuItemUseCaseTest` - Atualização com null handling
- `DeleteMenuItemUseCaseTest` - Exclusão de itens
- `GetMenuItemUseCaseTest` - Busca de itens
- `CreateRestaurantUseCaseTest` - 15 cenários expandidos
- `UpdateRestaurantUseCaseTest` - Atualização de restaurantes

##### **Mappers (4 classes)**
- `UserMapperTest` - Conversões User completas
- `TypeUserMapperTest` - Conversões TypeUser
- `RestaurantMapperTest` - Conversões complexas (endereços + horários)
- `MenuMapperTest` - Conversões MenuItem

##### **DTOs (9 classes - 134 cenários totais)**
- `UserRequestDtoTest` - 11 cenários de validação Bean Validation
- `MenuItemRequestDtoTest` - 10 cenários de validação
- `RestaurantRequestDtoTest` - 13 cenários (listas aninhadas)
- `AddressDtoTest` - 17 cenários (CEP, números positivos)
- `BusinessHoursDtoTest` - 12 cenários (horários, dias da semana)
- `UpdateMenuItemRequestDtoTest` - 19 cenários (atualizações completas)
- `UserCredentialsDtoTest` - 12 cenários (credenciais)
- `UpdateRequestDtoTest` - 17 cenários (atualização de usuário)
- `TypeUserRequestDtoTest` - 14 cenários (tipos de usuário)

### Padrões de Qualidade
- **Estrutura AAA**: Arrange-Act-Assert em todos os testes
- **Nomenclatura Consistente**: Métodos `should[RetornarX]When[CondicaoY]`
- **Mocking Padronizado**: Uso de `@Mock` e `@InjectMocks`
- **Asserções Detalhadas**: Validação completa de objetos e comportamentos
- **Classes Utilitárias**: TestConstants e TestDataBuilder para padronização
- **Documentação**: Todos os testes com `@DisplayName` descritivo

### Melhorias Implementadas Recentemente

#### **🔧 Padronização Completa**
- **TestConstants**: Todas as constantes centralizadas
- **TestDataBuilder**: Factory methods para objetos consistentes
- **Nomenclatura Unificada**: Padrão `shouldXWhenY` em 100% dos testes
- **Estrutura AAA**: Aplicada uniformemente

#### **🎯 Nova Cobertura de Testes**
- **Mappers**: Testes de integridade de dados entre camadas
- **DTOs**: Validação completa Bean Validation (9 classes, 134 cenários)
- **Use Cases**: Correções de comportamento e edge cases
- **Repositories**: Testes de lógica customizada

#### **📊 Validações Implementadas**
- **Conversões de Dados**: Preservação durante transformações
- **Bean Validation**: Testes completos para @NotNull, @NotBlank, @Size, @Pattern, @Email, @Positive
- **Tratamento de Null**: Verificação em todos os cenários
- **Formatos Complexos**: Regex de preços, emails, CEP, padrões de nome
- **Objetos Aninhados**: Validação com @Valid em listas e objetos

### Executar Testes
```powershell
# Executar todos os testes
./mvnw test

# Executar testes de uma classe específica
./mvnw test -Dtest=CreateRestaurantUseCaseTest

# Executar com relatórios detalhados
./mvnw test -Dtest.verbose=true
```

### Relatórios de Testes
Após a execução, os relatórios podem ser encontrados em:
- `target/surefire-reports/` - Relatórios XML e TXT
- Console output com estatísticas detalhadas
- **Documentação completa**: `DOCUMENTACAO_COMPLETA_TESTES.md`

---

## Collections para Teste

### Collection do Postman
**Arquivo**: `collections/Fortaleza_de_Sabor_API_Completa.postman_collection.json`

Esta collection única e consolidada contém **35+ cenários de teste** organizados em:
- 👤 **User Management** (6 endpoints)
- 🍽️ **Restaurant Management** (3 endpoints)  
- 🔐 **Authentication** (3 endpoints)
- 🧪 **Test Scenarios** (23 cenários de validação, erro e segurança)

### Execução dos Testes via Postman

#### 📋 **Ordem Recomendada de Execução**

Para obter os melhores resultados nos testes, execute os endpoints na seguinte ordem:

#### **1. Gestão de Usuários**
```
1. POST /users - Criar usuário (tipo CLIENTE)
2. POST /users - Criar usuário (tipo DONO)
3. GET /users - Listar todos os usuários
4. GET /users/{id} - Buscar usuário por ID
5. PUT /users/{id} - Atualizar usuário
```

#### **2. Autenticação**
```
6. POST /auth/login - Realizar login (obter token)
7. PATCH /auth/password - Alterar senha
```

#### **3. Gestão de Restaurantes**
```
8. POST /restaurants - Criar restaurante (necessário usuário DONO)
9. PUT /restaurants/{id} - Atualizar restaurante
```

#### **4. Cenários de Teste** (Validação de Falhas)
```
10. Error Tests - Validar tratamento de erros
11. Validation Tests - Verificar regras de validação
12. Business Logic Tests - Testar lógica de negócio
13. Security Tests - Testes básicos de segurança
```

#### 🔧 **Configuração da Collection**

1. **Importe a collection**: `Fortaleza_de_Sabor_API_Completa.postman_collection.json`
2. **Variáveis automáticas já configuradas**:
   ```json
   {
     "baseUrl": "http://localhost:8080",
     "authToken": "<auto-preenchido>",
     "userId": "<auto-preenchido>",
     "restaurantId": "<auto-preenchido>"
   }
   ```
3. **Execute em ordem sequencial** ou use "Run Collection" para execução automática

#### 📊 **Cobertura da Collection**

| Categoria | Descrição | Cenários |
|-----------|-----------|----------|
| **Funcionalidades** | CRUD completo de usuários e restaurantes | 12 endpoints |
| **Validação** | Testes de dados inválidos e formatos | 11 cenários |
| **Erro/Negócio** | Recursos inexistentes e conflitos | 8 cenários |
| **Segurança** | XSS, SQL Injection, autenticação | 4 cenários |

#### ⚠️ **Observações Importantes**
- Collection com **automação completa**: captura automática de tokens e IDs
- **Testes de falha incluídos**: validação de cenários de erro esperados
- **Execução única**: todos os cenários em uma collection consolidada
- **Documentação integrada**: assertions e validações automáticas

Para documentação completa dos testes, consulte: **`DOCUMENTACAO_COMPLETA_TESTES.md`**

---

## Artefatos Gerados

### JARs
- Executável: `target/fortaleza.sabor-0.0.1-SNAPSHOT.jar`
- Original: `target/fortaleza.sabor-0.0.1-SNAPSHOT.jar.original`

### Configurações e Chaves
- `target/classes/application.properties`
- `target/classes/app.key`
- `target/classes/app.pub`

---

## Documentação Adicional

### 📚 **Relatórios Técnicos**
- [`RESUMO_PROJETO.md`](RESUMO_PROJETO.md) - **Resumo executivo completo do projeto**
- [`DOCUMENTACAO_COMPLETA_TESTES.md`](DOCUMENTACAO_COMPLETA_TESTES.md) - **Documentação consolidada de testes**
- [`architecture.md`](architecture.md) - Documentação da arquitetura do sistema

### 🏗️ **Arquitetura e Design**
- [`diagram.png`](diagram.png) - Diagrama visual da arquitetura
- [`doc.md`](doc.md) - Documentação técnica adicional

### 📁 **Collections e Recursos**
- `collections/` - Collections do Postman para teste da API
- Configurações Docker para ambiente de desenvolvimento

---

## 📮 Collections Postman

O projeto inclui **4 collections completas** do Postman para diferentes tipos de teste:

### 🗂️ **Collections Disponíveis**

| Collection | Arquivo | Endpoints | Propósito |
|------------|---------|-----------|-----------|
| **🎯 API Completa** | `Fortaleza_de_Sabor_API_Completa.postman_collection.json` | 35+ | Testes completos com cenários de sucesso e falha |

### 🚀 **Como Usar**

1. **Importar no Postman**:
   ```
   Import → File → Selecionar arquivo .json
   ```

2. **Configurar variáveis**:
   ```json
   {
     "baseUrl": "http://localhost:8080"
   }
   ```

3. **Executar testes**:
   - **Individual**: Clicar em "Send" em cada request
   - **Collection**: Usar "Runner" para executar toda a collection
   - **Automático**: Usar Newman CLI para CI/CD

### 📖 **Documentação Detalhada**
👉 **[DOCUMENTACAO_COMPLETA_TESTES.md](DOCUMENTACAO_COMPLETA_TESTES.md)** - Documentação consolidada de testes

### 🎭 **Cenários Cobertos**
- ✅ **Funcionalidades**: CRUD de usuários e restaurantes
- ✅ **Autenticação**: Login, logout, alteração de senha
- ✅ **Validações**: Campos obrigatórios, formatos e limites
- ✅ **Casos de Erro**: Dados inválidos, recursos inexistentes
- ✅ **Segurança**: Testes de autorização e permissões
- ✅ **Casos Extremos**: Situações limites e excepcionais
- ✅ **Integração**: Fluxos completos de usuário

---



