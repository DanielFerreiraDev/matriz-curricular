# Estágio 1: Build
FROM node:20.20.0-bookworm-slim AS builder
WORKDIR /app

RUN npm config set registry https://registry.npmjs.org/
COPY package.json package-lock.json nx.json tsconfig.base.json ./
RUN npm ci

COPY . .
RUN npx nx build matrizcurricular --configuration=production

FROM nginx:stable-alpine
COPY --from=builder /app/dist/apps/matrizcurricular/browser /usr/share/nginx/html

RUN printf 'server { \n\
    listen 80; \n\
    location / { \n\
        root /usr/share/nginx/html; \n\
        index index.html index.htm; \n\
        try_files $uri $uri/ /index.html; \n\
    } \n\
}' > /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
