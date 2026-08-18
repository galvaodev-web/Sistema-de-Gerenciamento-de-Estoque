# Sistema de Gerenciamento de Estoque

Aplicação full stack para controlar produtos, categorias, entradas e saídas de estoque. O projeto foi construído para demonstrar uma API REST em Java com arquitetura em camadas e uma interface administrativa responsiva, mantendo o código direto e adequado para estudo.

**[Abrir demonstração online](https://galvaodev-web.github.io/Sistema-de-Gerenciamento-de-Estoque/)**

[![Deploy to Render](https://render.com/images/deploy-to-render-button.svg)](https://render.com/deploy?repo=https://github.com/galvaodev-web/Sistema-de-Gerenciamento-de-Estoque)

## Tecnologias

- Java 21 e Spring Boot 3
- Spring Web, Spring Data JPA e Bean Validation
- PostgreSQL
- Lombok e Maven Wrapper
- Swagger / OpenAPI
- JUnit 5 e Mockito
- HTML5, CSS3 e JavaScript modular

## Funcionalidades

- CRUD de produtos com desativação lógica e SKU único
- CRUD de categorias, impedindo exclusão quando existem produtos vinculados
- Registro transacional de entradas e saídas
- Bloqueio de saída superior ao saldo disponível
- Histórico com filtros por produto, tipo ou período
- Identificação de produtos com quantidade menor ou igual ao estoque mínimo
- Dashboard com produtos ativos, unidades, valor pelo preço de compra, estoque baixo e movimentações
- Respostas de erro padronizadas e validação dos dados de entrada
- Documentação interativa da API
- Painel web responsivo integrado à API

## Arquitetura

```text
Controller → Service → Repository → PostgreSQL
     ↕           ↕
    DTO     Regras de negócio
```

- `controller`: define os recursos e códigos HTTP.
- `service`: concentra regras de negócio e transações.
- `repository`: abstrai consultas e persistência com JPA.
- `model`: contém entidades e enum do domínio.
- `dto`: define contratos de entrada e saída da API.
- `exception`: padroniza erros com `@RestControllerAdvice`.
- `config`: configura a documentação OpenAPI.
- `static`: contém o frontend servido pelo Spring Boot.

## Como executar

### Pré-requisitos

- JDK 21
- PostgreSQL 14 ou superior

### 1. Crie o banco

```sql
CREATE DATABASE estoque_db;
```

### 2. Configure as variáveis de ambiente

Copie os nomes disponíveis em `.env.example` e configure-os no terminal ou na IDE. O Spring lê:

| Variável | Exemplo |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/estoque_db` |
| `DB_USERNAME` | `postgres` |
| `DB_PASSWORD` | sua senha local |

No PowerShell, para a sessão atual:

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

Acesse o painel em [http://localhost:8080](http://localhost:8080) e o Swagger em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

## Deploy no Render

O arquivo `render.yaml` provisiona a aplicação e o PostgreSQL automaticamente:

1. Clique no botão **Deploy to Render** no início deste README.
2. Entre ou crie uma conta no Render e autorize o acesso ao GitHub.
3. Confirme **Deploy Blueprint**.
4. Aguarde o serviço ficar com o status `Live` e abra a URL `.onrender.com` exibida pelo Render.

As credenciais do banco são geradas e vinculadas pelo Render, sem serem gravadas no repositório. Serviços gratuitos podem levar alguns segundos para responder após períodos sem acesso.

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
| `GET` | `/api/movimentacoes` | Consulta o histórico e seus filtros |
| `GET` | `/api/dashboard` | Retorna os indicadores principais |

Exemplo de produto:

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

Os testes unitários cobrem cadastro, SKU duplicado, estoque baixo, entradas, saídas e saldo insuficiente:

```powershell
.\mvnw.cmd test
```

## Screenshots

Adicione aqui capturas do dashboard, da listagem de produtos e da tela de movimentações após iniciar o projeto com dados de demonstração.

## Melhorias futuras

- Spring Security, JWT, usuários e permissões
- Relatórios e exportação em PDF/Excel
- Paginação e filtros combinados no histórico
- Migrações de banco com Flyway
- Docker e deploy em nuvem

## Sugestão de commits

```text
feat: add product and category CRUD
feat: implement stock movements
feat: create inventory dashboard
test: add inventory service tests
feat: add responsive admin interface
docs: add project documentation
```
