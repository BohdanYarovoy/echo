DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE users
(
    user_id       BIGINT AUTO_INCREMENT ,
    nickname      VARCHAR(50) UNIQUE  NOT NULL,
    first_name    VARCHAR(50)         NOT NULL,
    last_name     VARCHAR(50)         NOT NULL,
    patronymic    VARCHAR(50),
    sex           SMALLINT,
    email         VARCHAR(100) UNIQUE NOT NULL,
    password      VARCHAR(255)        NOT NULL,
    date_of_birth DATE,
    created       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    changed       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id)
);

CREATE TABLE roles
(
    role_id   SMALLINT AUTO_INCREMENT,
    role_name VARCHAR(50) UNIQUE NOT NULL,
    PRIMARY KEY (role_id)
);

CREATE TABLE users_roles
(
    user_id BIGINT   NOT NULL,
    role_id SMALLINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE CASCADE
);