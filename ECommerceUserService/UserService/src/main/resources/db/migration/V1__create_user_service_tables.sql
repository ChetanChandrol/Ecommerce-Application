CREATE TABLE role
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    role VARCHAR(255)
);

CREATE TABLE user
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(255),
    email    VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE session
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    token          VARCHAR(255),
    login_at       DATETIME,
    expiring_at    DATETIME,
    user_id        BIGINT,
    session_status VARCHAR(255),
    CONSTRAINT fk_session_user
        FOREIGN KEY (user_id)
            REFERENCES user (id)
);

CREATE TABLE user_roles
(
    user_id  BIGINT NOT NULL,
    roles_id BIGINT NOT NULL,
    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
            REFERENCES user (id),
    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (roles_id)
            REFERENCES role (id)
);
