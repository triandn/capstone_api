CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS history_transactions (
  transaction_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  status VARCHAR(50),
  transaction_date DATE,
  money DECIMAL,
  order_id UUID REFERENCES orders(order_id) NOT NULL,
  user_id UUID REFERENCES  users(user_id) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);