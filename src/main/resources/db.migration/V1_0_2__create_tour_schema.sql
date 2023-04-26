CREATE TABLE IF NOT EXISTS tours (
     tour_id SERIAL PRIMARY KEY,
     title VARCHAR(255)  NOT NULL,
     rating FLOAT NOT NULL,
     city VARCHAR(255) NOT NULL,
     price_one_person  FLOAT NOT NULL,
     image_main TEXT NOT NULL,
     working TEXT NOT NULL,
     destination TEXT NOT NULL,
     destination_description TEXT NOT NULL,
     created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);