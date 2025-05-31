# Projeto: Fortaleza de Sabor API
## Equipe
- Nome: Equipe Fortaleza de Sabor
- RMs: [Insira os RMs dos integrantes]

---

## 1. Introdução
### Descrição do problema
Na nossa região, um grupo de restaurantes decidiu contratar estudantes para construir um sistema de gestão compartilhado, visando reduzir custos e melhorar a eficiência. O sistema permitirá que clientes escolham restaurantes com base na comida oferecida e que os restaurantes gerenciem suas operações de forma eficiente.

### Objetivo do projeto
Desenvolver um backend completo e robusto utilizando Spring Boot, com foco no gerenciamento de usuários, incluindo operações de criação, atualização, exclusão e validação de login. O projeto será configurado para rodar em um ambiente Docker, utilizando Docker Compose para orquestração dos serviços e integração com um banco de dados relacional.

---

## 2. Arquitetura do Sistema
### Descrição da Arquitetura
O projeto segue uma arquitetura modular e organizada, com as seguintes camadas principais:
- **Configuração**: Configurações globais da aplicação, como OpenAPI.
- **Controladores**: Exposição de endpoints da API.
- **Casos de Uso**: Lógica de negócios.
- **Repositórios**: Interação com o banco de dados.
- **Persistência**: Entidades e repositórios JPA.
- **DTOs e Mapeamento**: Transferência de dados e mapeamento.
- **Exceções**: Gerenciamento de erros.

### Diagrama da Arquitetura
![Diagrama de Arquitetura](diagram.png)

---

## 3. Descrição dos Endpoints da API
### Tabela de Endpoints
| Endpoint               | Método  | Descrição                        |
|------------------------|---------|----------------------------------|
| `/usuarios`            | POST    | Criar novo usuário               |
| `/usuarios/{idUser}`   | PUT     | Atualizar usuário                |
| `/usuarios/{idUser}`   | GET     | Buscar usuário por ID            |
| `/usuarios`            | GET     | Buscar todos os usuários         |
| `/usuarios/{idUser}`   | DELETE  | Remover usuário                  |
| `/auth/login`          | POST    | Validar login                    |
| `/auth/password`       | PATCH   | Alterar senha                    |

### Exemplos de requisição e resposta

#### Criar Usuário
**POST** `/usuarios`

Request:
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "login": "joaosilva",
  "senha": "senha1234",
  "dataAlteracao": "2025-05-17",
  "tipo": "DONO",
  "address": [
    {
      "rua": "Rua Alves Paulista",
      "bairro": "Paulista Nova",
      "complemento": "casa",
      "numero": 130,
      "estado": "São Paulo",
      "cidade": "São Paulo",
      "cep": 85965000
    }
  ]
}
```
Response (201 Created):
```json
{
  "nome": "João Silva",
  "login": "joaosilva",
  "email": "joao@email.com",
  "tipo": "DONO",
  "address": [
    {
      "rua": "Rua Alves Paulista",
      "bairro": "Paulista Nova",
      "complemento": "casa",
      "numero": 130,
      "estado": "São Paulo",
      "cidade": "São Paulo",
      "cep": 85965000
    }
  ]
}
```

#### Buscar Usuário por ID
**GET** `/usuarios/{idUser}`

Response (202 Accepted):
```json
{
  "nome": "João Silva",
  "login": "joaosilva",
  "email": "joao@email.com",
  "tipo": "DONO",
  "address": [
    {
      "rua": "Rua Alves Paulista",
      "bairro": "Paulista Nova",
      "complemento": "casa",
      "numero": 130,
      "estado": "São Paulo",
      "cidade": "São Paulo",
      "cep": 85965000
    }
  ]
}
```

#### Buscar Todos os Usuários
**GET** `/usuarios`

Response (200 OK):
```json
[
  {
    "nome": "João Silva",
    "login": "joaosilva",
    "email": "joao@email.com",
    "tipo": "DONO",
    "address": [
      {
        "rua": "Rua Alves Paulista",
        "bairro": "Paulista Nova",
        "complemento": "casa",
        "numero": 130,
        "estado": "São Paulo",
        "cidade": "São Paulo",
        "cep": 85965000
      }
    ]
  },
  {
    "nome": "Maria Oliveira",
    "login": "mariaoliveira",
    "email": "maria@email.com",
    "tipo": "CLIENTE",
    "address": [
      {
        "rua": "Rua das Flores",
        "bairro": "Centro",
        "complemento": "apto 101",
        "numero": 50,
        "estado": "Ceará",
        "cidade": "Fortaleza",
        "cep": 60000000
      }
    ]
  }
]
```

#### Atualizar Usuário
**PUT** `/usuarios/{idUser}`

Request:
```json
{
  "nome": "João Silva Atualizado",
  "email": "joao@email.com",
  "login": "joaosilva",
  "senha": "novaSenha123",
  "dataAlteracao": "2025-06-01",
  "tipo": "DONO",
  "address": [
    {
      "rua": "Rua Nova",
      "bairro": "Paulista Nova",
      "complemento": "casa",
      "numero": 130,
      "estado": "São Paulo",
      "cidade": "São Paulo",
      "cep": 85965000
    }
  ]
}
```
Response (202 Accepted):
```json
{
  "nome": "João Silva Atualizado",
  "login": "joaosilva",
  "email": "joao@email.com",
  "tipo": "DONO",
  "address": [
    {
      "rua": "Rua Nova",
      "bairro": "Paulista Nova",
      "complemento": "casa",
      "numero": 130,
      "estado": "São Paulo",
      "cidade": "São Paulo",
      "cep": 85965000
    }
  ]
}
```

#### Remover Usuário
**DELETE** `/usuarios/{idUser}`

Response (204 No Content):

---

#### Login
**POST** `/auth/login`

Request:
```json
{
  "email": "joao@email.com",
  "password": "senha1234"
}
```
Response (200 OK):
```json
{
  "token": "<jwt-token>",
  "id": 5
}
```

#### Alterar Senha
**PATCH** `/auth/password`

Request:
```json
{
  "email": "joao@email.com",
  "password": "novaSenha123"
}
```
Response (204 No Content):

---

## 4. Configuração do Projeto
### Configuração do Docker Compose
O arquivo `docker-compose.yml` orquestra a aplicação e o banco de dados PostgreSQL. Abaixo, cada comando e configuração do arquivo é explicado:

#### Estrutura do docker-compose.yml

```yaml
version: '3.8'

