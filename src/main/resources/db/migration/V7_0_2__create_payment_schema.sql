CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS payments (
      payment_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
      vnp_order_info VARCHAR(255),
      order_type VARCHAR(255),
      amount DECIMAL NOT NULL,
      locate VARCHAR(255) NOT NULL,
      ip_address VARCHAR(255) NOT NULL,
      payment_url TEXT,
      status VARCHAR(20) CONSTRAINT valid_status CHECK ( status IN ('WAITING', 'SUCCESS', 'FAILURE')),
      tnx_ref VARCHAR(255) NOT NULL,
      time_over DATE NOT NULL,
      time_id UUID REFERENCES time_book_details(time_id) NOT NULL,
      user_id UUID REFERENCES  users(user_id) NOT NULL,
      created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
      updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);