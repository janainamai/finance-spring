CREATE TABLE public.user_account_roles (
	user_account_id uuid NOT NULL,
	roles_id uuid NOT NULL,
	CONSTRAINT fk_role_id FOREIGN KEY (roles_id) REFERENCES public."role"(id),
	CONSTRAINT fk_user_account_id FOREIGN KEY (user_account_id) REFERENCES public.user_account(id)
);