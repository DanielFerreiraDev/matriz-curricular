# Estágio 1: Build
FROM node:20.20.0-alpine AS builder
WORKDIR /app

COPY ../../package*.json ./
RUN npm install
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
