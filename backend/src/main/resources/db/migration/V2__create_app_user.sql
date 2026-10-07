CREATE TABLE app_user (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_app_user
        PRIMARY KEY (id),

    CONSTRAINT uq_app_user_username
        UNIQUE (username),

    CONSTRAINT chk_app_user_username
        CHECK (BTRIM(username) <> '')
);