CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS guests (
     guest_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
     guest_type VARCHAR(255),
     quantity INT,
     time_id UUID,
     user_id UUID,
     created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     CONSTRAINT FK_time_book_detail FOREIGN KEY (time_id) REFERENCES time_book_details (time_id),
     CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);