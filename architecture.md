# Arquitetura do Sistema - Fortaleza de Sabor

## 🏗️ Visão Geral

O **Fortaleza de Sabor** implementa  **Clean Architecture** com **Domain-Driven Design (DDD)**, garantindo:

- 📦 **Separação clara de responsabilidades** entre camadas
- 🔄 **Baixo acoplamento** e alta coesão
- 🧪 **Testabilidade** facilitada por inversão de dependências  
- 📈 **Escalabilidade** e manutenibilidade do código
- 🛡️ **Isolamento de regras de negócio** das tecnologias

## 📊 Tecnologias Implementadas

### Backend Core
- ☕ **Java 21** - Linguagem principal
- 🍃 **Spring Boot 3.4.5** - Framework base  
- 🗄️ **PostgreSQL** - Banco de dados produção
- 💾 **H2** - Banco de dados para testes

### Segurança e Validação
- 🔒 **Spring Security** - Autenticação/autorização
- 🎫 **Spring OAuth2 Resource Server** - JWT tokens
- ✅ **Bean Validation** - Validação de entrada

### Testes e Qualidade
- 🧪 **JUnit 5** - Framework de testes
- 🎭 **Mockito** - Mocking para testes unitários
- 🌐 **REST-assured** - Testes de integração
- 🥒 **Cucumber** - Testes BDD
- 📊 **70 testes** implementados

### DevOps e Build
- 📦 **Maven** - Gerenciamento de dependências
- 🐳 **Docker** - Containerização
- 📖 **SpringDoc OpenAPI** - Documentação automática

## � Controllers Implementados

### 🎯 **UserController** (`/users`)
- **CRUD Completo**: Create, Read, Update, Delete
- **Endpoints**: POST, GET, GET/{id}, PUT/{id}, DELETE/{id}
- **Validações**: Bean Validation em DTOs
- **Documentação**: Swagger separada em `UserControllerDocs`

### 🔐 **AuthController** (`/auth`)
- **Login**: POST `/auth/login` - Autenticação com email/senha
- **Update Password**: PATCH `/auth/password` - Alteração de senha
- **JWT**: Retorna tokens de acesso
- **Validações**: Credenciais e formatos

### 🍽️ **RestaurantController** (`/restaurants`)
- **CRUD Completo**: Gestão completa de restaurantes
- **Owner Management**: PATCH `/restaurants/owner/{id}` - Gestão de proprietários
- **Múltiplos Endereços**: Suporte a endereços múltiplos
- **Horários**: Gestão de horários de funcionamento

### 🍕 **MenuItemsController** (`/cardapio`)
- **CRUD Completo**: Gestão de itens do cardápio
- **Controle de Preços**: Validação de valores
- **Disponibilidade**: Controle de status dos itens
- **Validações**: Dados obrigatórios e formatos

### ⚙️ **TypeController** (`/type-users`)
- **CRUD Completo**: Gestão de tipos de usuários
- **Validações**: Tipos permitidos e formatos
- **Integração**: Vinculação com usuários
    
## 🎯 Princípios Aplicados

### Clean Architecture
- **Regra de Dependência**: Camadas internas não conhecem camadas externas
- **Inversão de Dependência**: Use Cases dependem de abstrações (Ports)
- **Separação de Responsabilidades**: Cada camada tem uma responsabilidade específica
- **Testabilidade**: Facilita criação de testes unitários e de integração

### Domain-Driven Design (DDD)
- **Linguagem Ubíqua**: Conceitos do domínio refletidos no código
- **Entidades de Domínio**: Representam conceitos do negócio
- **Value Objects**: Objetos imutáveis com lógica de domínio
- **Agregados**: Consistência transacional garantida

## 📦 Detalhamento das Camadas

### 🎯 **Camada de Apresentação** (`infrastructure.controller`)
**Responsabilidade**: Interface REST da aplicação

#### Controllers
- **AuthController** - Autenticação e autorização
- **UserController** - Gestão de usuários (CRUD)
- **RestaurantController** - Gestão de restaurantes
- **MenuItemsController** - Gestão de cardápio
- **TypeController** - Tipos de usuários

