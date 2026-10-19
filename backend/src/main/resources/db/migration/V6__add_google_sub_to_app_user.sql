ALTER TABLE app_user
ADD COLUMN google_sub VARCHAR(255);

CREATE UNIQUE INDEX uk_app_user_google_sub
ON app_user (google_sub);