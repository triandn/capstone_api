CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS daybooks (
    guest_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    guest_type VARCHAR(255),
    quantity INT,
    tour_id SERIAL,
    time_id UUID,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    CONSTRAINT FK_tour FOREIGN KEY (tour_id) REFERENCES tours (tour_id),
    CONSTRAINT FK_tour FOREIGN KEY (time_id) REFERENCES time_book_details (time_id)
);