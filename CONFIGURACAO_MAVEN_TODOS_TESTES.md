# Configuração Maven - Execução de Todos os Testes

## Alterações Realizadas no pom.xml

### 1. Plugin Surefire Principal
**Antes:**
- Executava apenas testes unitários (*Test.java)
- Excluía testes de integração (*IntegrationTest.java)

**Depois:**
```xml
<!-- Maven Surefire Plugin para todos os testes -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.1.2</version>
    <configuration>
        <includes>
            <include>**/*Test.java</include>
            <include>**/*IntegrationTest.java</include>
        </includes>
        <systemPropertyVariables>
            <spring.profiles.active>test</spring.profiles.active>
        </systemPropertyVariables>
    </configuration>
</plugin>
```

### 2. Perfil Maven Padrão
**Antes:**
- Perfil `unit-tests` como padrão (activeByDefault=true)
- Executava apenas testes unitários

**Depois:**
- Perfil `all-tests` como padrão (activeByDefault=true)
- Executa todos os testes (unitários + integração)

```xml
<!-- Profile padrão para executar todos os testes -->
<profile>
    <id>all-tests</id>
    <activation>
        <activeByDefault>true</activeByDefault>
    </activation>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <includes>
                        <include>**/*Test.java</include>
                        <include>**/*IntegrationTest.java</include>
                    </includes>
                    <systemPropertyVariables>
                        <spring.profiles.active>test</spring.profiles.active>
                    </systemPropertyVariables>
                </configuration>
            </plugin>
        </plugins>
    </build>
</profile>
```

### 3. Perfis Disponíveis

| Perfil | Comando | Descrição |
|--------|---------|-----------|
| `all-tests` (padrão) | `mvn test` | Executa TODOS os testes (unitários + integração) |
| `unit-tests` | `mvn test -P unit-tests` | Executa apenas testes unitários |
| `integration-tests` | `mvn test -P integration-tests` | Executa apenas testes de integração |

## Estrutura de Testes Atual

### Testes Unitários (46 arquivos)
- Localizados em: `src/test/java/com/br/fiap/fortaleza/sabor/`
- Padrão de nomenclatura: `*Test.java`
- Exemplos: `UserUseCaseTest.java`, `AuthUseCaseTest.java`, etc.

### Testes de Integração (8 arquivos)
- Localizados em: `src/test/java/com/br/fiap/fortaleza/sabor/integration/`
- Padrão de nomenclatura: `*IntegrationTest.java`
- Arquivos:
  1. `AuthControllerIntegrationTest.java`
  2. `MenuItemsControllerIntegrationTest.java` 
  3. `RestaurantControllerIntegrationTest.java`
  4. `TypeControllerIntegrationTest.java`
  5. `UserControllerIntegrationTest.java`
  6. `WorkflowIntegrationTest.java`
  7. Mais 2 arquivos de teste de integração

## Resultado

✅ **Agora o comando `mvn clean test` executa TODOS os testes:**
- Testes unitários: ~46 arquivos
- Testes de integração: 8 arquivos  
- **Total esperado: ~54 testes**

## Comandos de Teste

```bash
# Executa TODOS os testes (unitários + integração)
mvn clean test

# Executa apenas testes unitários
mvn clean test -P unit-tests

# Executa apenas testes de integração  
mvn clean test -P integration-tests
```

## Configuração do Ambiente de Teste

- **Profile Spring ativo:** `test`
- **Banco de dados:** H2 (em memória) para testes de integração
- **Configuração:** `application-test.properties`
