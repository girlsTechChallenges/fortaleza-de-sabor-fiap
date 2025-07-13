# 📋 **Documentação Completa de Testes - Fortaleza de Sabor**

## 🎯 **Visão Geral**

Este documento consolida toda a documentação relacionada aos testes do projeto Fortaleza de Sabor, incluindo testes unitários, testes de API via Postman, padronização e cobertura de testes.

---

## 📊 **Resumo Executivo dos Testes**

### **Status Geral**
- **Testes Unitários**: 15 classes principais com 100% de sucesso
- **Testes de API**: 35+ cenários via Postman Collection
- **Cobertura**: Todas as camadas da arquitetura testadas
- **Padrão de Qualidade**: AAA (Arrange-Act-Assert) em 100% dos testes
- **Padronização**: Estrutura consistente aplicada

### **Objetivos Alcançados**
1. ✅ **Cobertura Completa**: Todas as classes principais possuem testes
2. ✅ **Padronização**: Estrutura consistente em todos os testes
3. ✅ **Qualidade**: Testes robustos com cenários reais e extremos
4. ✅ **Manutenibilidade**: Código de teste organizado e bem documentado
5. ✅ **Automação**: Collection Postman com validações automáticas

---

## 🧪 **Testes Unitários**

### **Classes de Teste Implementadas (15 total)**

#### **Application Layer (Use Cases)**
1. **`CreateUserUseCaseTest`** - Criação de usuários
2. **`DeleteUserUseCaseTest`** - Exclusão de usuários
3. **`UpdateUserUseCaseTest`** - Atualização de usuários
4. **`GetUserUseCaseTest`** - Busca de usuários
5. **`AuthUserUseCaseTest`** - Autenticação de usuários
6. **`CreateRestaurantUseCaseTest`** - Criação de restaurantes (15 cenários)
7. **`UpdateRestaurantUseCaseTest`** - Atualização de restaurantes

#### **Infrastructure Layer (Controllers)**
8. **`UserControllerTest`** - Endpoints REST de usuários
9. **`AuthControllerTest`** - Endpoints de autenticação
10. **`RestaurantControllerTest`** - Endpoints REST de restaurantes

#### **Infrastructure Layer (Gateways)**
11. **`UserRepositoryJpaTest`** - Persistência JPA de usuários
12. **`RestaurantRepositoryJpaTest`** - Persistência JPA de restaurantes

#### **Infrastructure Layer (Mappers)**
13. **`UserEntityMapperTest`** - Conversões usuário
14. **`RestaurantMapperTest`** - Conversões restaurante

#### **Exception Handling**
15. **`GlobalExceptionHandlerTest`** - Tratamento global de exceções

### **Cobertura Expandida - CreateRestaurantUseCase**

**Evolução**: De 2 cenários para **15 cenários abrangentes**

#### **Cenários de Negócio Reais (4)**
- ✅ Validação com email de proprietário específico
- ✅ Nomes com caracteres especiais (acentos, símbolos)
- ✅ Tipos de cozinha com descrições longas
- ✅ Restaurantes com múltiplas unidades/endereços

#### **Casos de Dados Variados (5)**
- ✅ Lista de endereços vazia
- ✅ Horários de funcionamento vazios
- ✅ Horários completos da semana (7 dias)
- ✅ Dados mínimos válidos
- ✅ Campos nulos individuais

#### **Cenários de Exceção (4)**
- ✅ UserNotFoundException (usuário não existe)
- ✅ RestaurantAlreadyExistsException (restaurante já existe)
- ✅ RuntimeException (erros gerais de sistema)
- ✅ Propagação correta de exceções

### **Validações de Dados Complexos**
- **Endereços Múltiplos**: Restaurantes com mais de um endereço
- **Horários Completos**: Todos os dias da semana com horários específicos
- **Caracteres Especiais**: Acentos, símbolos e caracteres UTF-8
- **Limites de Campo**: Dados mínimos e máximos para campos de texto

---

## 📮 **Testes de API - Collection Postman**

### **Collection Única Consolidada**
- **Arquivo**: `Fortaleza_de_Sabor_API_Completa.postman_collection.json`
- **Total**: 35+ cenários de teste
- **Cobertura**: Funcionalidade, validação, falhas e segurança

### **Grupos de Endpoints**

#### **👤 User Management (6 endpoints)**
- ✅ Create User (Cliente)
- ✅ Create User (Dono Restaurante)
- ✅ Get All Users
- ✅ Get User by ID
- ✅ Update User
- ✅ Delete User

#### **🍽️ Restaurant Management (3 endpoints)**
- ✅ Create Restaurant
- ✅ Create Restaurant (Multiple Addresses)
- ✅ Update Restaurant