#### DTOs e Validação
- **Request DTOs**: Validação Bean Validation
- **Response DTOs**: Estrutura de resposta padronizada
- **Global Exception Handler**: Tratamento centralizado de erros

#### Documentação
- **Swagger Interfaces**: Documentação OpenAPI separada dos controllers
- **OpenAPI Configuration**: Configuração centralizada da documentação

## 💼 **Camada de Aplicação** (`application`)

**Responsabilidade**: Orquestração das regras de negócio através de Use Cases e Ports.

### Use Cases Implementados

#### **UserUseCasePort**
```java
public interface UserUseCasePort {
    List<User> getAll();                    // Listar todos
    User save(User user);                   // Criar usuário
    Optional<User> update(Long id, User user); // Atualizar
    Optional<User> getById(Long id);        // Buscar por ID
    Optional<User> deleteById(Long id);     // Deletar
    Optional<User> findByEmail(String email); // Buscar por email
    void updatePassword(String email, String password); // Atualizar senha
}
```

#### **AuthUseCasePort**
```java
public interface AuthUseCasePort {
    Token validateLogin(String email, String password); // Login
    void updatePassword(String email, String password); // Alterar senha
}
```

#### **RestaurantUseCasePort**
```java
public interface RestaurantUseCasePort {
    Restaurant create(Restaurant restaurant);           // Criar
    Optional<Restaurant> update(Long id, Restaurant restaurant); // Atualizar
    List<Restaurant> getAll();                         // Listar todos
    Optional<Restaurant> getById(Long id);            // Buscar por ID
    void deleteById(Long id);                         // Deletar
    Restaurant updateOwner(Long id, String owner, String email); // Atualizar proprietário
}
```

#### **MenuItemsUseCasePort**
```java
public interface MenuItemsUseCasePort {
    MenuItem save(MenuItem menuItem);               // Criar item
    List<MenuItem> getAll();                       // Listar todos
    Optional<MenuItem> getById(Long id);          // Buscar por ID
    Optional<MenuItem> update(Long id, MenuItem menuItem); // Atualizar
    void deleteById(Long id);                     // Deletar
}
```

#### **TypeUseCasePort**
```java
public interface TypeUseCasePort {
    TypeUser create(TypeUser typeUser);           // Criar tipo
    Optional<TypeUser> update(Long id, TypeUser typeUser); // Atualizar
    Optional<TypeUser> deleteById(Long id);       // Deletar
    Optional<TypeUser> getById(Long id);         // Buscar por ID
    List<TypeUser> getAll();                     // Listar todos
}
```

### Ports (Interfaces)

#### **Input Ports** - Interfaces dos Use Cases
- Definem os contratos de entrada para a aplicação
- Implementados pelos Use Cases
- Chamados pelos Controllers

#### **Output Ports** - Interfaces dos Repositórios  
- Definem os contratos de saída para persistência
- Implementados pelos Repository Adapters
- Utilizados pelos Use Cases


## 🏛️ **Camada de Domínio** (`domain.model`)

**Responsabilidade**: Núcleo da aplicação com entidades, value objects e regras de negócio puras, sem dependências externas.

### Entidades Implementadas

#### **User** (Usuário)
```java
public class User {
    private String nome;          // Nome do usuário
    private String email;         // Email único
    private String login;         // Login único  
    private String senha;         // Senha (criptografada)
    private LocalDate dataAlteracao; // Data de modificação
    private String tipo;          // Tipo do usuário
    private List<Address> address; // Endereços múltiplos
}
```

#### **Restaurant** (Restaurante)
```java
public class Restaurant {
    private Long id;
    private String name;          // Nome do restaurante
    private String kitchenType;   // Tipo de cozinha
    private String email;         // Email do restaurante
    private String owner;         // Proprietário
    private List<Address> address; // Endereços múltiplos
    private List<BusinessHours> businessHours; // Horários
}
```

