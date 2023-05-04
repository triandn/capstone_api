CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS reviews (
     review_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
     rating INTEGER CONSTRAINT valid_rating CHECK (rating > 0 AND rating <= 5) NOT NULL,
     comment TEXT,
     user_id UUID,
     tour_id SERIAL,
     created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     CONSTRAINT FK_tour FOREIGN KEY (tour_id) REFERENCES tours (tour_id),
     CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);
