CREATE TABLE IF NOT EXISTS wallets (
      wallet_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
      account_number TEXT,
      total_money DECIMAL,
      user_id UUID REFERENCES  users(user_id) NOT NULL,
      created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
      updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);