#### **MenuItem** (Item do Cardápio)
```java
public class MenuItem {
    private Long id;
    private String name;          // Nome do item
    private String description;   // Descrição
    private BigDecimal price;     // Preço
    private Boolean available;    // Disponibilidade
    private String image;         // URL da imagem
    private Long restaurantId;    // ID do restaurante
}
```

#### **Address** (Endereço)
```java
public class Address {
    private String street;        // Logradouro
    private String neighborhood;  // Bairro
    private String complement;    // Complemento
    private Integer number;       // Número
    private String state;         // Estado (UF)
    private String city;          // Cidade
    private String zipCode;       // CEP
}
```

#### **TypeUser** (Tipo de Usuário)
```java
public class TypeUser {
    private Long id;
    private String nameType;      // Nome do tipo (CUSTOMER, OWNER)
}
```

### Value Objects

#### **BusinessHours** (Horário de Funcionamento)
```java
public class BusinessHours {
    private DayOfWeek dayOfWeek;     // Dia da semana
    private LocalTime openingTime;   // Horário de abertura
    private LocalTime closingTime;   // Horário de fechamento
    private String observations;     // Observações
}
```

#### **Token** (Token de Autenticação)
```java
public class Token {
    private String accessToken;     // Token JWT
    private Long expiresIn;         // Tempo de expiração
}
```


### 🔧 **Camada de Infraestrutura** (`infrastructure`)
**Responsabilidade**: Implementações técnicas, integração com frameworks, persistência e configurações externas.

#### Adapters e Repositórios
- **UserRepositoryJpa**: Implementa UserRepositoryPort
- **RestaurantRepositoryJpa**: Implementa RestaurantRepositoryPort
- **MenuRepositoryJpa**: Implementa MenuItemsRepositoryPort
- **TypeUserRepositoryJpa**: Implementa TypeRepositoryPort
- **AuthRepositoryJpa**: Implementa AuthRepositoryPort

#### Mappers e Conversão de Dados
```java
// Conversores entre camadas
UserMapper: UserRequestDto ↔ User ↔ UserEntity ↔ UserResponseDto
RestaurantMapper: RestaurantRequestDto ↔ Restaurant ↔ RestaurantEntity ↔ RestaurantResponseDto
MenuMapper: MenuItemRequestDto ↔ MenuItem ↔ MenuItemEntity ↔ MenuItemResponseDto
TypeUserMapper: TypeUserRequestDto ↔ TypeUser ↔ TypeUserEntity ↔ TypeUserResponseDto
AddressMapper: AddressRequestDto ↔ Address ↔ AddressEntity ↔ AddressResponseDto
// Conversão bidirecional entre DTOs, entidades de domínio e entidades JPA
```

#### Entidades JPA (Persistence)
```java
UserEntity, RestaurantEntity, AddressEntity, MenuItemEntity, TypeUserEntity
```

#### Configurações e Integrações
- **SecurityConfig**: Configuração de autenticação JWT, autorização, CORS
- **OpenAPIConfig**: Configuração centralizada da documentação Swagger
- **DatabaseConfig**: Configuração de datasources PostgreSQL/H2
- **ExceptionHandler**: Tratamento global de exceções

#### Outras Responsabilidades
- Integração com frameworks Spring Boot, Spring Data JPA, Bean Validation
- Implementação de serviços externos e utilitários


## 🧪 **Estratégia de Testes Implementada**

O projeto possui **70 testes automatizados** organizados em múltiplos níveis:

### **Testes Unitários** (~46 testes)

#### **Controllers Testados**
- **UserControllerTest** - Endpoints REST de usuários
- **AuthControllerTest** - Endpoints de autenticação  
- **RestaurantControllerTest** - Endpoints REST de restaurantes
- **MenuItemsControllerTest** - Endpoints de cardápio
- **TypeControllerTest** - Endpoints de tipos de usuário

#### **Use Cases Testados**
- **UserUseCaseTest** - Lógica de negócio de usuários
- **AuthUseCaseTest** - Lógica de autenticação
- **RestaurantUseCaseTest** - Lógica de restaurantes
- **MenuItemUseCaseTest** - Lógica de cardápio
- **TypeUseCaseTest** - Lógica de tipos

