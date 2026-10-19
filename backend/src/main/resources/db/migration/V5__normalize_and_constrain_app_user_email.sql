UPDATE app_user
SET email = LOWER(BTRIM(email))
WHERE email IS NOT NULL;

CREATE UNIQUE INDEX uk_app_user_email_lower
ON app_user (LOWER(email));

ALTER TABLE app_user
ALTER COLUMN email SET NOT NULL;