# Execução do Projeto

## 1 - Pré-requisitos
- Docker
- Docker Compose

## Subir a aplicação
```bash
docker-compose up -d
```

# KEYCLOAK
## Autenticação e Autorização

Este projeto utiliza Keycloak para autenticação e autorização.

A configuração completa está em [keycloak-setup.md](keycloak-setup.md)


- Autenticação via Keycloak (OAuth2 + OIDC)
- Frontend usa Authorization Code Flow (PKCE)
- Backend valida JWT
- Controle de acesso por role no backend

### Perfis de Usuário
- ALUNO: matrícula
- COORDENADOR: gestão de matriz

### Regras de Acesso
- Coordenadores acessam apenas matrizes sob sua responsabilidade.
- Alunos acessam apenas suas próprias matrículas.
- As permissões são validadas tanto no frontend quanto no backend.

### Observações
- Tokens JWT são utilizados para controle de sessão.
- As rotas são protegidas por perfil.