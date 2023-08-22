ALTER TABLE public.transaction ADD COLUMN IF NOT EXISTS deleted boolean DEFAULT false;

ALTER TABLE public.transaction ADD COLUMN IF NOT EXISTS deleted_on DATE NULL;