services:
  app:
    build:
      context: .           # Define o diretório de build da aplicação (raiz do projeto)
    container_name: app    # Nome do container da aplicação
    depends_on:
      - db                 # Garante que o banco de dados (db) será iniciado antes da aplicação
    environment:
      - POSTGRES_USER=postgres                # Usuário do banco de dados
      - POSTGRES_PASSWORD=postgres            # Senha do banco de dados
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/postgres  # URL de conexão do Spring para o banco
      - SPRING_DATASOURCE_USERNAME=postgres   # Usuário do banco para o Spring
      - SPRING_DATASOURCE_PASSWORD=postgres   # Senha do banco para o Spring
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update  # Configuração do Hibernate para atualizar o schema automaticamente
    ports:
      - "8080:8081"         # Mapeia a porta 8081 do container para a 8080 do host

  db:
    image: postgres:latest # Imagem oficial do PostgreSQL
    container_name: db     # Nome do container do banco de dados
    environment:
      - POSTGRES_USER=postgres      # Usuário padrão do banco
      - POSTGRES_PASSWORD=postgres  # Senha padrão do banco
      - POSTGRES_DB=postgres        # Nome do banco de dados
    ports:
      - "5432:5432"        # Mapeia a porta 5432 do container para a 5432 do host
```

#### Explicação dos principais comandos e parâmetros:
- `version`: Define a versão do Docker Compose utilizada.
- `services`: Define os serviços que serão orquestrados (app e db).
- `build.context`: Diretório onde está o Dockerfile da aplicação.
- `container_name`: Nome do container para facilitar identificação.
- `depends_on`: Garante a ordem de inicialização dos serviços.
- `environment`: Variáveis de ambiente passadas para os containers (credenciais, URLs, configurações do Spring e do banco).
- `ports`: Mapeamento de portas entre o host e o container.
- `image`: Imagem Docker a ser utilizada (no caso do banco, a oficial do PostgreSQL).

### Instruções para execução local
1. Clonar o repositório:
   ```powershell
   git clone https://github.com/seu-usuario/fortaleza-de-sabor-fiap.git
   cd fortaleza-de-sabor-fiap
   ```
2. Construir o projeto:
   ```powershell
   ./mvnw clean install
   ```
3. Executar com Docker Compose:
   ```powershell
   docker-compose up --build
   ```
4. Acessar a aplicação:
   - API: [http://localhost:8080](http://localhost:8080)
   - Swagger: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 5. Qualidade do Código
### Boas Práticas Utilizadas
- **DRY**: Código reutilizável e modular.
- **SOLID**: Princípios de design orientado a objetos.
- **Convenções do Spring Boot**: Seguindo padrões do framework.

---

## 6. Collections para Teste
### Link para a Collection do Postman
Arquivo de collection não está presente no repositório. Para testar, utilize os exemplos de payload acima ou gere uma collection manualmente via Swagger.

### Descrição dos Testes Manuais
1. Utilize os exemplos de payload acima para testar os endpoints via Postman ou outra ferramenta REST.
2. Valide os endpoints conforme os exemplos fornecidos.

---

## 7. Repositório do Código
### URL do Repositório
[https://github.com/seu-usuario/fortaleza-de-sabor-fiap](https://github.com/seu-usuario/fortaleza-de-sabor-fiap)

---

### Notas
Fiquem à vontade para modificar e ajustar este modelo às necessidades do projeto.
Não é necessário implementar a autenticação dos usuários mas fiquem à vontade para fazê-lo se assim desejarem.
