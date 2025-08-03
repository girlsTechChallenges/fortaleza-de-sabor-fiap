# Testes de Integração - Fortaleza de Sabor

Este documento descreve a implementação e execução dos testes de integração para o projeto Fortaleza de Sabor.

## 📋 Visão Geral

Os testes de integração foram implementados usando **REST-assured** para validar a integração real entre os componentes da aplicação, garantindo que os endpoints funcionem corretamente e que as dependências estejam conectadas como esperado.

## 🏗️ Arquitetura dos Testes

### Estrutura dos Pacotes

```
src/test/java/
└── com/br/fiap/fortaleza/sabor/integration/
    ├── config/
    │   └── BaseIntegrationTest.java          # Configuração base para testes
    ├── controller/
    │   ├── AuthControllerIntegrationTest.java    # Testes do controller de autenticação
    │   ├── UserControllerIntegrationTest.java    # Testes do controller de usuários
    │   ├── RestaurantControllerIntegrationTest.java # Testes do controller de restaurantes
    │   ├── MenuItemsControllerIntegrationTest.java  # Testes do controller de menu
    │   └── TypeControllerIntegrationTest.java       # Testes do controller de tipos
    ├── suite/
    │   └── CompleteWorkflowIntegrationTest.java     # Suite completa de testes
    └── util/
        └── TestDataFactory.java             # Factory para dados de teste
```

### Recursos de Teste

```
src/test/resources/
├── application-test.properties              # Configuração específica para testes
└── sql/
    ├── insert-test-user.sql                 # Scripts SQL para inserir dados de teste
    ├── insert-multiple-users.sql
    ├── insert-test-restaurant.sql
    ├── insert-test-restaurants.sql
    ├── insert-test-menu-item.sql
    ├── insert-test-menu-items.sql
    ├── insert-test-type-user.sql
    ├── insert-test-type-users.sql
    └── cleanup.sql                          # Script de limpeza dos dados
```

## 🔧 Configuração do Ambiente

### Dependências Adicionadas

