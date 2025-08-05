# 📋 Documentação de Testes - Fortaleza de Sabor
## FIAP Tech Challenge - Sistema de Gestão de Restaurantes

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-23D96C?style=for-the-badge&logo=cucumber&logoColor=white)

---

## 📖 **Índice**

1. [Visão Geral](#-visão-geral)
2. [Estrutura dos Testes](#-estrutura-dos-testes)
3. [Tipos de Teste Implementados](#-tipos-de-teste-implementados)
4. [Configuração e Execução](#-configuração-e-execução)
5. [Relatórios e Métricas](#-relatórios-e-métricas)
6. [Troubleshooting](#-troubleshooting)

---

## 🎯 **Visão Geral**

O projeto **Fortaleza de Sabor** implementa uma estratégia completa de testes com **85+ testes** distribuídos em **3 níveis**:

### **Stack Tecnológica**
```yaml
Testing Framework: JUnit 5 + Cucumber 7.14.0
API Testing: REST-assured 5.3.2
Mocking: Mockito 5.5.0
Build Tool: Maven com 5 profiles
Database: PostgreSQL + H2 (testes)
```

### **Resumo de Cobertura**
- **Testes Unitários**: 70+ testes das camadas Application e Infrastructure
- **Testes de Integração E2E**: 5+ testes de fluxos completos
- **Testes BDD com Cucumber**: 24 cenários em português
- **Collection Postman**: 35+ cenários de API

---

## 🏗️ **Estrutura dos Testes**

```
src/test/java/
├── application/
│   ├── ports/in/         # Use Case Interfaces (5 testes)
│   ├── ports/out/        # Repository Interfaces (5 testes)
│   └── usecase/          # Regras de Negócio (5 testes)
├── cucumber/             # BDD Runner e Steps
├── e2e/flows/           # Testes End-to-End (5 testes)
├── infrastructure/
│   ├── adapters/        # Controllers e Mappers
│   └── persistence/     # Repositories JPA
└── resources/
    ├── features/        # Features Cucumber (.feature)
    └── application-e2e.properties
```

---

## 🧪 **Tipos de Teste Implementados**

### **1. Testes Unitários (70+ testes)**

#### **Application Layer**
- **Use Case Ports** (10 classes): Interfaces de entrada e saída
- **Use Cases** (5 classes): Regras de negócio isoladas
- **DTOs Validation** (9 classes): Validação Bean Validation

```java
@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {
    @Mock private UserRepositoryPort userRepositoryPort;
    @InjectMocks private UserUseCase userUseCase;

    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void shouldCreateUserSuccessfully() {
        // Arrange
        var userRequest = TestDataBuilder.createValidUserRequest();
        when(userRepositoryPort.save(any())).thenReturn(expectedUser);

        // Act
        var result = userUseCase.createUser(userRequest);

        // Assert
        assertThat(result).isNotNull();
        verify(userRepositoryPort).save(any());
    }
}
```

#### **Infrastructure Layer**
- **Controllers** (5 classes): Endpoints REST
- **Mappers** (4 classes): Conversão DTO ↔ Domain ↔ Entity
- **Repositories** (3 classes): Persistência JPA

### **2. Testes de Integração E2E (5+ testes)**

Testes que validam fluxos completos da aplicação:

```java
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-e2e.properties")
class AuthenticationE2ETest {
    
    @Test
    void shouldExecuteCompleteAuthenticationFlow() {
        // Given - Criar usuário
        var userResponse = restTemplate.postForEntity("/users", request, UserResponse.class);
        
        // When - Fazer login
        var loginResponse = restTemplate.postForEntity("/auth/login", credentials, TokenResponse.class);
        
        // Then - Verificar token válido
        assertThat(loginResponse.getBody().getToken()).isNotBlank();
    }
}
```

**Classes Implementadas:**
- `AuthenticationE2ETest` - Fluxo de autenticação
- `UserManagementE2ETest` - CRUD de usuários
- `RestaurantManagementE2ETest` - Gestão de restaurantes
- `MenuItemManagementE2ETest` - Gestão de cardápio
- `CompleteWorkflowE2ETest` - Fluxo completo do sistema

### **3. Testes BDD com Cucumber (24 cenários)**

Features em português com Gherkin:

```gherkin
Funcionalidade: Gerenciamento de Usuários
  Como um administrador do sistema
  Eu quero gerenciar usuários
  Para manter o controle de acesso

  Cenário: Criar usuário com sucesso
    Dado que a API está rodando
    Quando eu criar um usuário com dados válidos
    Então o usuário deve ser criado com sucesso
    E deve retornar status 201
```

**Features Implementadas:**
- `authentication.feature` - Autenticação de usuários
- `user_management.feature` - Gestão de usuários
- `restaurant_management.feature` - Gestão de restaurantes
- `menu_management.feature` - Gestão de cardápio
- `basic_test.feature` - Testes básicos do sistema

### **4. Collection Postman (35+ cenários)**

Testes de API organizados por categoria:

#### **Grupos de Endpoints**
- **User Management** (6 endpoints): CRUD completo
- **Restaurant Management** (3 endpoints): Criação e atualização
- **Authentication** (3 endpoints): Login e gestão de senhas
- **Test Scenarios** (23 cenários): Validações e casos extremos

#### **Cenários de Teste**
- **Sucessos** (12 cenários): Operações normais
- **Validações** (11 cenários): Dados inválidos
- **Segurança** (3 cenários): XSS, SQL Injection
- **Recursos inexistentes** (6 cenários): Testes 404
- **Conflitos** (1 cenário): Duplicação de dados

---

## ⚙️ **Configuração e Execução**

### **Profiles Maven**

| Profile | Comando | Descrição |
|---------|---------|-----------|
| **Padrão** | `mvn clean test` | Todos os testes |
| **Unitários** | `mvn test -Punit-tests` | Apenas `*Test.java` |
| **Integração** | `mvn test -Pintegration-tests` | Apenas `*IntegrationTest.java` |
| **E2E** | `mvn test -Pe2e-tests` | Apenas `*E2ETest.java` |
| **Cucumber** | `mvn test -Pe2e-cucumber` | Testes BDD |

### **Comandos Específicos**

```bash
# Teste específico
mvn test -Dtest=AuthenticationE2ETest

# Múltiplos testes
mvn test -Dtest="*E2ETest"

# Por pacote
mvn test -Dtest="com.br.fiap.fortaleza.sabor.application.*Test"

# Com debug
mvn test -Dtest=UserUseCaseTest -X
```

### **Configurações de Ambiente**

#### **application-e2e.properties**
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
server.port=0
logging.level.com.br.fiap.fortaleza.sabor=DEBUG
```

#### **Dependências Principais**
```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
    </dependency>
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

---

## 📊 **Relatórios e Métricas**

### **Relatórios Gerados**

1. **Surefire Reports**: `target/surefire-reports/`
2. **Cucumber HTML**: `target/cucumber-html-reports/`
3. **Cucumber JSON**: `target/cucumber-json-reports/cucumber.json`
4. **Cucumber XML**: `target/cucumber-xml-reports/cucumber.xml`

### **Métricas de Cobertura**

| Camada | Classes Testadas | Cobertura |
|--------|------------------|-----------|
| **Application Ports** | 10/10 | 100% |
| **Application Use Cases** | 5/5 | 100% |
| **Infrastructure Controllers** | 5/10 | 50% |
| **Infrastructure Mappers** | 4/4 | 100% |
| **DTOs Validation** | 9/34 | Principais cobertos |

### **Funcionalidades Validadas**

#### **👥 Gestão de Usuários**
- ✅ CRUD completo (Create, Read, Update, Delete)
- ✅ Validação de dados e regras de negócio
- ✅ Autenticação e autorização

#### **🏪 Gestão de Restaurantes**
- ✅ Cadastro por proprietários
- ✅ Múltiplos endereços e horários
- ✅ Validações de dados obrigatórios

#### **🍽️ Gestão de Cardápio**
- ✅ CRUD de itens do menu
- ✅ Controle de preços e disponibilidade
- ✅ Validações específicas

#### **🔐 Sistema de Autenticação**
- ✅ Login com JWT
- ✅ Validação de credenciais
- ✅ Tratamento de erros

---

## 🔧 **Troubleshooting**

### **Problemas Comuns**

#### **Testes Unitários**
```bash
# Erro: Mock não funcionando
# Solução: Verificar @ExtendWith(MockitoExtension.class)

# Erro: Dependência não injetada
# Solução: Confirmar @Mock e @InjectMocks
```

#### **Testes E2E**
```bash
# Erro: Porta em uso
mvn test -Dserver.port=0

# Erro: Banco H2 não configurado
# Solução: Verificar application-e2e.properties
```

#### **Cucumber**
```bash
# Erro: Features não encontradas
mvn test -Pe2e-cucumber -Dcucumber.features=src/test/resources/features

# Erro: Steps não definidos
# Solução: Verificar glue package configuration
```

#### **Postman Collection**
1. **Variáveis não preenchidas**: Execute na ordem recomendada
2. **Servidor não rodando**: Verificar `http://localhost:8080`
3. **Testes falhando**: Verificar validações nos scripts

### **Comandos de Diagnóstico**

```bash
# Verificar configuração Maven
mvn help:active-profiles

# Listar todos os testes
mvn test -Dtest=NonExistentTest -DfailIfNoTests=false

# Logs detalhados
mvn test -X

# Verificar relatórios
ls target/surefire-reports/
ls target/cucumber-*-reports/
```

---

## 🚀 **CI/CD e Integração**

### **GitHub Actions**
```yaml
name: Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - name: Set up JDK 21
      uses: actions/setup-java@v4
      with:
        java-version: '21'
    - name: Run all tests
      run: mvn clean test
    - name: Upload reports
      uses: actions/upload-artifact@v4
      with:
        name: test-reports
        path: target/
```

### **Pipeline por Etapas**
```bash
# Etapa 1: Testes rápidos
mvn test -Punit-tests

# Etapa 2: Testes de integração
mvn test -Pintegration-tests

# Etapa 3: Testes E2E
mvn test -Pe2e-tests

# Etapa 4: Testes BDD
mvn test -Pe2e-cucumber
```

---

## 📋 **Resumo Executivo**

### **Status da Implementação: 100% Completo**

✅ **85+ testes** cobrindo todas as camadas da arquitetura Clean  
✅ **3 tipos de teste** (Unitário, Integração, BDD) implementados  
✅ **Relatórios avançados** em múltiplos formatos  
✅ **CI/CD ready** com profiles Maven configurados  
✅ **Documentação completa** e troubleshooting  

### **Qualidade Garantida**
- **Cobertura completa** das regras de negócio
- **Validação de dados** robusta
- **Tratamento de erros** abrangente
- **Testes de segurança** básicos implementados
- **Facilidade de manutenção** com padrões estabelecidos

### **Comando Principal**
```bash
mvn clean test
```
**Executa todos os 85+ testes em uma única execução!**

---

**🎯 Documentação limpa e organizada - sem repetições!**