#### **🔐 Authentication (3 endpoints)**
- ✅ Login User
- ✅ Login Restaurant Owner
- ✅ Update Password

#### **🧪 Test Scenarios (23 cenários organizados)**

##### **Error Tests (3 testes)**
- ❌ Create User - Invalid Email
- ❌ Create Restaurant - Nonexistent Owner
- ❌ Login - Invalid Credentials

##### **Validation Tests (6 testes)**
- ❌ Create User - Short Password
- ❌ Create User - Invalid CEP
- ❌ Create User - Invalid Name Pattern
- ❌ Create User - Short Login
- ❌ Create Restaurant - Short Kitchen Type
- ❌ Create Restaurant - Invalid Time Format

##### **Business Logic Tests (5 testes)**
- ❌ Create User - Duplicate Email
- ❌ Update Non-existent User
- ❌ Delete Non-existent User
- ❌ Get Non-existent User
- ❌ Update Non-existent Restaurant

##### **Edge Cases (2 testes)**
- ⚠️ Create Restaurant - Empty Business Hours
- ⚠️ Create User - Minimal Data

##### **Malformed Requests (5 testes)**
- ❌ Create User - Missing Required Fields
- ❌ Create User - Invalid JSON
- ❌ Create Restaurant - Empty Name
- ❌ Login - Empty Body
- ❌ Update Password - Invalid Email

##### **Security Tests (3 testes)**
- 🔒 Access Without Authentication
- 🔒 SQL Injection Test - Login
- 🔒 XSS Test - User Creation

### **Status Codes Cobertos**

#### **✅ Sucessos (200/201/202/204)**
- 12 cenários de operações normais
- Criação, leitura, atualização de recursos
- Autenticação e alteração de senhas

#### **❌ Erros de Cliente (400)**
- 11 cenários de validação
- Dados inválidos, malformados ou ausentes
- Formatos incorretos de campos

#### **🔒 Não Autorizado (401)**
- 2 cenários de autenticação
- Login com credenciais inválidas
- Acesso sem token válido

#### **❌ Não Encontrado (404)**
- 6 cenários de recursos inexistentes
- Operações em IDs inválidos
- Emails não cadastrados

#### **⚠️ Conflito (409)**
- 1 cenário de conflito
- Tentativa de duplicação de email

---

## 🔧 **Padronização de Testes**

### **Padrões Aplicados Uniformemente**

#### **1. Estrutura de Annotations**
- **Padrão**: `@ExtendWith(MockitoExtension.class)` com `@Mock` e `@InjectMocks`
- **Aplicado**: 100% dos testes unitários

#### **2. Nomenclatura de Métodos**
- **Formato**: `should[Ação][Condição/Resultado]`
- **Exemplos**:
  - `shouldCreateUser()`
  - `shouldGetAllUsers()`
  - `shouldHandleNullFields()`

#### **3. Estrutura AAA (Arrange-Act-Assert)**
- **Comentários padronizados**:
  - `// Arrange` - Preparação dos dados
  - `// Act` - Execução da ação
  - `// Assert` - Verificação dos resultados
  - `// Act & Assert` - Para testes combinados

#### **4. Display Names**
- Mantidas as annotations `@DisplayName` para documentação clara

### **Arquivos Padronizados**

#### **Use Cases (Application Layer)**
- ✅ `CreateUserUseCaseTest.java`
- ✅ `DeleteUserUseCaseTest.java`
- ✅ `UpdateUserUseCaseTest.java`
- ✅ `AuthUserUseCaseTest.java`
- ✅ `GetUserUseCaseTest.java`
- ✅ `CreateRestaurantUseCaseTest.java`
- ✅ `UpdateRestaurantUseCaseTest.java`

#### **Controllers (Infrastructure Layer)**
- ✅ `UserControllerTest.java`
- ✅ `AuthControllerTest.java`
- ✅ `RestaurantControllerTest.java`

#### **Repositories (Infrastructure Layer)**
- ✅ `UserRepositoryJpaTest.java`
- ✅ `RestaurantRepositoryJpaTest.java`

#### **Mappers (Infrastructure Layer)**
- ✅ `UserEntityMapperTest.java`
- ✅ `RestaurantMapperTest.java`

---

## 🚀 **Como Executar os Testes**

### **Testes Unitários**

#### **Via Maven**
```bash
# Executar todos os testes
mvn test

# Executar com limpeza
mvn clean test

# Executar com relatório detalhado
mvn test -Dtest.verbose=true
```

