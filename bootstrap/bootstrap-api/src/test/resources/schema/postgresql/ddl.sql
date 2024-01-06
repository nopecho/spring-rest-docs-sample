CREATE TABLE IF NOT EXISTS tester
(
    id               BIGINT PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    created_at       TIMESTAMPTZ           DEFAULT NOW(),
    modified_at      TIMESTAMPTZ           DEFAULT NOW(),
    deleted_at       TIMESTAMPTZ           DEFAULT NULL
);