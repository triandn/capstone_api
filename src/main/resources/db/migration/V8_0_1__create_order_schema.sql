CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS orders (
        order_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
        status_order VARCHAR(20) CONSTRAINT valid_status_order CHECK ( status_order IN ('SUCCESS','FAILURE','CANCEL','WAITING','USED')),
        time_id UUID REFERENCES time_book_details(time_id) NOT NULL,
        order_date DATE,
        user_id UUID REFERENCES  users(user_id) NOT NULL,
        created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
        updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);