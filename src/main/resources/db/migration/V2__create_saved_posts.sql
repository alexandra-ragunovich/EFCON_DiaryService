CREATE TABLE saved_posts (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_saved_post FOREIGN KEY (post_id) REFERENCES diary_post(id) ON DELETE CASCADE,
    UNIQUE (user_id, post_id)
);