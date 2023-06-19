<h3>Planner - Authentication</h3>
Projeto responsável pela autenticação do usuário na plataforma.

<h3>Como executar o projeto:</h3>
Database: postgreSQL, porta 5432, username "postgres", password "postgres". 
<br>Não é necessário criar as tabelas, o sistema cria automaticamente.

<b>Script para inserir roles de admin e user:</b>
<br>INSERT INTO public."role"(id, "name") VALUES('01a41a44-4ea1-4f0b-b14d-2a1a5979418e'::uuid, 'USER');
<br>INSERT INTO public."role"(id, "name") VALUES('82de8f22-4485-4b57-8f34-bb5bdf64230f'::uuid, 'ADMIN');
