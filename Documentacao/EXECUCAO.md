# Execução do Projeto

Este documento descreve como executar o projeto localmente de forma automatizada, utilizando Docker.

Todo o ambiente (banco de dados, Keycloak, backend e frontend) é provisionado automaticamente via Docker Compose.

---

## 1 - Pré-requisitos

É necessário ter instalado apenas:

- Docker (versão 20+)
- Docker Compose (v2+)

> Não é necessário instalar Java, Node.js, Maven ou banco de dados localmente.

---

## 2 - Configuração de Ambiente

Antes de subir os containers, crie um arquivo `.env` na **raiz do projeto** (matriz-curricular) com o seguinte conteúdo:

```env
KEYCLOAK_BACKEND_SECRET=CLIENT_SECRET_BACKEND
```

## 3 - Build da Aplicação

Na raiz do projeto, execute:

```docker compose up -d --build```

O Docker Compose irá:
- Criar o banco de dados PostgreSQL 
- Inicializar os schemas do banco 
- Subir o Keycloak e importar automaticamente o Realm 
- Subir o backend já integrado ao Keycloak

## 4 - Serviços disponíveis

Após a inicialização, os serviços estarão disponíveis em:
- Keycloak (Admin Console): http://localhost:8080
  - Usuário: admin 
  - Senha: admin 
- Backend API: http://localhost:8081
- Frontend: http://localhost:4200

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
- Caso seja necessário reiniciar o ambiente do zero, execute:
  - ```docker compose down -v```
  - Então build novamente a aplicação.