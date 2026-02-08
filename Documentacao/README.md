# Documentação do Projeto

Este diretório contém a documentação técnica do projeto desenvolvido para o desafio técnico.

A documentação está organizada por assunto, facilitando a navegação e avaliação.

## Conteúdo

- [Execução do Projeto](EXECUCAO.md)
- [Decisões Técnicas](DECISOES_TECNICAS.md)

> Recomenda-se iniciar pela documentação de execução.

TODO DOCUMENTAÇÃO
- Criando containers dockers para banco, keycloak, backend, frontend
- Configurando para que container keycloak save o volume de dados em banco para cada vez que for recriado o container não perder os dados
- Corrigindo .dockerignore para buildar corretamente o backend


- Criado .env no projeto raiz para setar o secret id do client backend, mas não expondo no git (explicar no passo a passo para ser criado antes)
  - Criar arquivo .env na raiz do projeto:
    - KEYCLOAK_BACKEND_SECRET=CLIENT_SECRET_BACKEND
  - Configuração já feito no ```application.properties``` e ```docker-compose.yml```
  - Visualizar .env no container pra ver se foi criado: 
    - docker exec -it matriz-curricular-backend printenv | grep KEYCLOAK


- Criado script de inicialização ```init-db.sh```. Este script será executado pelo Postgres apenas na primeira vez que o volume for criado. 
Necessário para criação do banco, schema persistências das configs do keycloak e schema do backend. Para que se o container for parado ou removido, as informações não se percam.


- Criado Importação Automática do Keycloak: Foi exportado uma configuração básica no Keycloak conforme o arquivo ```keycloak-setup.md``` para um arquivo JSON ```matriz-realm.json``` , 
onde o Docker vai importar esse arquivo no container novo sempre que for criado, garantindo assim um cenário de inicial de teste da aplicação.
 - Exportar arquivo .json:
   - docker exec matriz-curricular-keycloak /opt/keycloak/bin/kc.sh export --file /tmp/matriz-realm.json --realm nome_do_realm 
   - Nos logs gerará um erro mas é porque o serviço do keycloak está usando a mesma porta que o processo de exportar o json, mas o importante é verificar se o arquivo foi criado. 
 - Verificar se o arquivo foi criado:
   - docker exec matriz-curricular-keycloak ls -lh /tmp/matriz-realm.json
 - Crie a pasta no seu projeto primeiro
   - mkdir -p keycloak-config
 - Copie o arquivo 
   - docker cp matriz-curricular-keycloak:/tmp/matriz-realm.json ./keycloak-config/matriz-realm.json
 - Crie um volume no serviço Keycloak no arquivo docker-compose.yml:
   - ./keycloak-config/matriz-realm.json:/opt/keycloak/data/import/realm.json

- Autenticando tokens do client e dos usuários no backend e testando endpoints com validação de Roles.
- Removendo necessidade do campo e-mail dos usuários no Realm settings > User profile > Edit attribute
- Corrigindo erro de iss claim value: Ao realizar requisições pro backend via Postman mesmo com o token válido, o backend
não confia nele porque o Keycloak gerou o token com iss = localhost e o Quarkus espera iss = keycloak.
  - Foi realizado um ajuste no ```docker-compose.yml``` onde ao setar KC_HOSTNAME: http://localhost:8080, KC_HOSTNAME_STRICT: false,
    e KC_HOSTNAME_BACKCHANNEL_DYNAMIC: true. Para evitar inconsistências de issuer entre backend, frontend e keycloak e manter simplicidade no setup Docker.