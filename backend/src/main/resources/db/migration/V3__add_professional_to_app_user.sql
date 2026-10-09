ALTER TABLE app_user
    ADD COLUMN professional_id BIGINT NOT NULL;

ALTER TABLE app_user
    ADD CONSTRAINT fk_app_user_professional
        FOREIGN KEY (professional_id)
        REFERENCES professional (id)
        ON DELETE RESTRICT;

ALTER TABLE app_user
    ADD CONSTRAINT uk_app_user_professional
        UNIQUE (professional_id);