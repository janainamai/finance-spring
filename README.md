## Sobre o projeto:

Projeto criado para gerenciar as finanças de 2 pessoas, objetivando o estudo principalmente.

## Instruções para executar o projeto:

### Instalações necessárias:

- PostgreSQL
    - Porta: 5432
    - Username: postgres
    - Password: postgres

- Java
    - Versão: 17

## API's

### API's Authentication
- registerUser: realiza o cadastro de um novo usuário na plataforma <br>
  - POST http://localhost:8080/auth/register
- login: realiza o login do usuário na plataforma
  - POST http://localhost:8080/auth/login
- createUserRole: atribui uma role em um usuário 
  - POST http://localhost:8080/auth/roles
- getAllRoles: retorna todas as roles cadastradas 
  - GET http://localhost:8080/role

### API's Finance - Bank Account
- retrieveAll: retorna todas as contas bancárias
  - GET http://localhost:8080/bank
- create: realiza o cadastro de uma nova conta bancária
  - POST http://localhost:8080/bank