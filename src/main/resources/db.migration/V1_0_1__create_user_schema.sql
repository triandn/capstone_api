CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users (
     user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
     user_name VARCHAR(30) CONSTRAINT user_name_not_null_unique NOT NULL UNIQUE,
     email VARCHAR(255) NOT NULL,
     password VARCHAR(255) CONSTRAINT user_password_not_null NOT NULL,
     role VARCHAR(50) CONSTRAINT user_role_check CHECK(role IN ('ADMIN','USER')),
     is_enabled boolean,
     created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);