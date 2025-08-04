# Fortaleza de Sabor API

Uma API REST robusta para gestão de restaurantes e usuários, desenvolvida com Spring Boot e arquitetura Clean Architecture.

## 📊 Status do Projeto

Veja o status detalhado, tecnologias, estrutura de arquivos e funcionalidades em:
- [RESUMO_PROJETO.md](RESUMO_PROJETO.md) — Resumo executivo do projeto
- [architecture.md](architecture.md) — Arquitetura, camadas e diagramas
- [doc.md](doc.md) — Documentação técnica detalhada
- [DOCUMENTACAO_COMPLETA_TESTES.md](DOCUMENTACAO_COMPLETA_TESTES.md) — Estratégia e cobertura de testes

## 🚀 Funcionalidades e Estrutura

Consulte as funcionalidades, endpoints, estrutura de pastas e exemplos de payloads em:
- [RESUMO_PROJETO.md](RESUMO_PROJETO.md) — Funcionalidades, endpoints e estrutura
- [architecture.md](architecture.md) — Camadas, fluxos e diagramas
- [doc.md](doc.md) — Exemplos de requisição/resposta e detalhes técnicos

## 🚀 Como Executar

Veja instruções detalhadas de execução (Docker, build local, variáveis de ambiente) em:
- [RESUMO_PROJETO.md](RESUMO_PROJETO.md)
- [doc.md](doc.md)

## 🔗 Acesso à Aplicação

URLs principais:
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## 🧪 Executar Testes

Veja comandos e detalhes de execução de testes em:
- [DOCUMENTACAO_COMPLETA_TESTES.md](DOCUMENTACAO_COMPLETA_TESTES.md)

## 📊 Cobertura, Funcionalidades e Testes

Consulte detalhes completos sobre endpoints, exemplos de payloads, cobertura de testes, arquitetura e métricas em:
- [RESUMO_PROJETO.md](RESUMO_PROJETO.md)
- [architecture.md](architecture.md)
- [doc.md](doc.md)
- [DOCUMENTACAO_COMPLETA_TESTES.md](DOCUMENTACAO_COMPLETA_TESTES.md)
- **Nomenclatura Unificada**: Padrão `shouldXWhenY` em 100% dos testes
- **Estrutura AAA**: Aplicada uniformemente

#### **🎯 Nova Cobertura de Testes**
- **Mappers**: Testes de integridade de dados entre camadas
- **DTOs**: Validação completa Bean Validation (9 classes, 134 cenários)
- **Use Cases**: Correções de comportamento e edge cases
- **Repositories**: Testes de lógica customizada

#### **📊 Validações Implementadas**
- **Conversões de Dados**: Preservação durante transformações
- **Bean Validation**: Testes completos para @NotNull, @NotBlank, @Size, @Pattern, @Email, @Positive
- **Tratamento de Null**: Verificação em todos os cenários
- **Formatos Complexos**: Regex de preços, emails, CEP, padrões de nome
- **Objetos Aninhados**: Validação com @Valid em listas e objetos


### Executar Testes

Para executar todos os testes automatizados (unitários e integração):
```powershell
mvn clean test
```

Relatórios de execução são gerados em `target/surefire-reports/`.
Consulte detalhes completos de cobertura, comandos e exemplos em [DOCUMENTACAO_COMPLETA_TESTES.md](DOCUMENTACAO_COMPLETA_TESTES.md).

---



## 🧪 Testes Automatizados via Postman

O projeto disponibiliza uma collection Postman para automação dos testes de API:

- `collections/Fortaleza_de_Sabor_API.postman_collection.json`

**Como utilizar:**
1. Importe o arquivo `.json` no Postman
2. Configure a variável `baseUrl` para `http://localhost:8080`
3. Execute os testes individualmente ou via "Run Collection"

A collection cobre cenários de:
- Validação de campos obrigatórios e formatos
- Fluxos completos de autenticação, usuários, restaurantes, cardápio e tipos
- Testes de segurança e casos de erro

Consulte detalhes e exemplos em [DOCUMENTACAO_COMPLETA_TESTES.md](DOCUMENTACAO_COMPLETA_TESTES.md).




## 🔗 URLs de Acesso

- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI**: http://localhost:8080/v3/api-docs



## 📚 Documentação

Documentação centralizada:
- [RESUMO_PROJETO.md](RESUMO_PROJETO.md): Resumo executivo
- [architecture.md](architecture.md): Arquitetura e decisões técnicas
- [doc.md](doc.md): Documentação técnica detalhada
- [DOCUMENTACAO_COMPLETA_TESTES.md](DOCUMENTACAO_COMPLETA_TESTES.md): Estratégia e cobertura de testes
- [collections/](collections/): Collection Postman para testes automatizados

> Arquivos como `GUIA_EXECUCAO_TESTES.md` e `Fortaleza_de_Sabor_API_Completa.postman_collection.json` não existem na raiz do projeto neste momento. Caso sejam criados, atualizar este README.


## 👨‍💻 Desenvolvido por

**Equipe Girls Tech Challenges**
- Tech Challenge FIAP
- Arquitetura de Software

---

**Fortaleza de Sabor API** - Uma solução robusta, bem testada e documentada para gestão de restaurantes. 🍽️✨

---





