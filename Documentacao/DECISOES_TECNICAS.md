# Decisões Técnicas

## Stack
- Backend: Quarkus + Java/Kotlin
- Frontend: Angular + Nx
- Autenticação: Keycloak
- Banco de Dados: PostgreSQL

## Decisões Adotadas

### Autenticação e Autorização

Ao ser adotado o Keycloak como provedor de identidade utilizando OAuth2 com OpenID Connect. Optei pelo uso de **Realm Roles** em conjunto com **Groups**, permitindo:

- Mapeamento direto de roles no token JWT
- Uso nativo de `@RolesAllowed` no backend Quarkus
- Redução de lógica customizada de autorização

O client `backend` utiliza autenticação via **Client Credentials**, enquanto o `frontend` utiliza **Authorization Code Flow (PKCE)**.

O secret do client backend é configurado via variável de ambiente (.env), seguindo boas práticas de segurança.

Durante a integração em ambiente Docker, foi necessário ajustar a configuração de hostname do Keycloak para garantir consistência do `issuer` (`iss`) nos tokens JWT, evitando falhas de validação no backend.


### Backend


### Frontend

O frontend utiliza autenticação baseada em token (OAuth2/OIDC).
- O logout foi implementado de forma explícita limpando tokens locais e invalidando a sessão quando existente, evitando auto-login silencioso e garantindo previsibilidade do fluxo.


### Banco de dados


### Simplificações


### Possíveis Evoluções

