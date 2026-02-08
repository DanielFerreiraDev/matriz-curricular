# Decisões Técnicas

## Stack
- Backend: Quarkus + Java/Kotlin
- Frontend: Angular + Nx
- Autenticação: Keycloak
- Banco de Dados: PostgreSQL

## Decisões Adotadas

### Autenticação
- Na autenticação, optei por utilizar Realm Roles no Keycloak para simplificar o controle de acesso no backend com Quarkus, 
permitindo o uso direto de @RolesAllowed, reduzindo complexidade e risco de erro.
- Sobre gerenciamento de usuários, optei por criar grupos para que os usuários possam a herdar roles do grupo.
- O secret do client backend é configurado via variável de ambiente (.env), conforme boas práticas de segurança.


### Backend


### Frontend


### Banco de dados


### Simplificações


### Possíveis Evoluções