#### **Mappers Testados**
- **UserMapperTest** - Conversões de usuário
- **RestaurantMapperTest** - Conversões de restaurante
- **MenuMapperTest** - Conversões de cardápio
- **TypeUserMapperTest** - Conversões de tipos

#### **DTOs com Bean Validation**
- **13 DTOs testados** com validações completas
- **Request DTOs**: Validação de entrada
- **Response DTOs**: Estrutura de saída
- **Cenários**: Válidos, inválidos e extremos

#### **Repository Adapters**
- **UserRepositoryPortJpaTest** - Persistência de usuários
- **RestaurantRepositoryPortJpaTest** - Persistência de restaurantes
- **MenuItemRepositoryPortJpaTest** - Persistência de cardápio
- **TypeRepositoryPortJpaTest** - Persistência de tipos

### **Testes de Integração** (~8 testes)

#### **Configuração**
- **Spring Boot Test** - Contexto completo da aplicação
- **REST-assured** - Chamadas HTTP reais
- **H2 Database** - Banco em memória para testes
- **TestContainers** - Ambiente isolado

#### **Controllers Integrados**
- **UserControllerIntegrationTest** - CRUD completo de usuários
- **AuthControllerIntegrationTest** - Fluxo de autenticação
- **RestaurantControllerIntegrationTest** - CRUD de restaurantes
- **MenuItemsControllerIntegrationTest** - Gestão de cardápio

### **Testes E2E** (~16 testes)

#### **Cenários Completos**
- **UserManagementE2ETest** - Workflow completo de usuários
- **AuthenticationE2ETest** - Fluxo de login e autenticação
- **RestaurantManagementE2ETest** - Gestão completa de restaurantes
- **MenuItemManagementE2ETest** - Gestão de cardápio
- **TypeUserManagementE2ETest** - Gestão de tipos
- **CompleteWorkflowE2ETest** - Fluxo completo da aplicação

### **Testes BDD com Cucumber**

#### **Features em Português**
- **user_management.feature** - Gestão de usuários
- **authentication.feature** - Autenticação
- **restaurant_management.feature** - Gestão de restaurantes
- **menu_management.feature** - Gestão de cardápio

#### **Step Definitions**
- **BaseSteps** - Configuração e setup
- **UserManagementSteps** - Steps de usuários
- **AuthenticationSteps** - Steps de autenticação
- **RestaurantManagementSteps** - Steps de restaurantes
- **MenuManagementSteps** - Steps de cardápio

### **Perfis Maven de Teste**

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

### **Cobertura e Relatórios**

#### **Relatórios Gerados**
- **Surefire Reports** - `target/surefire-reports/`
- **Cucumber Reports** - `target/cucumber-html-reports/`
- **JSON Reports** - `target/cucumber-json-reports/`

#### **Métricas de Qualidade**
- ✅ **Use Cases**: 100% dos casos cobertos
- ✅ **Controllers**: 100% dos endpoints testados
- ✅ **Mappers**: 100% das conversões testadas
- ✅ **DTOs**: Validação Bean Validation completa
- ✅ **Integration**: Fluxos end-to-end validados

## ✅ **Benefícios da Arquitetura Implementada**

### 🎯 **Separação de Responsabilidades**
- **5 Controllers** com responsabilidades bem definidas
- **70 testes** organizados por camada
- **Clean Code** com padrões consistentes aplicados
- **Documentação Swagger** separada dos controllers

### 🧪 **Testabilidade Avançada**
- **70 testes implementados** (unitários + integração + E2E)
- **Múltiplos níveis** de validação
- **Cucumber BDD** para testes de aceitação
- **Postman Collection** com 35+ cenários

### 🚀 **Escalabilidade e Flexibilidade**
- **Ports & Adapters** implementados
- **Use Cases** isolados e testáveis
- **Domain Model** rico e expressivo
- **Bean Validation** robusta