```xml
<!-- REST-assured para testes de API -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.3.2</version>
    <scope>test</scope>
</dependency>

<!-- JSON Path para validação de respostas JSON -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>json-path</artifactId>
    <version>5.3.2</version>
    <scope>test</scope>
</dependency>

<!-- JSON Schema Validator -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>json-schema-validator</artifactId>
    <version>5.3.2</version>
    <scope>test</scope>
</dependency>

<!-- Spring Security Test -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

### Banco de Dados de Teste

Os testes utilizam **H2 Database** em memória para isolamento completo:

```properties
# In-memory H2 Database for tests
spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
```

## 🧪 Tipos de Testes Implementados

### 1. Testes de Controller Individual

Cada controller possui testes específicos que validam:

- **Cenários de sucesso**: Operações CRUD básicas
- **Cenários de erro**: Validação de entrada, recursos não encontrados
- **Validação de dados**: Formatos inválidos, campos obrigatórios
- **Performance**: Tempo de resposta dos endpoints

### 2. Testes de Integração Completa

A suite `CompleteWorkflowIntegrationTest` valida:

- **Ciclo de vida completo** de usuários
- **Fluxo de gestão** de restaurantes
- **Integração entre serviços** (Usuário → Restaurante → Menu)
- **Testes de performance** com alto volume de operações

### 3. Validações Implementadas

- ✅ **Status codes HTTP** corretos
- ✅ **Formato das respostas JSON**
- ✅ **Headers HTTP** apropriados
- ✅ **Tempo de resposta** (< 2-5 segundos dependendo da operação)
- ✅ **Validação de campos** obrigatórios
- ✅ **Integridade referencial** entre entidades

## 🚀 Execução dos Testes

### Perfis Maven Configurados

#### 1. Executar apenas testes unitários (padrão)
```bash
mvn test
# ou
mvn test -P unit-tests
```

#### 2. Executar apenas testes de integração
```bash
mvn verify -P integration-tests
```

#### 3. Executar todos os testes
```bash
mvn verify -P all-tests
```

### Comandos Específicos

#### Executar um teste específico
```bash
mvn test -Dtest=AuthControllerIntegrationTest
```

#### Executar testes com logs detalhados
```bash
mvn verify -P integration-tests -Dspring.profiles.active=test -X
```

#### Executar testes em paralelo
```bash
mvn verify -P integration-tests -Dmaven.test.parallel=classes
```

## 📊 Exemplos de Testes

### Teste de Autenticação
```java
@Test
@DisplayName("Should authenticate user successfully with valid credentials")
void shouldAuthenticateUserSuccessfully() throws Exception {
    var credentials = TestDataFactory.createValidCredentials();

    given()
            .contentType(ContentType.JSON)
            .body(objectMapper.writeValueAsString(credentials))
    .when()
            .post("/auth/login")
    .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("token", notNullValue())
            .body("type", equalTo("Bearer"))
            .time(lessThan(2000L));
}
```

### Teste de CRUD Completo
```java
@Test
@DisplayName("Complete User Lifecycle: Create -> Login -> Update -> Delete")
void shouldCompleteUserLifecycleSuccessfully() throws Exception {
    // 1. Create User
    Response createResponse = given()
            .contentType(ContentType.JSON)
            .body(objectMapper.writeValueAsString(userRequest))
            .when()
            .post("/users")
            .then()
            .statusCode(201)
            .extract().response();

    Long userId = createResponse.jsonPath().getLong("id");
    
    // 2. Login, 3. Update, 4. Delete...
}
```

## 📈 Métricas e Relatórios

### Cobertura de Testes
- **Controllers**: 100% dos endpoints cobertos
- **Cenários positivos**: Todos os fluxos principais
- **Cenários negativos**: Validações e tratamento de erros
- **Integração**: Comunicação entre camadas

### Performance
- **Tempo médio de resposta**: < 2 segundos
- **Operações concorrentes**: Suporte testado
- **Alto volume**: 10+ operações simultâneas

## 🛡️ Isolamento e Limpeza

### Estratégias de Isolamento
- **Banco H2 em memória**: Criado e destruído a cada execução
- **Scripts SQL**: Dados de teste específicos por cenário
- **@Sql annotations**: Controle fino de dados por teste
- **Transações**: Rollback automático após cada teste

### Limpeza Automática
```sql
-- cleanup.sql
DELETE FROM address WHERE user_id IN (1, 2, 3);
DELETE FROM restaurant_address WHERE restaurant_id IN (1, 2, 3);
DELETE FROM business_hours WHERE restaurant_id IN (1, 2, 3);
DELETE FROM users WHERE id IN (1, 2, 3);
DELETE FROM restaurants WHERE id IN (1, 2, 3);
DELETE FROM menu_items WHERE id IN (1, 2, 3);
DELETE FROM type_users WHERE id IN (1, 2, 3);
```

## 🔍 Debugging e Troubleshooting

### Logs Detalhados
```properties
# Logging configuration for tests
logging.level.com.br.fiap.fortaleza.sabor=INFO
logging.level.org.springframework.web=INFO
logging.level.org.springframework.security=INFO
```

### REST-assured Logging
```java
RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
```

### Execução com Debug
```bash
mvn verify -P integration-tests -Dmaven.surefire.debug
```

## 🎯 Cenários de Teste Cobertos

### AuthController
- ✅ Login com credenciais válidas
- ✅ Login com credenciais inválidas
- ✅ Atualização de senha
- ✅ Validação de formato de dados
- ✅ Requisições concorrentes

### UserController
- ✅ Criação de usuários
- ✅ Listagem de usuários
- ✅ Busca por ID
- ✅ Atualização de dados
- ✅ Exclusão de usuários
- ✅ Validação de duplicatas

### RestaurantController
- ✅ Gestão completa de restaurantes
- ✅ Atualização de proprietário
- ✅ Validação de dados de negócio
- ✅ Integração com endereços e horários

### MenuItemsController
- ✅ Gestão de cardápio
- ✅ Validação de preços
- ✅ Disponibilidade de itens
- ✅ Associação com restaurantes

### TypeController
- ✅ Tipos de usuários
- ✅ Hierarquia de permissões
- ✅ Validação de duplicatas

## 📋 Próximos Passos

### Melhorias Futuras
- [ ] Testes de segurança (autenticação JWT)
- [ ] Testes de performance com JMeter
- [ ] Testes de carga e stress
- [ ] Integração com Testcontainers
- [ ] Testes de API versioning
- [ ] Testes de rate limiting

### Integração CI/CD
```yaml
# Exemplo para GitHub Actions
- name: Run Integration Tests
  run: mvn verify -P integration-tests
```

## 🤝 Contribuição

Para adicionar novos testes de integração:

1. **Seguir o padrão** da classe `BaseIntegrationTest`
2. **Usar TestDataFactory** para dados de teste
3. **Implementar cleanup** adequado
4. **Validar status codes, headers e tempo de resposta**
5. **Documentar cenários específicos**

---

**Nota**: Estes testes de integração garantem que o sistema funcione corretamente como um todo, validando a comunicação entre componentes e a integridade dos dados em cenários reais de uso.
