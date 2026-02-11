# Documentação da API - Sistema de Matriz Curricular

Guia de referência para autenticação e operações de matrícula.

---

## 1. Autenticação (Keycloak)

Para interagir com os endpoints protegidos, é necessário obter o token de acesso.

* **Endpoint:** `POST http://localhost:8080/realms/matriz-curricular/protocol/openid-connect/token`
* **Header:** `Content-Type: application/x-www-form-urlencoded`

### Credenciais de Teste
| Perfil          | Usuário  | Senha            |
|:----------------|:---------|:-----------------|
| **Aluno**       | `aluno1` | `unifor123`      |
| **Coordenador** | `coord1` | `coordenacao123` |

### Parâmetros do Body
| Chave        | Valor        |
|:-------------|:-------------|
| `client_id`  | `backend`    |
| `grant_type` | `password`   |
| `username`   | `{username}` |
| `password`   | `{password}` |

---

## 2. Endpoints do Coordenador

### Criar Aula
Cria uma nova oferta de disciplina na matriz.

- **URL:** `POST http://localhost:8081/api/aulas`
- **Auth:** `Bearer <TOKEN_COORDENADOR>`
- **Body (JSON):**

```json
{
  "disciplinaId": 1,
  "professorId": 2,
  "horarioId": 3,
  "vagasMaximas": 40,
  "cursosAutorizadosIds": [1, 3]
}
```

### Editar Aula
Editar oferta de disciplina na matriz.

- **URL:** `PUT http://localhost:8081/api/aulas/{id}`
- **Auth:** `Bearer <TOKEN_COORDENADOR>`
- **Body (JSON):**

```json
{
  "disciplinaId": 1,
  "professorId": 2,
  "horarioId": 3,
  "vagasMaximas": 40,
  "cursosAutorizadosIds": [1, 3]
}
```

### Excluir Aula
Excluir oferta de disciplina na matriz.

- **URL:** `DELETE http://localhost:8081/api/aulas/{id}`
- **Auth:** `Bearer <TOKEN_COORDENADOR>`


## 3. Endpoints do Aluno


Realizar Matrícula

Inscreve o aluno em uma aula específica através do ID.

- **URL**: `POST http://localhost:8081/api/matriculas/{aulaId}`

- **Auth**: `Bearer <TOKEN_ALUNO>`

Listar Minhas Matrículas

Consulta as matrículas já realizadas pelo aluno autenticado.

- **URL**: `GET http://localhost:8081/api/matriculas`

- **Auth**: `Bearer <TOKEN_ALUNO>`

## 4. Testando a API com Postman


A collection do Postman está disponível em:

docs/postman/matriz-curricular.postman_collection.json

Para importar:

1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo JSON

Será feito a importação da collection base para teste