ALTER TABLE public.bank_account ADD COLUMN IF NOT EXISTS version BIGINT DEFAULT 0 NOT NULL;