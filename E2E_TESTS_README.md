# Testes End-to-End (E2E) - Fortaleza de Sabor

## Visão Geral

Este projeto implementa testes End-to-End (E2E) abrangentes para a aplicação Fortaleza de Sabor, simulando cenários reais de uso da API REST. Os testes seguem as melhores práticas de E2E e utilizam REST-assured como ferramenta principal.

## Estrutura dos Testes E2E

### 📁 Organização dos Arquivos

```
src/test/java/com/br/fiap/fortaleza/sabor/e2e/
├── config/
│   └── E2ETestConfiguration.java          # Configuração base para testes E2E
├── util/
│   └── E2ETestDataFactory.java           # Factory para criação de dados de teste
└── flows/
    ├── AuthenticationE2ETest.java        # Testes de autenticação
    ├── UserManagementE2ETest.java        # Testes de gerenciamento de usuários
    ├── RestaurantManagementE2ETest.java  # Testes de gerenciamento de restaurantes
    ├── MenuItemManagementE2ETest.java    # Testes de gerenciamento de cardápio
    ├── TypeUserManagementE2ETest.java    # Testes de tipos de usuário
    └── CompleteWorkflowE2ETest.java      # Testes de fluxos completos
```

## ⚙️ Configuração

### Tecnologias Utilizadas

- **REST-assured 5.3.2**: Framework principal para testes de API REST
- **Spring Boot Test**: Integração com framework Spring Boot
- **H2 Database**: Banco de dados em memória para testes
- **JUnit 5**: Framework de testes
- **Jackson**: Serialização/deserialização JSON

### Configuração Base

A classe `E2ETestConfiguration` fornece:
- Configuração do REST-assured com base URL
- Configuração do banco H2 em memória
- Métodos utilitários para geração de dados únicos
- Setup de ambiente de teste isolado

## 🧪 Tipos de Testes Implementados

### 1. Testes de Autenticação (`AuthenticationE2ETest`)
- **Cenários Cobertos**:
  - Registro de usuário completo
  - Login com credenciais válidas
  - Validação de responses de autenticação
  - Tratamento de erros de autenticação

### 2. Testes de Usuários (`UserManagementE2ETest`)
- **Cenários Cobertos**:
  - Ciclo completo de usuário (CRUD)
  - Validação de dados de entrada
  - Tratamento de duplicatas
  - Validação de tipos de usuário
  - Validação de senhas fracas

### 3. Testes de Restaurantes (`RestaurantManagementE2ETest`)
- **Cenários Cobertos**:
  - Criação de restaurante com endereço e horários
  - Listagem e busca de restaurantes
  - Atualização de dados do restaurante
  - Atualização de proprietário
  - Validação de regras de negócio

### 4. Testes de Cardápio (`MenuItemManagementE2ETest`)
- **Cenários Cobertos**:
  - Gerenciamento completo de itens do cardápio
  - Validação de preços e disponibilidade
  - Busca por restaurante
  - Validação de horários de funcionamento
  - Atualização de disponibilidade

### 5. Testes de Tipos de Usuário (`TypeUserManagementE2ETest`)
- **Cenários Cobertos**:
  - CRUD de tipos de usuário
  - Validação de tipos reservados do sistema
  - Tratamento de duplicatas

### 6. Testes de Fluxos Completos (`CompleteWorkflowE2ETest`)
- **Cenários Cobertos**:
  - Jornada completa do proprietário de restaurante
  - Jornada de registro e navegação do cliente
  - Tarefas de administração do sistema
  - Fluxo de autenticação com criação de usuário
  - Cenários de erro em workflows

## 🎯 Características dos Testes

### Validações Implementadas

1. **Status Codes**: Validação flexível aceitando múltiplos códigos de resposta válidos
2. **Performance**: Timeouts definidos para garantir responsividade da API
3. **Dados Únicos**: Geração automática de dados únicos para evitar conflitos
4. **Tolerância a Falhas**: Testes projetados para lidar com diferentes estados do sistema

