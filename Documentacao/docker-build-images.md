# Construção das Imagens Docker (backend, frontend)

Este documento descreve a forma como foi feito o build das imagens do backend e frontend da aplicação para o DockerHub.

---

## 1 - Conta no DockerHub

Primeiramente é necessário ter uma conta no DockerHub antes de iniciarmos:

Guarde suas credenciais de acesso:
- Usuário: `user`
- Senha: `password`

---

## 2 - Criar repositórios

Logar na sua conta e então crie os repositórios:

- seu-user/matriz-curricular-backend 
- seu-user/matriz-curricular-frontend

---

## 3 - Login via Terminal

No terminal no diretório raiz do projeto (matrizcurricular), execute:

- ```docker login -u [seu-user]```

Digitar senha:

- Agora você está logado e pronto pra subir suas imagens

## 3 - Buildando Imagem Backend

- Execute comando no diretório raiz do projeto (matrizcurricular):

 ```docker build -t seu-user/matriz-curricular-backend:latest ./backend```

## 4 - Subindo Imagem Backend

- Logo após o build da imagem ser concluído com sucesso, execute:

```docker push seu-user/matriz-curricular-backend:latest```

---

> Lembrar de substituir o trecho ```seu-user``` pelo usuário do seu dockerhub, no meu caso é ```docker0599```

## 5 - Buildando Imagem Frontend

- Execute comando no diretório raiz do projeto (matrizcurricular):

```
   docker build -f ./frontend/apps/matrizcurricular/Dockerfile \
  -t seu-user/matriz-curricular-frontend:latest \
  ./frontend
```

## 6 - Subindo Imagem Frontend

- Logo após o build da imagem ser concluído com sucesso, execute:

```docker push seu-user/matriz-curricular-frontend:latest```

---
> Lembrar de substituir o trecho ```seu-user``` pelo usuário do seu dockerhub, no meu caso é ```docker0599```

## 7 - Dockerfiles:
- Os arquivos Dockerfile de cada serviço estarão no projeto, para cada novo lançamento de versão dessas imagens para o DockerHub
    - Backend: ```./backend/Dockerfile```
    - Frontend: ```./frontend/apps/Dockerfile```
        - No arquivo ```docker-compose.yml``` adicione no serviço.
      ```
      backend
        build:
        context: ./backend
      ```
      ``` 
      frontend
        build: 
             context: ./frontend
             dockerfile: apps/matrizcurricular/Dockerfile
      ```
      
> Após as mudanças seja no back ou front, realizar os passos acima para subir novamente as imagens atualizadas
> Dica: Lançar tags das imagens de acordo com a versão do projeto (invés de latest)