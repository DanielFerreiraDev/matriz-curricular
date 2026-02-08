# Desafio Técnico – Desenvolvedor de Software Pleno

Este repositório contém a implementação do desafio técnico para a vaga de **Desenvolvedor de Software – Pleno**, contemplando **backend**, **frontend**, **Docker** e **documentação**, conforme solicitado no escopo do desafio.

O objetivo do projeto é demonstrar boas práticas de desenvolvimento, organização de código, clareza de domínio, aderência à stack solicitada e facilidade de execução.

---

## Estrutura do Repositório

```text
├── backend/
├── Documentação/
    ├── DECISOES_TECNICAS.md
    ├── EXECUCAO.md
    ├── keycloak-setup.md
    ├── README.md/
├── frontend/
├── keycloak-config/
    ├── matriz-realm.json
├── .gitignore
├── docker-compose.yml
├── init-db.sh
├── README.md
```

## Backend

Contém a aplicação backend do sistema.

Responsável por:

- Regras de negócio da matriz curricular
- Exposição da API REST
- Controle de acesso, validações e consistência transacional

## Frontend
Contém a aplicação frontend do sistema.

Responsável por:

- Interface funcional
- Integração com Keycloak para autenticação e autorização
- Consumo da API REST do backend


## Docker Compose

Ferramenta responsável por orquestrar todos os serviços necessários para execução da aplicação em ambiente local.

Inclui os seguintes serviços:

- Backend (Quarkus)
- Frontend (Angular)
- Keycloak (autenticação e autorização)
- Banco de dados PostgreSQL

## Documentação

Contém toda a documentação necessária para entendimento, execução e testes do projeto.

Contém:

- Pré-requisitos
- Passo a passo de execução do projeto
- Documentação da API (Swagger / OpenAPI ou Markdown)
- Descrição dos endpoints
- Payloads de request e response
- Códigos de erro e status HTTP
- Diagramas e decisões técnicas adotadas