### 🔒 **Segurança Empresarial**
- **Spring Security** configurado
- **JWT Tokens** para autenticação
- **Validation** em todas as camadas
- **Exception Handling** global

## 🔄 **Fluxo de Dados Implementado**

### 📥 **Request Flow (Entrada)**
```
1. Cliente HTTP → Controller REST endpoint
2. Controller → DTO Request com Bean Validation  
3. Controller → Use Case (regras de negócio)
4. Use Case → Repository Port (interface)
5. Repository Adapter → Database (PostgreSQL/H2)
```

### 📤 **Response Flow (Saída)**
```
1. Database → Repository Adapter
2. Repository Adapter → Use Case  
3. Use Case → Controller
4. Controller → DTO Response (JSON)
5. Controller → Cliente HTTP
```

### 🔄 **Exemplo Prático: Criar Usuário**
```java
// 1. Controller recebe requisição
@PostMapping("/users")
public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto request) {
    // 2. Mapper converte DTO para Domain
    User user = userMapper.toUserDomain(request);
    
    // 3. Use Case executa regras de negócio
    User savedUser = userUseCasePort.save(user);
    
    // 4. Mapper converte Domain para Response
    UserResponseDto response = userMapper.toUserResponseDto(savedUser);
    
    // 5. Retorna resposta HTTP 201
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
```

## 🏗️ **Estrutura de Pastas Implementada**

```
src/main/java/com/br/fiap/fortaleza/sabor/
├── 📁 application/                    # Camada de Aplicação
│   ├── 📁 ports/
│   │   ├── 📁 in/                    # Input Ports (Use Cases)
│   │   │   ├── UserUseCasePort.java
│   │   │   ├── AuthUseCasePort.java
│   │   │   ├── RestaurantUseCasePort.java
│   │   │   ├── MenuItemsUseCasePort.java
│   │   │   └── TypeUseCasePort.java
│   │   └── 📁 out/                   # Output Ports (Repositories)
│   └── 📁 usecase/                   # Implementação dos Use Cases
├── 📁 domain/                        # Camada de Domínio
│   └── 📁 model/                     # Entidades e Value Objects
│       ├── 📁 user/                  # User, TypeUser
│       ├── 📁 restaurant/            # Restaurant, BusinessHours
│       ├── 📁 menu/                  # MenuItem
│       ├── 📁 address/               # Address
│       └── 📁 token/                 # Token
└── 📁 infrastructure/                # Camada de Infraestrutura
    ├── 📁 adapter/                   # Repository Adapters
    ├── 📁 config/                    # Configurações Spring
    ├── 📁 controller/                # Controllers REST
    │   ├── 📁 docs/                  # Interfaces Swagger
    │   └── 📁 dto/                   # DTOs Request/Response
    ├── 📁 exception/                 # Exception Handlers
    ├── 📁 mapper/                    # Conversores entre camadas
    └── 📁 persistence/               # JPA Entities e Repositories
```

## 📊 **Métricas de Qualidade Alcançadas**

### Cobertura de Testes Implementada
- ✅ **Controllers**: 5/5 classes (100%)
- ✅ **Use Cases**: 5/5 interfaces (100%)
- ✅ **Mappers**: 4/4 classes (100%)
- ✅ **DTOs**: 13/13 classes principais (100%)
- ✅ **Repository Adapters**: 4/4 classes (100%)

### Padrões Arquiteturais Aplicados
- ✅ **Clean Architecture**: Implementação completa
- ✅ **DDD**: Entidades e Value Objects implementados
- ✅ **SOLID**: Princípios aplicados consistentemente
- ✅ **Ports & Adapters**: Inversão de dependências

### Qualidade do Código Implementada
- ✅ **Nomenclatura**: Padrões consistentes aplicados
- ✅ **Separação**: Responsabilidades bem definidas
- ✅ **Testabilidade**: 70 testes implementados
- ✅ **Documentação**: Swagger completo + documentação técnica

---

**Esta arquitetura implementa um sistema robusto, testável e mantível, seguindo as melhores práticas de desenvolvimento de software empresarial com Clean Architecture e DDD.** 🏗️✨
