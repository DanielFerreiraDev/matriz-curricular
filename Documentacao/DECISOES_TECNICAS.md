# Decisões Técnicas

## Stack
- Backend: Quarkus + Java
- Frontend: Angular + Nx
- Autenticação: Keycloak
- Banco de Dados: PostgreSQL
- Integração: Docker

## Decisões Adotadas

### Autenticação e Autorização

Ao ser adotado o Keycloak como provedor de identidade utilizando OAuth2 com OpenID Connect. Optei pelo uso de **Realm Roles** em conjunto com **Groups**, permitindo:

- Mapeamento direto de roles no token JWT
- Uso nativo de `@RolesAllowed` no backend Quarkus
- Redução de lógica customizada de autorização

- O client `backend` utiliza autenticação via **Client Credentials**, enquanto o `frontend` utiliza **Authorization Code Flow (PKCE)**.
- O secret do client backend é configurado via variável de ambiente (.env), seguindo boas práticas de segurança. **Por conta de ser um desafio técnico o .env foi exposto no git afim de facilitar o teste**
- Durante a integração em ambiente Docker, foi necessário ajustar a configuração de hostname do Keycloak para garantir consistência do `issuer` (`iss`) nos tokens JWT, evitando falhas de validação no backend.
- Para validar cursos associados aos usuários (alunos), foi criado um novo atributo de usuário: ```cursoId``` 
para que seja consumido pelo backend e assim validar se a aula está autorizada para o curso do aluno. 
- Foi necessário associar um client scope com esse atributo de usuário para os clients da aplicação 

**Todas configs do Keycloak são importadas automaticamente na criação do container através do arquivo ```matriz-realm.json``` na pasta keycloak-config**

### Backend

- O backend consome o sub do JWT como identificador do usuário e usa esse valor como chave primária nas entidades acadêmicas, garantindo desacoplamento entre autenticação e domínio.
- Foi configurado no application.properties uma forma que o projeto possa ser testado tanto localmente quando em container Docker. Atribuindo tags de dev ou prod nas configs.

### Frontend

O frontend utiliza autenticação baseada em token (OAuth2/OIDC).
- O logout foi implementado de forma explícita limpando tokens locais e invalidando a sessão quando existente, evitando auto-login silencioso e garantindo previsibilidade do fluxo.
- A implementação da integração do frontend com o back não foi possível por questão de tempo, então foi priorizado o fluxo de Login e Logout.
Deixando a visualização da tela limpa de todas as informações não necessárias no momento.

### Banco de dados

- As tabelas e entidades base (disciplinas, professores, cursos, horários) são pré-carregadas via script SQL, sendo rodado pelo Docker. 
conforme especificado no desafio, e são apenas consumidas pelo sistema.
- “O schema é controlado por scripts SQL versionados. O Hibernate está configurado apenas para leitura do modelo.”

### Simplificações

- Toda lógica do fluxo foi colocada no diretório services do backend afim de centralizar toda regra de negócio
- No fluxo de edição aula da matriz curricular: Não é permitido editar aulas se tiverem alunos matriculados
- Para melhoria no build dos containers foi feito o upload das imagens gerados pelos Dockerfilers no DockerHub, com isso,
será feito a construção mais segura e eficiente.

### Possíveis Evoluções

- migration das tabelas do banco para se tornar mais escalável.
- Melhorar fluxo de edição das Aulas.
- Refatoração de métodos
- Pensar em utilizar respositories pra consultas sql
- Melhorar design das telas 
- Melhorar fluxo de UI do usuário
- Implementar mensagens de erro ao ser lançado exceções nas requisições

