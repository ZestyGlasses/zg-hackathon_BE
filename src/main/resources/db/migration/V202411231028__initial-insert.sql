CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,
    username      VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    date_created  TIMESTAMP,
    date_modified TIMESTAMP,
    user_created  VARCHAR(255),
    user_modified VARCHAR(255)
);

CREATE TABLE user_roles
(
    id      SERIAL PRIMARY KEY,
    user_id INT          NOT NULL,
    role    VARCHAR(255) NOT NULL
);