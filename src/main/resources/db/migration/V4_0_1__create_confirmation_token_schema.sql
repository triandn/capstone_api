CREATE TABLE IF NOT EXISTS verification_tokens (
     token_id SERIAL PRIMARY KEY,
     confirmation_token TEXT,
     user_id UUID,
     created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);