#### **Via IDE**
- Executar classe individual: Clique direito na classe → "Run Tests"
- Executar método específico: Clique direito no método → "Run Test"
- Executar todos: Clique direito na pasta test → "Run All Tests"

### **Testes de API (Postman)**

#### **Importação da Collection**
1. Abrir Postman
2. **Import** → **File** → Selecionar `Fortaleza_de_Sabor_API_Completa.postman_collection.json`
3. Configurar variável `baseUrl` se necessário

#### **Ordem Recomendada**
1. **👤 User Management** → Criar usuários base
2. **🔐 Authentication** → Obter tokens de acesso
3. **🍽️ Restaurant Management** → Criar e gerenciar restaurantes
4. **🧪 Test Scenarios** → Validar cenários de erro e falhas

#### **Execução Automática**
- Clique em "Run Collection"
- Selecione todos os testes
- Execute para validação abrangente

---

## 📊 **Automação e Recursos**

### **Testes Unitários**
- ✅ **Mocks automáticos** com Mockito
- ✅ **Injeção de dependências** automática
- ✅ **Assertions robustas** com AssertJ
- ✅ **Cobertura de exceções** completa

### **Testes de API**
- ✅ **Auto-extraction** de tokens de autenticação
- ✅ **Auto-storage** de IDs criados
- ✅ **Auto-validation** de respostas
- ✅ **Auto-testing** com assertions
- ✅ **Variables management** dinâmico

### **Validações Automáticas**
- ✅ Schema de resposta
- ✅ Códigos de status esperados
- ✅ Estrutura de dados
- ✅ Regras de negócio
- ✅ Tempo de resposta

---

## 🎭 **Cenários Cobertos**

### **Funcionalidades Básicas**
- [x] Criação de usuários (CLIENTE e DONO)
- [x] Autenticação e autorização
- [x] Gestão de restaurantes
- [x] Operações CRUD completas
- [x] Validação de dados

### **Regras de Negócio**
- [x] Apenas DONOs podem criar restaurantes
- [x] Múltiplos endereços por restaurante
- [x] Horários de funcionamento complexos
- [x] Validação de email único
- [x] Gestão de senhas

### **Cenários de Erro e Validação**
- [x] Dados inválidos e malformados
- [x] Recursos inexistentes (404)
- [x] Falhas de autenticação (401)
- [x] Violações de regras de negócio
- [x] Testes de segurança básica (XSS, SQL Injection)
- [x] Validações de campo específicas

### **Casos Extremos**
- [x] Dados mínimos obrigatórios
- [x] Campos vazios
- [x] Limites de validação
- [x] Requests malformados

---

## 🔧 **Troubleshooting**

### **Testes Unitários**
1. **Falhas de Mock**: Verificar se `@Mock` e `@InjectMocks` estão corretos
2. **Dependências não injetadas**: Confirmar uso de `@ExtendWith(MockitoExtension.class)`
3. **Assertions falhando**: Verificar dados de teste e expectativas

### **Testes de API**
1. **Servidor não rodando**: Verificar `http://localhost:8080`
2. **Ordem de execução**: Execute na sequência recomendada
3. **Variáveis não preenchidas**: Execute os testes na ordem correta
4. **Testes de falha passando**: Verifique se as validações estão corretas

### **Verificação Rápida**
- GET `/users` deve retornar 200
- POST `/auth/login` deve retornar token válido
- Variáveis `{{baseUrl}}`, `{{userId}}`, `{{authToken}}` devem estar preenchidas

---

## 📈 **Métricas de Qualidade**

### **Cobertura de Código**
- **Application Layer**: 100% (7 classes)
- **Infrastructure Layer**: 100% (7 classes)
- **Exception Handling**: 100% (1 classe)
- **Total**: 15 classes testadas

### **Cenários de Teste**
- **Testes Unitários**: 50+ métodos de teste
- **Testes de API**: 35+ cenários
- **Cobertura Total**: 85+ cenários únicos

### **Tipos de Validação**
- **Funcionais**: 70% dos testes
- **Exceções**: 20% dos testes
- **Segurança**: 10% dos testes

---

## 📚 **Documentação Relacionada**

- 📖 **README.md** → Visão geral do projeto
- 🏗️ **architecture.md** → Documentação da arquitetura
- 📋 **RESUMO_PROJETO.md** → Resumo executivo
- � **doc.md** → Documentação técnica detalhada

---

**🎉 Documentação Completa de Testes Consolidada!**

Este documento unifica toda a estratégia de testes do projeto Fortaleza de Sabor, fornecendo visão completa sobre testes unitários, testes de API, padronização e cobertura. Com esta documentação, qualquer desenvolvedor pode entender, executar e expandir os testes do projeto de forma consistente e eficiente.
