CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS image_details (
     image_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
     link TEXT,
     tour_id SERIAL,
     created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     CONSTRAINT FK_tour FOREIGN KEY (tour_id) REFERENCES tours (tour_id)
);