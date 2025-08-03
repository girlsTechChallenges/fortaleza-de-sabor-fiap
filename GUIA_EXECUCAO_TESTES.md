# 🧪 Guia de Execução de Testes - Fortaleza de Sabor

## 📋 Comandos de Teste Disponíveis

### 🚀 **Comando Principal (Executa TODOS os testes)**
```bash
mvn clean test
```
**Executa**: Testes Unitários + Testes de Integração + Testes E2E

---

## 🎯 **Comandos por Categoria**

### 1️⃣ **Apenas Testes Unitários**
```bash
mvn clean test -P unit-tests
```
**Executa**: Todos os arquivos `*Test.java` (excluindo `*IntegrationTest.java` e `*E2ETest.java`)

### 2️⃣ **Apenas Testes de Integração**  
```bash
mvn clean test -P integration-tests
```
**Executa**: Todos os arquivos `*IntegrationTest.java`

### 3️⃣ **Apenas Testes E2E**
```bash
mvn clean test -P e2e-tests
```
**Executa**: Todos os arquivos `*E2ETest.java`

---

## 🔍 **Comandos Específicos**

### **Executar Teste Específico**
```bash
# Teste específico
mvn test -Dtest=AuthenticationE2ETest

# Múltiplos testes específicos
mvn test -Dtest=AuthenticationE2ETest,UserManagementE2ETest

# Padrão de testes
mvn test -Dtest="*E2ETest"
mvn test -Dtest="*IntegrationTest"
```

### **Executar por Pacote**
```bash
# Todos os testes de um pacote
mvn test -Dtest="com.br.fiap.fortaleza.sabor.application.*Test"

# Testes de controllers
mvn test -Dtest="com.br.fiap.fortaleza.sabor.infrastructure.controller.*Test"
```

---

## 📊 **Estrutura de Testes do Projeto**

| Tipo de Teste | Padrão de Nome | Quantidade Aprox. | Descrição |
|---------------|----------------|-------------------|-----------|
| **Unitários** | `*Test.java` | ~90 | Testes de classes individuais |
| **Integração** | `*IntegrationTest.java` | ~20 | Testes de integração entre componentes |
| **E2E** | `*E2ETest.java` | 6 | Testes end-to-end da API REST |

---

## ⚙️ **Configurações Maven**

### **Perfis Configurados no POM.xml:**

- **`all-tests`** (padrão): Executa todos os tipos de teste
- **`unit-tests`**: Apenas testes unitários  
- **`integration-tests`**: Apenas testes de integração
- **`e2e-tests`**: Apenas testes E2E

### **Propriedades de Sistema:**
- Testes unitários e integração: `spring.profiles.active=test`
- Testes E2E: `spring.profiles.active=e2e`

---

## 🎯 **Exemplos de Uso Prático**

### **Durante Desenvolvimento:**
```bash
# Execução rápida - apenas unitários
mvn test -P unit-tests

# Validação completa antes de commit
mvn clean test
```

### **Para CI/CD:**
```bash
# Pipeline completo
mvn clean test

# Pipeline por etapas
mvn test -P unit-tests      # Etapa 1: Testes rápidos
mvn test -P integration-tests   # Etapa 2: Testes de integração  
mvn test -P e2e-tests       # Etapa 3: Testes E2E
```

### **Para Debug:**
```bash
# Executar com logs detalhados
mvn test -Dtest="*E2ETest" -X

# Executar um teste específico com debug
mvn test -Dtest=AuthenticationE2ETest -Dlogging.level.root=DEBUG
```

---

## ✅ **Verificação de Sucesso**

Após executar `mvn clean test`, você deve ver:

```
[INFO] Results:
[INFO] 
[INFO] Tests run: XXX, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] BUILD SUCCESS
```

Onde `XXX` é o total de testes executados (~120+ testes).

---

## 🚨 **Solução de Problemas**

### **Se alguns testes falharem:**
1. Verifique se todas as dependências estão instaladas
2. Confirme se o banco H2 está funcionando corretamente
3. Execute cada categoria separadamente para identificar o problema

### **Comandos de diagnóstico:**
```bash
# Verificar configuração
mvn help:active-profiles

# Listar todos os testes
mvn test -Dtest=NonExistentTest -DfailIfNoTests=false

# Executar com máximo de logs
mvn test -X -Dtest="*E2ETest"
```

---

**📋 Resumo**: Use `mvn clean test` para executar todos os testes do projeto (unitários, integração e E2E) em uma única execução!
