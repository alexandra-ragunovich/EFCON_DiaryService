CREATE TABLE diary_post (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    location TEXT NOT NULL,
    travel_date VARCHAR(255),
    rating INTEGER,
    is_public BOOLEAN NOT NULL,
    duration VARCHAR(255),
    transport_info VARCHAR(255),
    expenses DOUBLE PRECISION,
    tips TEXT,
    created_at TIMESTAMP
);

CREATE TABLE tag (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE post_tags (
    post_id BIGINT,
    tag_id BIGINT,
    PRIMARY KEY (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES diary_post(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
);

CREATE TABLE photo (
    id BIGSERIAL PRIMARY KEY,
    url TEXT,
    post_id BIGINT,
    FOREIGN KEY (post_id) REFERENCES diary_post(id) ON DELETE CASCADE
);

CREATE TABLE comment (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    text TEXT NOT NULL,
    post_id BIGINT,
    created_at TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES diary_post(id) ON DELETE CASCADE
);