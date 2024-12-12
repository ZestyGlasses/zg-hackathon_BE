CREATE TABLE user_favorite_events (
    user_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (event_id) REFERENCES events (id)
);