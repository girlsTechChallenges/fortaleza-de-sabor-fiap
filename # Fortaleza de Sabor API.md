# Fortaleza de Sabor API

Fortaleza de Sabor é uma API desenvolvida para gerenciar o restaurante Fortaleza de Sabor. Este repositório contém o código-fonte do projeto, que utiliza o framework Spring Boot para criar uma aplicação robusta e escalável.

---

## Arquitetura do Projeto

O projeto segue uma arquitetura modular e organizada, com as seguintes camadas principais:

1. **Camada de Configuração**:
   - Configurações globais da aplicação, como a configuração do OpenAPI para documentação da API.
   - Arquivo: [`OpenApiConfig`](src/main/java/com/br/fiap/fortaleza/sabor/naousar/configuration/OpenApiConfig.java).

2. **Camada de Recursos**:
   - Contém os controladores responsáveis por expor os endpoints da API.
   - Exemplo: Controladores Spring MVC.

3. **Camada de Repositórios**:
   - Responsável pela interação com o banco de dados.
   - Exemplo: [`xxxxxxRepository`](src/main/java/com/br/fiap/fortaleza/sabor/naousar/infrastructure/repositories/xxxxxxRepository.java).

4. **Camada de Configuração de Propriedades**:
   - Configurações específicas da aplicação, como conexão com o banco de dados e propriedades do JPA.
   - Arquivo: [`application.properties`](src/main/resources/application.properties).

---

## Tecnologias Utilizadas

- **Java 21**: Linguagem principal do projeto.
- **Spring Boot 3.4.5**: Framework para desenvolvimento rápido de aplicações Java.
- **PostgreSQL**: Banco de dados relacional utilizado para persistência.
- **Hibernate**: Implementação do JPA para mapeamento objeto-relacional.
- **SpringDoc OpenAPI**: Ferramenta para geração automática de documentação da API.
- **H2 Database**: Banco de dados em memória utilizado para testes.
- **Maven**: Gerenciador de dependências e build.

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
│   │   │                       ├── [MainApplication.java](http://_vscodecontentref_/0)
│   │   │                       ├── infrastructure/
│   │   │                       │   ├── configuration/
│   │   │                       │   │   └── [OpenApiConfig.java](http://_vscodecontentref_/1)
│   │   │                       │   └── repositories/
│   │   │                       │       └── [xxxxxxRepository.java](http://_vscodecontentref_/2)
│   │   └── resources/        # Recursos como arquivos de configuração
│   │       └── [application.properties](http://_vscodecontentref_/3)
│   └── test/                 # Código de testes
├── target/                  # Arquivos gerados pelo build
├── [pom.xml](http://_vscodecontentref_/4)                  # Configuração do Maven
└── [README.md](http://_vscodecontentref_/5)                # Documentação do projeto