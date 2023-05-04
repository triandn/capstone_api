CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS time_book_details (
     time_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
     start_time TIME NOT NULL,
     end_time TIME NOT NULL,
     tour_id SERIAL,
     day_book_id UUID,
     is_payment BOOLEAN,
     user_id UUID,
     created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     CONSTRAINT FK_tour FOREIGN KEY (tour_id) REFERENCES tours (tour_id),
     CONSTRAINT FK_day_book FOREIGN KEY (day_book_id) REFERENCES daybooks (day_book_id),
     CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);
