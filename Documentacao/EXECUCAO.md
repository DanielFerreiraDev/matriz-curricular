# Execução do Projeto

Este documento descreve como executar o projeto localmente de forma automatizada, utilizando Docker.

Todo o ambiente (banco de dados, Keycloak, backend e frontend) é provisionado automaticamente via Docker Compose.

---

## 1 - Pré-requisitos

É necessário ter instalado apenas:

- Docker (versão 20+)
- Docker Compose (v2+)
- Java 21
- Maven 3.9.*


---

## 2 - Configuração de Ambiente

Antes de subir os containers, crie um arquivo `.env` na **raiz do projeto** (matriz-curricular) com o seguinte conteúdo:

```env
KEYCLOAK_BACKEND_SECRET=CLIENT_SECRET_BACKEND
```

**Por se tratar de um Desafio técnico o .env foi exposto no git para facilitar testes**

## 3 - Build da Aplicação

Na raiz do projeto, execute:

```docker compose up -d --build```

O Docker Compose irá:
- Criar o banco de dados PostgreSQL 
- Inicializar os schemas do banco 
- Subir o Keycloak e importar automaticamente o Realm 
- Subir o backend já integrado ao Keycloak
- Subir o frontend já integrado ao Keycloak

## 4 - Serviços disponíveis

Após a inicialização, os serviços estarão disponíveis em:
- Keycloak (Admin Console): http://localhost:8080
  - Usuário: admin 
  - Senha: admin 
- Backend API: http://localhost:8081
- Frontend: http://localhost ou http://localhost:4200 (rodar local)

## 5 - Reset Total

```docker compose down -v```

```docker compose up -d --build```

## 5 - Usuários de Teste

Os seguintes usuários são importados automaticamente no Keycloak:

| Usuário    | Perfil      | Senha          |
|------------|-------------|----------------|
| aluno1     | ALUNO       | unifor123      |
| joaocarlos | COORDENADOR | coordenacao123 |


## 6 - Observações Importantes

O Realm, Clients, Roles e usuários são importados automaticamente via arquivo JSON. (ver [keycloak-setup.md](keycloak-setup.md). Seção Configuração automática)
- O backend valida tokens JWT emitidos pelo Keycloak. 
- O controle de acesso é feito por roles no backend.

## 7 - Teste e Validação

- BACKEND: Para testes da API, visualizar documentação [API-TESTE.md](API-TESTE.md)
- FRONTEND: Para validar fluxo de login e logout acessar serviço disponível em:
  - http://localhost
  - http://localhost:4200

## 8 - Testes Rodando Localmente

- Para ser feito debugger dos serviços backend e frontend, você pode seguir as documentações padrões de execução.
- Se atentar somente as versões requiridas das stacks
  - Exemplo:
    - backend: ```./mvnw quarkus:dev```
    - frontend: ```npx nx serve matrizcurricular``` 