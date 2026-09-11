# Sistema de Gerenciamento de Estoque

Aplicação full stack para controle de produtos, categorias, entradas e saídas de estoque, desenvolvida com foco em arquitetura backend, regras de negócio e experiência administrativa.

**[Abrir demonstração online](https://galvaodev-web.github.io/Sistema-de-Gerenciamento-de-Estoque/)**

[![Deploy to Render](https://render.com/images/deploy-to-render-button.svg)](https://render.com/deploy?repo=https://github.com/galvaodev-web/Sistema-de-Gerenciamento-de-Estoque)

## Destaques

- API REST em Java 21 e Spring Boot 3
- Arquitetura em camadas
- PostgreSQL com Spring Data JPA
- Regras de negócio para movimentação de estoque
- Validação de dados e erros padronizados
- Testes unitários com JUnit 5 e Mockito
- Swagger / OpenAPI
- Interface administrativa responsiva
- Deploy preparado para Render

## Tecnologias

### Backend
- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Bean Validation
- PostgreSQL
- Lombok
- Maven Wrapper
- Swagger / OpenAPI
- JUnit 5
- Mockito

### Frontend
- HTML5
- CSS3
- JavaScript modular

## Funcionalidades

- CRUD de produtos com desativação lógica
- SKU único por produto
- CRUD de categorias
- Proteção contra exclusão de categorias vinculadas
- Registro transacional de entradas e saídas
- Bloqueio de saída acima do saldo disponível
- Histórico de movimentações
- Filtros por produto, tipo e período
- Identificação de estoque baixo
- Dashboard com indicadores principais
- Respostas de erro padronizadas
- Validação de dados de entrada
- Documentação interativa da API
- Painel web responsivo integrado ao backend

## Arquitetura

```text
Controller → Service → Repository → PostgreSQL
     ↕           ↕
    DTO     Regras de negócio
```

### Organização

- `controller`: endpoints e códigos HTTP
- `service`: regras de negócio e transações
- `repository`: persistência e consultas JPA
- `model`: entidades e enums do domínio
- `dto`: contratos de entrada e saída
- `exception`: tratamento padronizado de erros
- `config`: configuração OpenAPI
- `static`: frontend servido pelo Spring Boot

## Regras de negócio relevantes

O projeto implementa regras que vão além de um CRUD básico:

- um SKU não pode ser duplicado;
- uma saída não pode superar o saldo disponível;
- categorias vinculadas a produtos não podem ser removidas indevidamente;
- produtos abaixo do estoque mínimo são identificados automaticamente;
- movimentações preservam o histórico do estoque.

## Como executar

### Pré-requisitos

- JDK 21
- PostgreSQL 14+

### 1. Crie o banco

```sql
CREATE DATABASE estoque_db;
```

### 2. Configure as variáveis de ambiente

Use os nomes disponíveis em `.env.example`:

| Variável | Exemplo |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/estoque_db` |
| `DB_USERNAME` | `postgres` |
| `DB_PASSWORD` | sua senha local |

No PowerShell:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/estoque_db"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="sua_senha"
```

### 3. Inicie a aplicação

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Painel:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

## API

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` / `POST` | `/api/produtos` | Lista ou cria produtos |
| `GET` / `PUT` / `DELETE` | `/api/produtos/{id}` | Consulta, altera ou desativa um produto |
| `GET` | `/api/produtos/estoque-baixo` | Lista itens abaixo do mínimo |
| `GET` / `POST` | `/api/categorias` | Lista ou cria categorias |
| `PUT` / `DELETE` | `/api/categorias/{id}` | Altera ou exclui uma categoria |
| `POST` | `/api/movimentacoes/entrada` | Registra uma entrada |
| `POST` | `/api/movimentacoes/saida` | Registra uma saída |
| `GET` | `/api/movimentacoes` | Consulta o histórico e filtros |
| `GET` | `/api/dashboard` | Retorna indicadores principais |

### Exemplo de payload

```json
{
  "nome": "Teclado mecânico",
  "descricao": "Switch brown, padrão ABNT2",
  "codigoSku": "TEC-001",
  "categoriaId": 1,
  "quantidade": 10,
  "estoqueMinimo": 3,
  "precoCompra": 180.00,
  "precoVenda": 299.90,
  "ativo": true
}
```

## Testes

Os testes unitários cobrem cenários como:

- cadastro de produtos;
- SKU duplicado;
- estoque baixo;
- entradas;
- saídas;
- saldo insuficiente.

Execute com:

```powershell
.\mvnw.cmd test
```

## Deploy

O projeto possui configuração para deploy no Render por meio de `render.yaml`, incluindo aplicação e PostgreSQL.

As credenciais do banco são vinculadas por variáveis de ambiente e não ficam armazenadas no repositório.

## Roadmap

- Spring Security
- JWT
- Usuários e permissões
- Flyway
- Paginação avançada
- Relatórios e exportação
- Docker
- CI/CD

## O que este projeto demonstra

- Modelagem de domínio
- Regras de negócio
- Arquitetura em camadas
- API REST
- Persistência relacional
- Testes automatizados
- Documentação de API
- Deploy de aplicação backend

---

Desenvolvido por [Guilherme Galvão](https://github.com/galvaodev-web).