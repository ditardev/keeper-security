create schema if not exists auth;

SET search_path TO auth;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS signed;
DROP TABLE IF EXISTS deleted;

CREATE TABLE IF NOT EXISTS users
(
    id        BIGSERIAL PRIMARY KEY,
    uuid      uuid         NOT NULL DEFAULT gen_random_uuid(),
    username  VARCHAR(50)  NOT NULL UNIQUE,
    firstname VARCHAR(50)  NOT NULL,
    lastname  VARCHAR(50)  NOT NULL,
    email     VARCHAR(50)  NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,
    role      VARCHAR(10)  NOT NULL DEFAULT 'USER',
    status    VARCHAR(10)  NOT NULL DEFAULT 'ACTIVE',
    created   timestamp    NOT NULL DEFAULT current_timestamp,
    updated   timestamp    NOT NULL DEFAULT current_timestamp
);

CREATE TABLE IF NOT EXISTS recovery
(
    id      SERIAL PRIMARY KEY,
    uuid    uuid        NOT NULL,
    email   VARCHAR(50) NOT NULL UNIQUE,
    code    VARCHAR(10) NOT NULL UNIQUE,
    created timestamp   NOT NULL DEFAULT current_timestamp,
    expired timestamp   NOT NULL DEFAULT current_timestamp
);
