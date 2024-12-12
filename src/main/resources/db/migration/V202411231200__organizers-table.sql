CREATE TABLE organizers
(
    id                       SERIAL PRIMARY KEY,
    name                     VARCHAR(255),
    location                 VARCHAR(255),
    contact_email            VARCHAR(255),
    parking_available_flag   BOOLEAN,
    animals_allowed_flag     BOOLEAN,
    disability_entrance_flag BOOLEAN,
    disability_toilet_flag   BOOLEAN,
    logo_url                 TEXT
);

INSERT INTO organizers (name, location, contact_email, parking_available_flag, animals_allowed_flag,
                        disability_entrance_flag, disability_toilet_flag)
VALUES ('Hrvatsko narodno kazalište', 'Trg Republike Hrvatske 15', 'hnk@hnk.hr', TRUE, FALSE, TRUE, TRUE),
       ('Muzej suvremene umjetnosti', 'Avenija Dubrovnik 17, 10000 Zagreb', 'media@msu.hr', TRUE, FALSE, TRUE, TRUE),
       ('Hrvatski prirodoslovni muzej', 'Demetrova 1', 'branimir.ivic@hpm.hr', FALSE, FALSE, TRUE, TRUE),
       ('Muzej Prigorja', 'Trg Dragutina Domjanića 5, 10360 Sesvete', 'info@muzejprigorja.hr', TRUE, TRUE, FALSE,
        FALSE),
       ('CENTAR ZA LIKOVNI ODGOJ GRADA ZAGREBA', 'ROKOV PERIVOJ 4, 10 000 ZAGREB', 'info@czlogz.hr', FALSE, FALSE,
        FALSE, FALSE),
       ('Tehnički muzej Nikola Tesla', 'Savska cesta 18', 'info@tmnt.hr', FALSE, FALSE, TRUE, TRUE),
       ('Muzej suvremene umjetnosti', 'Avenija Dubrovnik 17', 'msu@msu.hr', TRUE, FALSE, TRUE, TRUE),
       ('Muzej za umjetnost i obrt', 'Trg Republike Hrvatske 10', 'uprava@muo.hr', FALSE, FALSE, FALSE, FALSE),
       ('HRVATSKO NARODNO KAZALIŠTE U ZAGREBU', 'TRG REPUBLIKE HRVATSKE 15', 'miro.marketing@hnk.hr', FALSE, FALSE,
        TRUE, FALSE),
       ('Gradsko dramsko kazalište Gavella', 'Frankopanska 10, 10 000 Zagreb', 'niko.babajko@gavella.hr', FALSE, FALSE,
        TRUE, TRUE),
       ('Zagrebačka filharmonija', 'Trg Stjepana Radića 4', 'ivan.lozic@zgf.hr', TRUE, FALSE, TRUE, TRUE),
       ('Galerija Klovićevi dvori', 'Jezuitski trg 4, Zagreb', 'info@gkd.hr', FALSE, FALSE, TRUE, TRUE),
       ('Gradsko kazalište Trešnja', 'Mošćenička 1', 'tresnjatresnjevka@gmail.com', FALSE, FALSE, TRUE, FALSE),
       ('Centar za kulturu Novi Zagreb', 'Trg Narodne Zaštite 2', 'marketing@czk-novi-zagreb.hr', TRUE, FALSE, FALSE,
        FALSE),
       ('Knjižnice grada Zagreba', 'Starčevićev trg 6', 'maticna.sluzba@kgz.hr', FALSE, FALSE, TRUE, TRUE),
       ('Centar za kulturu i obrazovanje Susedgrad', 'Argentinska 5, 10090 Zagreb', 'info@czkio-susedgrad.hr', FALSE,
        FALSE, TRUE, TRUE),
       ('Hrvatski školski muzej, galerija "ŠKOLICA ZA 5"', 'HEBRANGOVA 5, (službena adresa Trg Republike Hrvatske 4)',
        'mbracic@hsmuzej.hr', TRUE, TRUE, FALSE, FALSE),
       ('Gradsko satiričko kazalište Kerempuh', 'Prolaz Fadila Hadžića 3', 'sonja.stanicic@kazalistekerempuh.hr', FALSE,
        FALSE, TRUE, TRUE),
       ('Centar za kulturno-društveni razvoj Novi prostori kulture', 'Savska cesta 28, 10 000 Zagreb',
        'info@noviprostorikulture.hr', FALSE, FALSE, FALSE, FALSE),
       ('POGON – ZAGREBAČKI CENTAR ZA NEZAVISNU KULTURU I MLADE', 'Kneza Mislava 11, 10 000 Zagreb', 'pr@pogon.hr',
        FALSE, TRUE, FALSE, FALSE),
       ('Umjetnički paviljon u Zagrebu', 'Trg kralja Tomislava 22', 'info@umjetnicki-paviljon.hr', FALSE, TRUE, TRUE,
        TRUE),
       ('Zagrebačko kazalište lutaka', 'Baruna Trenka 3 (ulaz za publiku)', 'prodaja@zkl.hr', FALSE, FALSE, TRUE,
        FALSE),
       ('Gradsko kazalište Žar ptica', 'Bijenička cesta 97', 'marketing@zar-ptica.hr', TRUE, FALSE, TRUE, FALSE),
       ('Kulturni centar Travno', 'Božidara Magovca 17, 10010 Zagreb', 'kuc@kuctravno.hr', TRUE, TRUE, TRUE, FALSE),
       ('Narodno sveučilište Dubrava', 'Cerska 1', 'ns-dubrava@ns-dubrava.hr', FALSE, FALSE, TRUE, TRUE),
       ('CENTAR KULTURE NA PEŠČENICI/CENTAR KNAP', 'Ivanićgradska 41 a', 'tajnistvo@knap.hr', TRUE, TRUE, TRUE, TRUE),
       ('Centar za kulturu i informacije Maksimir', 'Lavoslava Švarca 18, 10000 Zagreb, Hrvatska',
        'natalija.hrastic@ckim.hr', TRUE, TRUE, TRUE, TRUE),
       ('Knjižnice grada Zagreba - Knjižnica "M2"', 'Medveščak 71', 'djecja.knjiznica.m2@kgz.hr', FALSE, TRUE, FALSE,
        FALSE),
       ('Arheološki muzej u Zagrebu', 'Trg Nikole Šubića Zrinskog 19', 'dmaracic@asmz.hr', FALSE, FALSE, FALSE, TRUE),
       ('Muzej grada Zagreba', 'Opatička 20, 10 000 Zagreb', 'mgz@mgz.hr', FALSE, TRUE, TRUE, TRUE),
       ('Centar za kulturu Trešnjevka', 'Park Stara Trešnjevka 1', 'cekate.marketing@gmail.com', FALSE, FALSE, TRUE,
        FALSE),
       ('Koncertna dvorana Vatroslava Lisinskog', 'Trg Stjepana Radića 4', 'marketing@lisinski.hr', FALSE, FALSE, TRUE,
        TRUE),
       ('Centar mladih Ribnjak', 'Park Ribnjak 1', 'valentina.milkovic@cmr.hr', FALSE, TRUE, FALSE, TRUE);


CREATE TABLE organizer_types
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255),
    organizer_id INTEGER,
    FOREIGN KEY (organizer_id) REFERENCES organizers (id)
);

INSERT INTO organizer_types (name)
VALUES ('Gradska ustanova'),
       ('Korisnik koji koristi prostorije gradskih ustanova'),
       ('Drugo');

CREATE TABLE organizers_types
(
    organizer_id      INTEGER,
    organizer_type_id INTEGER,
    FOREIGN KEY (organizer_id) REFERENCES organizers (id),
    FOREIGN KEY (organizer_type_id) REFERENCES organizer_types (id)
);

INSERT INTO organizers_types (organizer_id, organizer_type_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1),
       (7, 1),
       (8, 1),
       (9, 3),
       (10, 1),
       (11, 1),
       (11, 2),
       (13, 1),
       (14, 1),
       (16, 1),
       (17, 1),
       (18, 1),
       (19, 1),
       (20, 1),
       (21, 1),
       (22, 1),
       (23, 1),
       (24, 1),
       (25, 1),
       (26, 1),
       (26, 3),
       (27, 1),
       (28, 1),
       (29, 1),
       (30, 1),
       (31, 1),
       (33, 1);