CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS daybooks (
      day_book_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
      date_name DATE,
      status VARCHAR(255),
      tour_id SERIAL,
      created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
      updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
      CONSTRAINT FK_tour FOREIGN KEY (tour_id) REFERENCES tours (tour_id)
);