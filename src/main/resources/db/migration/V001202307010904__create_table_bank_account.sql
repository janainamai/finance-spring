CREATE TABLE public.bank_account (
  id UUID PRIMARY KEY,
  name VARCHAR(30) NOT NULL UNIQUE,
  description VARCHAR(50),
  total_balance numeric(19, 2) NOT NULL,
  active BOOLEAN DEFAULT TRUE
);