CREATE TABLE event_categories
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO event_categories (name)
VALUES ('Glazba'),
       ('Film'),
       ('Kazalište'),
       ('Muzeji'),
       ('Ples'),
       ('Likovna umjetnost'),
       ('Razno'),
       ('Književnost'),
       ('Interdisciplinarni program'),
       ('Diskurzivni program');


CREATE TABLE event_types
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO event_types (name)
VALUES ('Koncert'),
       ('Festival'),
       ('Izložba'),
       ('Predstava'),
       ('Manifestacija'),
       ('Kino'),
       ('Panel'),
       ('Radionica'),
       ('Razno'),
       ('Balet'),
       ('Opera'),
       ('Predstavljanje'),
       ('Skup'),
       ('Predavanje'),
       ('Performans');