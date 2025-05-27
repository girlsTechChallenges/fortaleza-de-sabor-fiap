# Fortaleza de Sabor API

Fortaleza de Sabor é uma API desenvolvida para gerenciar o restaurante Fortaleza de Sabor. Este repositório contém o código-fonte do projeto, que utiliza o framework Spring Boot para criar uma aplicação robusta e escalável.

---

## Arquitetura do Projeto

O projeto segue uma arquitetura modular e organizada, com as seguintes camadas principais:

1. **Camada de Configuração**:
   - Configurações globais da aplicação, como a configuração do OpenAPI para documentação da API.
   - Arquivo: [`OpenApiConfig`](src/main/java/com/br/fiap/fortaleza/sabor/infrastructure/config/OpenApiConfig.java).

2. **Camada de Controladores**:
   - Contém os controladores responsáveis por expor os endpoints da API.
   - Exemplo: [`UserController`](src/main/java/com/br/fiap/fortaleza/sabor/infrastructure/controller/UserController.java).

3. **Camada de Casos de Uso**:
   - Implementa a lógica de negócios da aplicação.
   - Exemplo: [`CreateUseCase`](src/main/java/com/br/fiap/fortaleza/sabor/application/usecase/CreateUseCase.java).

4. **Camada de Repositórios**:
   - Responsável pela interação com o banco de dados.
   - Exemplo: [`UserRepositoryJpa`](src/main/java/com/br/fiap/fortaleza/sabor/infrastructure/gateways/UserRepositoryJpa.java).

5. **Camada de Persistência**:
   - Contém as entidades e repositórios JPA.
   - Exemplo: [`UserEntity`](src/main/java/com/br/fiap/fortaleza/sabor/infrastructure/persistence/UserEntity.java).

6. **Camada de DTOs e Mapeamento**:
   - Define os objetos de transferência de dados e realiza o mapeamento entre entidades e domínios.
   - Exemplo: [`UserRequestDto`](src/main/java/com/br/fiap/fortaleza/sabor/infrastructure/controller/dto/UserRequestDto.java) e [`UserEntityMapper`](src/main/java/com/br/fiap/fortaleza/sabor/infrastructure/mapper/UserEntityMapper.java).

7. **Camada de Exceções**:
   - Gerencia os erros e exceções da aplicação.
   - Exemplo: [`UserNotFoundException`](src/main/java/com/br/fiap/fortaleza/sabor/infrastructure/config/exception/UserNotFoundException.java).

---

## Tecnologias Utilizadas

- **Java 21**: Linguagem principal do projeto.
- **Spring Boot 3.4.5**: Framework para desenvolvimento rápido de aplicações Java.
- **PostgreSQL**: Banco de dados relacional utilizado para persistência.
- **Hibernate**: Implementação do JPA para mapeamento objeto-relacional.
- **SpringDoc OpenAPI**: Ferramenta para geração automática de documentação da API.
- **H2 Database**: Banco de dados em memória utilizado para testes.
- **Maven**: Gerenciador de dependências e build.
- **Docker**: Utilizado para containerização da aplicação e banco de dados.

---

## Estrutura do Repositório

```plaintext
.
├── .devcontainer/          # Configurações do Dev Container
├── .mvn/                   # Configurações do Maven Wrapper
├── src/
│   ├── main/
│   │   ├── java/           # Código-fonte principal
│   │   │   └── com/
│   │   │       └── br/
│   │   │           └── fiap/
│   │   │               └── fortaleza/
│   │   │                   └── sabor/
│   │   │                       ├── MainApplication.java
│   │   │                       ├── application/
│   │   │                       │   └── usecase/   # Casos de uso
│   │   │                       ├── domain/       # Domínios
│   │   │                       ├── infrastructure/
│   │   │                       │   ├── config/   # Configurações
│   │   │                       │   ├── controller/ # Controladores
│   │   │                       │   ├── dto/      # DTOs
│   │   │                       │   ├── gateways/ # Repositórios customizados
│   │   │                       │   ├── mapper/   # Mapeamento de entidades
│   │   │                       │   └── persistence/ # Entidades JPA
│   │   └── resources/        # Recursos como arquivos de configuração
│   │       ├── application.properties
│   │       └── application-test.properties
│   └── test/                 # Código de testes
├── target/                  # Arquivos gerados pelo build
├── Dockerfile               # Dockerfile para a aplicação
├── docker-compose.yml       # Configuração do Docker Compose
├── pom.xml                  # Configuração do Maven
└── README.md                # Documentação do projeto
```

---

## Como Executar o Projeto

### Pré-requisitos

- **Java 21** instalado.
- **Maven** instalado.
- **Docker** e **Docker Compose** instalados.

### Passos

1. **Clonar o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/fortaleza-de-sabor.git
   cd fortaleza-de-sabor
   ```

2. **Construir o projeto**:
   ```bash
   ./mvnw clean install
   ```

3. **Executar com Docker Compose**:
   ```bash
   docker-compose up --build
   ```

4. **Acessar a aplicação**:
   - A API estará disponível em: [http://localhost:8080](http://localhost:8080).
   - A documentação do Swagger estará em: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

---

## Testes

Os testes estão localizados no diretório `src/test`. Para executá-los, utilize o comando:

```bash
./mvnw test
```

---

## Testing with Postman

1. Import the collection from `postman/fortaleza-de-sabor.postman_collection.json`
2. Import the environment from `postman/local-environment.postman_environment.json`
3. Select the "Local Environment" in Postman
4. Execute the requests in the following order:
   - Create User
   - Login Validation
   - Change Password

---

## Testando com Postman

1. Importe a collection do arquivo `postman/fortaleza-de-sabor.postman_collection.json`
2. Importe o ambiente do arquivo `postman/local-environment.postman_environment.json`
3. Selecione o ambiente "Local Environment" no Postman
4. Execute as requisições na seguinte ordem:
   - Criar Usuário
   - Validação de Login
   - Alteração de Senha

---

## Endpoints Disponíveis

### Usuários
- **POST** `/api/users` - Criar novo usuário
- **PUT** `/api/users/{id}` - Atualizar usuário existente
- **GET** `/api/users/{id}` - Buscar usuário por ID
- **DELETE** `/api/users/{id}` - Remover usuário

### Autenticação
- **POST** `/api/auth/login` - Validar login
- **PUT** `/api/auth/password` - Alterar senha

---

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

---

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo `LICENSE` para mais informações.
