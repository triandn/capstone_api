ALTER TABLE users DROP CONSTRAINT user_role_check;
ALTER TABLE users ADD CONSTRAINT user_role_check CHECK(role IN ('ADMIN', 'USER', 'OWNER'));