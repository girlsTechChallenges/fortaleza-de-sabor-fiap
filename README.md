# Fortaleza de Sabor API

Fortaleza de Sabor é uma API desenvolvida para gerenciar o restaurante Fortaleza de Sabor. Este repositório contém o código-fonte do projeto, que utiliza o framework Spring Boot para criar uma aplicação robusta e escalável.

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
├── collections/                      # Collections Postman
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
- **Problema**: Anotações Swagger "poluindo" os Controllers
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

### Authentication
- **POST** `/auth/login` - Realizar login
- **PATCH** `/auth/password` - Alterar senha

---

## Testes

### Testes Unitários
O projeto possui cobertura de testes para:
- Controllers
- Use Cases
- Repositories
- Mappers
- Exception Handlers

### Executar Testes
```powershell
./mvnw test
```

### Relatórios de Testes
Após a execução, os relatórios podem ser encontrados em:
- `target/surefire-reports/`

---

## Collections para Teste

### Collection do Postman
Disponível em: `collections/collection-phase-one`

### Execução dos Testes via Postman
1. Importe a collection do diretório `collections`
2. Execute os endpoints na seguinte ordem:
   - Criar usuário (POST `/users`)
   - Login (POST `/auth/login`)
   - Outras operações CRUD conforme necessário

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

## Contribuição

Contribuições são bem-vindas! Para contribuir:
1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/novaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/novaFeature`)
5. Abra um Pull Request

---

