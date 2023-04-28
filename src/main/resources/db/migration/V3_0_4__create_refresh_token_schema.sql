CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS refresh_tokens (
     refresh_token_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
     token TEXT,
     user_id UUID,
     expiry_date DATE,
     created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);