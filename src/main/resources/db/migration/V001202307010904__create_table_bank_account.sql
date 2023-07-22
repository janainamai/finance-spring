CREATE TABLE public.bank_account (
  id UUID PRIMARY KEY,
  name VARCHAR(30) NOT NULL UNIQUE,
  description VARCHAR(50),
  active BOOLEAN DEFAULT TRUE
);