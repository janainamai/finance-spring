CREATE TABLE public.users_roles (
	users_id uuid NOT NULL,
	roles_id uuid NOT NULL,
	CONSTRAINT fk_roles_id FOREIGN KEY (roles_id) REFERENCES public."role"(id),
	CONSTRAINT fk_users_id FOREIGN KEY (users_id) REFERENCES public.users(id),
	CONSTRAINT uq_users_roles UNIQUE (users_id, roles_id)
);