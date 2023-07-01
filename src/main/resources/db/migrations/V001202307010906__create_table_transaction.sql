CREATE TABLE publictransaction (
  id UUID PRIMARY KEY,
  description VARCHAR(100) NOT NULL,
  amount NUMERIC(19, 2) NOT NULL,
  transaction_type VARCHAR(20) NOT NULL,
  event_date DATE NOT NULL,
  event_time TIME NOT NULL,
  bank_account_id UUID NOT NULL,
  CONSTRAINT fk_bank_account_id FOREIGN KEY (bank_account_id) REFERENCES bank_account (id)
);
