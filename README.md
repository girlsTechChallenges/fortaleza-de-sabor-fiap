# Fortaleza de Sabor API

Uma API REST robusta para gestão de restaurantes e usuários, desenvolvida com Spring Boot e arquitetura Clean Architecture.

## 📊 Status do Projeto

| Aspecto | Status | Detalhes |
|---------|--------|----------|
| **Desenvolvimento** | ✅ MVP Concluído | API REST funcional para gestão de restaurantes e usuários |
| **Arquitetura** | ✅ Clean Architecture | Implementação completa com DDD e separação de camadas |
| **Testes** | ✅ Cobertura Completa | 54 testes (46 unitários + 8 integração) com 100% sucesso |
| **Documentação** | ✅ Atualizada | README, arquitetura e documentação de testes |
| **Containerização** | ✅ Docker Ready | Multi-stage build com PostgreSQL |
| **API Documentation** | ✅ Swagger/OpenAPI | Documentação interativa disponível |

## 🚀 Funcionalidades

### Endpoints Principais

#### 👥 Gestão de Usuários
- **POST** `/users` - Criar usuário (Cliente/Dono)
- **GET** `/users` - Listar todos os usuários  
- **GET** `/users/{id}` - Buscar usuário por ID
- **PUT** `/users/{id}` - Atualizar usuário
- **DELETE** `/users/{id}` - Remover usuário

#### 🍽️ Gestão de Restaurantes
- **POST** `/restaurants` - Criar restaurante (requer usuário DONO)
- **GET** `/restaurants` - Listar restaurantes
- **GET** `/restaurants/{id}` - Buscar restaurante por ID
- **PUT** `/restaurants/{id}` - Atualizar restaurante

#### 🔐 Autenticação
- **POST** `/auth/login` - Realizar login (JWT)
- **PATCH** `/auth/password` - Alterar senha

#### 🍕 Gestão de Cardápio
- **GET** `/cardapio` - Listar itens do menu
- **GET** `/cardapio/{id}` - Buscar item por ID

#### ⚙️ Tipos de Usuários
- **POST** `/type-users` - Criar tipo de usuário
- **GET** `/type-users` - Listar tipos disponíveis

## 🏗️ Arquitetura

O projeto implementa **Clean Architecture** com **Domain-Driven Design**, garantindo:

- 📦 **Separação de responsabilidades** entre camadas
- 🔄 **Baixo acoplamento** e alta coesão
- 🧪 **Testabilidade** facilitada por inversão de dependências
- 📈 **Escalabilidade** e manutenibilidade

### Camadas Principais:

1. **🎯 Camada de Apresentação** (Infrastructure)
   - Controllers REST
   - DTOs e validações Bean Validation
   - Exception Handlers globais
   - Documentação Swagger

2. **💼 Camada de Aplicação** (Application)
   - Use Cases (regras de negócio)
   - Ports (interfaces de entrada e saída)
   - Serviços de domínio

3. **🏛️ Camada de Domínio** (Domain)
   - Entidades de negócio
   - Value Objects
   - Regras de domínio

4. **🔧 Camada de Infraestrutura** (Infrastructure)
   - Repositórios JPA
   - Mappers de conversão
   - Configurações

## 🛠️ Tecnologias

### Backend Core
- **Java 21** - Linguagem principal
- **Spring Boot 3.4.5** - Framework base
- **PostgreSQL** - Banco de dados produção  
- **H2** - Banco de dados para testes

### Segurança e Validação
- **Spring Security** - Autenticação/autorização
- **JWT** - Tokens de acesso seguros
- **Bean Validation** - Validação de entrada

### Testes e Qualidade  
- **JUnit 5** - Framework de testes
- **Mockito** - Mocking para testes unitários
- **REST-assured** - Testes de integração
- **54 testes** (46 unitários + 8 integração)

### DevOps e Build
- **Maven** - Gerenciamento de dependências
- **Docker** - Containerização
- **SpringDoc OpenAPI** - Documentação automática
## 📁 Estrutura do Projeto

