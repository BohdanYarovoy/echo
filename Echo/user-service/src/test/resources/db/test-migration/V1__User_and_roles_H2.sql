DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_auths;
DROP TABLE IF EXISTS user_details;
DROP TABLE IF EXISTS roles;

CREATE TABLE users
(
    user_id    BIGINT AUTO_INCREMENT,
    nickname   VARCHAR(50) NOT NULL UNIQUE,
    avatar     BYTEA,
    created    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    changed    TIMESTAMP,
    is_deleted BIT       DEFAULT 0,
    PRIMARY KEY (user_id)
);

CREATE TABLE user_details
(
    user_detail_id BIGINT AUTO_INCREMENT,
    user_id        INTEGER UNIQUE,
    first_name     VARCHAR(50),
    last_name      VARCHAR(50),
    patronymic     VARCHAR(50),
    phone          VARCHAR(15) NOT NULL UNIQUE,
    about          VARCHAR(100),
    sex            SMALLINT,
    date_of_birth  DATE        NOT NULL,
    created        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    changed        TIMESTAMP,
    is_deleted     BIT       DEFAULT 0,
    PRIMARY KEY (user_detail_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE user_auths
(
    user_auth_id BIGINT AUTO_INCREMENT,
    user_id      INTEGER UNIQUE,
    email        VARCHAR(100) NOT NULL UNIQUE,
    password     VARCHAR(32)  NOT NULL,
    created      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    changed      TIMESTAMP,
    is_deleted   BIT       DEFAULT 0,
    PRIMARY KEY (user_auth_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE roles
(
    role_id   SMALLINT AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (role_id)
);

CREATE TABLE users_roles
(
    user_id BIGINT   NOT NULL,
    role_id SMALLINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (role_id) ON DELETE CASCADE
);