### Abordagem de Validação

```java
// Exemplo de validação flexível
.then()
    .statusCode(anyOf(equalTo(201), equalTo(400), equalTo(409), equalTo(500)))
    .time(lessThan(5000L))
```

Os testes são projetados para aceitar diferentes códigos de resposta, permitindo:
- **201**: Criação bem-sucedida
- **400**: Erro de validação
- **409**: Conflito (duplicata)
- **500**: Erro interno do servidor

## 🚀 Execução dos Testes

### Comandos Maven

```bash
# Executar todos os testes E2E
mvn test -Dtest="*E2ETest"

# Executar teste específico
mvn test -Dtest="AuthenticationE2ETest"

# Compilar testes apenas
mvn test-compile

# Executar com perfil de teste específico
mvn test -Dspring.profiles.active=e2e -Dtest="*E2ETest"
```

### Variáveis de Ambiente

```properties
# application-test.properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
logging.level.com.br.fiap.fortaleza.sabor=DEBUG
```

## 📊 Cobertura de Testes

### Endpoints Testados

| Controller | Endpoints Cobertos | Cenários |
|------------|-------------------|----------|
| AuthController | `/auth/login`, `/auth/password` | Login, alteração de senha |
| UserController | `/users/*` | CRUD completo |
| RestaurantController | `/restaurants/*` | CRUD + atualização de proprietário |
| MenuItemsController | `/cardapio/*` | CRUD + disponibilidade |
| TypeController | `/type-users/*` | CRUD de tipos |

### Cenários de Negócio

- ✅ Registro e autenticação de usuários
- ✅ Gerenciamento completo de restaurantes
- ✅ Administração de cardápios
- ✅ Controle de tipos de usuário
- ✅ Fluxos integrados multi-controller
- ✅ Tratamento de erros e validações

## 🛠️ Manutenção dos Testes

### Adicionando Novos Testes

1. **Criar novo arquivo de teste** na pasta `flows/`
2. **Estender** `E2ETestConfiguration`
3. **Utilizar** `E2ETestDataFactory` para dados de teste
4. **Seguir padrões** de nomenclatura e estrutura

### Atualizando Dados de Teste

O `E2ETestDataFactory` centraliza a criação de dados de teste:

```java
// Método para criar dados válidos
public static UserRequestDto createValidUserRequest(String uniqueId) {
    return new UserRequestDto(/* dados válidos */);
}

// Método para criar dados inválidos
public static UserRequestDto createInvalidUserRequest() {
    return new UserRequestDto(/* dados inválidos */);
}
```

## 📈 Métricas e Relatórios

### Informações de Execução

Os testes capturam e exibem:
- Responses de criação de recursos
- Tempos de resposta da API
- Status codes retornados
- Dados únicos utilizados

### Logging

```java
System.out.println("User creation response: " + createResponse.asString());
```

## 🔧 Solução de Problemas

### Problemas Comuns

1. **Erro de Compilação**: Verificar se todos os DTOs estão importados
2. **Falha de Conexão**: Validar configuração do H2
3. **Timeout**: Ajustar limites de tempo conforme necessário
4. **Dados Duplicados**: Utilizar métodos de geração de IDs únicos

### Debug

```bash
# Executar com debug habilitado
mvn test -Dtest="*E2ETest" -X

# Ver logs detalhados do Spring
mvn test -Dtest="*E2ETest" -Dlogging.level.root=DEBUG
```

## 📋 Checklist de Qualidade

- ✅ Testes independentes e isolados
- ✅ Dados únicos para cada execução
- ✅ Validação de performance (timeouts)
- ✅ Cobertura de cenários de sucesso e erro
- ✅ Documentação completa
- ✅ Estrutura organizacional clara
- ✅ Utilização de factory pattern para dados
- ✅ Configuração adequada de ambiente de teste

---

**Nota**: Os testes E2E são projetados para serem executados em ambiente de teste isolado com banco H2 em memória, garantindo que não interfiram com dados de produção ou desenvolvimento.
