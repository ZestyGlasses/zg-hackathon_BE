CREATE TABLE events
(
    id                         SERIAL PRIMARY KEY,
    short_name                 VARCHAR(1000),
    long_name                  VARCHAR(1000),
    english_name               VARCHAR(1000),
    organizer_id               INT,
    location                   VARCHAR(255),
    neighbourhood_id           INT,
    event_recurrence_id        INT,
    start_date                 TIMESTAMP,
    end_date                   TIMESTAMP,
    child_friendly_flag        BOOLEAN,
    description                TEXT,
    description_english        TEXT,
    event_image_url            TEXT,
    price                      DECIMAL,
    ticket_amount              INT,
    numbered_tickets_flag      BOOLEAN,
    sign_up_required_flag      BOOLEAN,
    available_in_croatian_flag BOOLEAN,
    available_in_english_flag  BOOLEAN,
    sign_up_end_date           TIMESTAMP,
    event_url                  TEXT,
    FOREIGN KEY (organizer_id) REFERENCES organizers (id),
    FOREIGN KEY (neighbourhood_id) REFERENCES neighbourhoods (id),
    FOREIGN KEY (event_recurrence_id) REFERENCES event_recurrences (id)
);

CREATE TABLE events_age_groups
(
    event_id     INT,
    age_group_id INT,
    FOREIGN KEY (event_id) REFERENCES events (id),
    FOREIGN KEY (age_group_id) REFERENCES age_groups (id)
);

CREATE TABLE events_categories
(
    event_id    INT,
    category_id INT,
    FOREIGN KEY (event_id) REFERENCES events (id),
    FOREIGN KEY (category_id) REFERENCES event_categories (id)
);

CREATE TABLE events_types
(
    event_id      INT,
    type_id INT,
    FOREIGN KEY (event_id) REFERENCES events (id),
    FOREIGN KEY (type_id) REFERENCES event_types (id)
);