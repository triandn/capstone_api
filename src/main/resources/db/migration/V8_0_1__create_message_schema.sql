CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS messages (
    messages_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    message_type VARCHAR(50) CONSTRAINT message_message_type_check CHECK(message_type IN ('CLIENT','SERVER')),
    message TEXT,
    username VARCHAR(250),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);