CREATE TABLE PROPERTIES
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    application VARCHAR(255) NOT NULL,
    profile     VARCHAR(255) NOT NULL,
    label       VARCHAR(255) NOT NULL,
    prop_key    VARCHAR(255) NOT NULL,
    prop_value  VARCHAR(500) NOT NULL
);

-- Optional: Add an initial test record
INSERT INTO PROPERTIES (application, profile, label, prop_key, prop_value)
VALUES ('EcommerceUserService', 'default', 'main', 'user.welcome.message', 'Hello from Flyway & JPA!');