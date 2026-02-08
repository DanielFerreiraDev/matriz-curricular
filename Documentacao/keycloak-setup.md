# Configuração Keycloak

Após iniciar container do keycloak:

Acessar o Admin Console.

http://localhost:8080

Login no Account Console com usuários:
http://localhost:8080/realms/matriz-curricular/account

- Login: admin
- Senha: admin

## Realm
Criar novo Realm

- Nome: matriz-curricular

## Roles
Criar papeis necessários
- ALUNO
- COORDENADOR

## Clients

### backend
- Client type: OpenID Connect
- Client ID: backend
- Name: Backend API
- Client authentication: ✅ ON 
- Authorization: ❌ OFF 
- Standard flow: ❌ OFF 
- Direct access grants: ❌ OFF 
- Service accounts roles: ✅ ON
- URLs (pode deixar vazio)

### frontend
- Client type: OpenID Connect 
- Client ID: frontend
- Name: Frontend
- Client authentication: ❌ OFF 
- Authorization: ❌ OFF 
- Standard flow: ✅ ON 
- Direct access grants: ❌ OFF 
- Service accounts roles: ❌ OFF
- Root URL: http://localhost:4200
- Valid redirect URIs: http://localhost:4200/*
- Web origins: http://localhost:4200

## Grupos

Criar grupo:

- alunos
- coordenadores

Em cada grupo:

Role mapping
- Atribuir role correspondente

## Usuários de Teste

**Usuário Aluno:**

- Username: aluno1
- Email verified: ✅ (opcional)
- First name:
- Last name:
- Groups: Join Groups (alunos)
- Create

Definir senha
- Aba Credentials
- Set password
- Temporary: ❌

Atribuir role (não necessário se já foi atribuido a um grupo)

- Aba Role mapping
- Assign role
- Realm roles → ALUNO
- Assign

**Usuário Coordenador:**

- Username: coord1
- Email verified: ✅ (opcional)
- First name:
- Last name:
- Groups: Join Groups (coordenadores)
- Create

Definir senha
- Aba Credentials
- Set password
- Temporary: ❌

Atribuir role (não necessário se já foi atribuido a um grupo)

- Aba Role mapping
- Assign role
- Realm roles → COORDENADOR
- Assign


| Usuário    | Nome   | Sobrenome  | Senha          | Grupo         | Papel       |
|------------|--------|------------|----------------|---------------|-------------|
| aluno1     | Daniel | Nascimento | unifor123      | alunos        | ALUNO       |
| joaocarlos | João   | Carlos     | coordenacao123 | coordenadores | COORDENADOR |
