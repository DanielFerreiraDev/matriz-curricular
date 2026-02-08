#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER keycloak WITH PASSWORD 'keycloak';
    CREATE DATABASE keycloak;
    GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak;
    ALTER DATABASE keycloak OWNER TO keycloak;
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "keycloak" <<-EOSQL
    CREATE SCHEMA keycloak AUTHORIZATION keycloak;
    GRANT ALL ON SCHEMA keycloak TO keycloak;
    ALTER ROLE keycloak SET search_path TO keycloak;
EOSQL


psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "matrizcurricular" <<-EOSQL
    CREATE SCHEMA IF NOT EXISTS matrizcurricular AUTHORIZATION postgres;
    ALTER ROLE postgres SET search_path TO matriz, public;
EOSQL