```
fortaleza-de-sabor-fiap/
├── 📁 src/main/java/com/br/fiap/fortaleza/sabor/
│   ├── 📁 application/             # Camada de Aplicação
│   │   ├── 📁 ports/              # Interfaces (entrada/saída)
│   │   └── 📁 usecase/            # Regras de negócio
│   ├── 📁 domain/                 # Camada de Domínio
│   │   └── 📁 model/              # Entidades de negócio
│   └── 📁 infrastructure/         # Camada de Infraestrutura
│       ├── 📁 config/             # Configurações Spring
│       ├── 📁 controller/         # Controllers REST
│       │   └── 📁 docs/          # Documentação Swagger
│       ├── 📁 dto/               # Data Transfer Objects
│       ├── 📁 mapper/            # Conversores entre camadas
│       └── 📁 persistence/       # Entidades JPA e Repositórios
├── 📁 src/test/java/              # Testes (54 testes total)
│   ├── 📁 ...unitários/          # 46 testes unitários
│   └── 📁 integration/           # 8 testes de integração REST-assured
├── 📁 collections/               # Collection Postman para testes
├── 📄 README.md                  # Este arquivo
├── 📄 architecture.md            # Documentação da arquitetura
├── 📄 doc.md                     # Documentação técnica
├── 📄 RESUMO_PROJETO.md          # Resumo executivo
├── 📄 DOCUMENTACAO_COMPLETA_TESTES.md # Documentação de testes
├── 🐳 docker-compose.yml         # Configuração Docker
└── 🐳 Dockerfile                 # Multi-stage build
```

## 🚀 Como Executar

### Opção 1: Docker (Recomendado)
```bash
# Clone o repositório
git clone https://github.com/girlsTechChallenges/fortaleza-de-sabor-fiap.git
cd fortaleza-de-sabor-fiap

# Execute com Docker Compose
docker-compose up --build
```

### Opção 2: Build Local + Docker
```bash
# Compile o projeto
./mvnw clean install

# Execute apenas o banco com Docker
docker-compose up database

# Execute a aplicação
./mvnw spring-boot:run
```

### Opção 3: Desenvolvimento Local
```bash
# Configurar PostgreSQL local (porta 5432)
# Configurar variáveis de ambiente conforme application.properties

# Executar aplicação
./mvnw spring-boot:run
```

## 🔗 Acesso à Aplicação

- **API Base URL**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🧪 Executar Testes

### Todos os Testes (Unitários + Integração)
```bash
mvn clean test
```

### Apenas Testes Unitários
```bash
mvn clean test -P unit-tests
```

### Apenas Testes de Integração
```bash
mvn clean test -P integration-tests
```

## 📊 Cobertura de Testes

- **54 testes totais** com 100% de sucesso
- **46 testes unitários** - Todas as camadas (Use Cases, Controllers, Mappers, DTOs)
- **8 testes de integração** - REST-assured com H2 em memória
- **Padrão AAA** (Arrange-Act-Assert) em todos os testes
- **Isolamento completo** entre testes

### Camadas Testadas
- ✅ **Use Cases** (11 classes) - Regras de negócio
- ✅ **Controllers** (5 classes) - Endpoints REST  
- ✅ **Mappers** (4 classes) - Conversões entre camadas
- ✅ **DTOs** (9 classes) - Validação Bean Validation
- ✅ **Repositories** (2 classes) - Persistência customizada
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
## � Collection Postman

### Testes de API Automatizados
- **35+ cenários** organizados em modules
- **Automação completa** com variáveis dinâmicas
- **Validações automáticas** de respostas

#### Grupos de Endpoints
- 👤 **User Management** (6 endpoints)
- 🍽️ **Restaurant Management** (3 endpoints) 
- 🔐 **Authentication** (3 endpoints)
- 🧪 **Test Scenarios** (23 cenários de validação e erro)

### Como Usar
1. **Importe**: `collections/Fortaleza_de_Sabor_API_Completa.postman_collection.json`
2. **Configure**: `baseUrl: http://localhost:8080`
3. **Execute**: Ordem sequencial ou "Run Collection"

## � URLs de Acesso

- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI**: http://localhost:8080/v3/api-docs

## 📚 Documentação

### Documentos Principais
- 📋 **[RESUMO_PROJETO.md](RESUMO_PROJETO.md)** - Resumo executivo completo
- 🧪 **[DOCUMENTACAO_COMPLETA_TESTES.md](DOCUMENTACAO_COMPLETA_TESTES.md)** - Doc. de testes
- 🏗️ **[architecture.md](architecture.md)** - Arquitetura detalhada
- 📄 **[doc.md](doc.md)** - Documentação técnica adicional

### Recursos Visuais
- 📊 **[diagram.png](diagram.png)** - Diagrama da arquitetura
- 📮 **[Collections Postman](collections/)** - Testes automatizados

## � Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## 👨‍💻 Desenvolvido por

**Equipe Girls Tech Challenges**
- Tech Challenge FIAP
- Arquitetura de Software

---

**Fortaleza de Sabor API** - Uma solução robusta, bem testada e documentada para gestão de restaurantes. 🍽️✨

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



