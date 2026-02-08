# Configuração do Keycloak

Este documento descreve a configuração do Keycloak utilizada no projeto, incluindo a criação do Realm, Roles, Clients e usuários de teste.

A configuração pode ser realizada **manualmente** para entendimento do processo ou **automaticamente**, utilizando importação via Docker.

---

## Acesso ao Keycloak

Após subir os containers, acesse o **Admin Console**:

- URL: http://localhost:8080
- Usuário: `admin`
- Senha: `admin`

### Account Console (usuários finais)

- URL: http://localhost:8080/realms/matriz-curricular/account
- Login: `usuario`
- Senha: `senha_usuario`

---

## Configuração Manual

### Realm

Criar um novo Realm com o nome:

- `matriz-curricular`

---

### Roles

Criar os seguintes papéis (Realm Roles):

- `ALUNO`
- `COORDENADOR`

---

### Clients

#### Client: backend

- Client type: OpenID Connect
- Client ID: `backend`
- Name: Backend API
- Client authentication:  ON
- Authorization:  OFF
- Standard flow:  OFF
- Direct access grants:  OFF
- Service accounts roles:  ON
- URLs: (pode deixar vazio)

---

#### Client: frontend

- Client type: OpenID Connect
- Client ID: `frontend`
- Name: Frontend
- Client authentication:  OFF
- Authorization:  OFF
- Standard flow:  ON
- Direct access grants:  OFF
- Service accounts roles:  OFF
- Root URL: http://localhost:4200
- Valid redirect URIs: http://localhost:4200/*
- Web origins: http://localhost:4200

---

### Grupos

Criar os seguintes grupos:

- `alunos`
- `coordenadores`

Em cada grupo:

- Acessar **Role mapping**
- Atribuir a role correspondente ao grupo

---

### Usuários de Teste

#### Usuário Aluno

- Username: `aluno1`
- Email verified: ON (opcional)
- Groups: `alunos`

Definir senha:
- Aba **Credentials**
- Temporary: OFF

> Não é necessário atribuir a role manualmente caso o usuário pertença ao grupo correto.

---

#### Usuário Coordenador

- Username: `coord1`
- Email verified: ON (opcional)
- Groups: `coordenadores`

Definir senha:
- Aba **Credentials**
- Temporary: OFF

> Não é necessário atribuir a role manualmente caso o usuário pertença ao grupo correto.

---

### Usuários Criados

| Usuário | Nome   | Sobrenome  | Senha          | Grupo         | Papel       |
|---------|--------|------------|----------------|---------------|-------------|
| aluno1  | Daniel | Nascimento | unifor123      | alunos        | ALUNO       |
| coord1  | João   | Carlos     | coordenacao123 | coordenadores | COORDENADOR |

---

## Configuração Automática (Importação via Docker)

Para facilitar a execução do projeto, a configuração do Keycloak é importada automaticamente ao subir os containers.

### Exportar o Realm para JSON

```bash
  docker exec matriz-curricular-keycloak \
  /opt/keycloak/bin/kc.sh export \
  --file /tmp/matriz-realm.json \
  --realm matriz-curricular
```

### Verificar se o arquivo foi criado

```docker exec matriz-curricular-keycloak ls -lh /tmp/matriz-realm.json```

### Copiar o arquivo para o projeto

```mkdir -p keycloak-config```

```docker cp matriz-curricular-keycloak:/tmp/matriz-realm.json ./keycloak-config/matriz-realm.json```

### Importação automática via Docker Compose

No docker-compose.yml, o arquivo é montado no container do Keycloak:

```./keycloak-config/matriz-realm.json:/opt/keycloak/data/import/realm.json```

**Assim, sempre que o container do Keycloak for recriado, o Realm com toda configuração será importado automaticamente, 
garantindo um ambiente inicial consistente para os testes.**