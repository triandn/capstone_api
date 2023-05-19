CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS time_book_details (
     time_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
     start_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
     end_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
     day_book_id UUID REFERENCES daybooks(day_book_id) NOT NULL ,
     is_payment BOOLEAN,
     created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
     updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);
