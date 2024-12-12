CREATE TABLE neighbourhoods
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO neighbourhoods (name)
VALUES ('Donji grad'),
       ('Trnje'),
       ('Maksimir'),
       ('Črnomerec'),
       ('Gornji grad - Medveščak'),
       ('Donja Dubrava'),
       ('Gornja Dubrava'),
       ('Sesvete'),
       ('Podsused - Vrapče'),
       ('Podsljeme'),
       ('Novi Zagreb - istok'),
       ('Novi Zagreb - zapad'),
       ('Stenjevec'),
       ('Brezovica'),
       ('Trešnjevka - sjever'),
       ('Trešnjevka - jug'),
       ('Peščenica - Žitnjak');


CREATE TABLE event_recurrences
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO event_recurrences (name)
VALUES ('događaj se odvija samo jednom'),
       ('događaj se ponavlja tjedno na određen dan u tjednu'),
       ('događaj se ponavlja mjesečno na određen datum'),
       ('događaj se ponavlja na određen dan u mjesecu i tjednu'),
       ('događaj se ponavlja na drugi način');

CREATE TABLE age_groups
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO age_groups (name)
VALUES ('djeca (0-15)'),
       ('mladi (16-29)'),
       ('odrasli (30+)');