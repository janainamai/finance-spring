## About the project:

Project created to manage the finances of 2 individuals, mainly for study purposes.

## Instructions to run the project:

### Required installations:

- Java
    - Versions: 17

- Run 'docker-compose up' to let the environment receive the necessary configurations.

## API's

### Authentication APIs - Users
- register: performs the registration of a new user in the platform
  - POST http://localhost:8080/auth/register
- login: performs user login in the platform
  - POST http://localhost:8080/auth/login

### Authentication APIs - Roles
- createUserRole: assigns a role to a user 
  - POST http://localhost:8080/auth/roles
- getAll: returns all roles 
  - GET http://localhost:8080/role

### Finance APIs - Bank Account
- getAll: returns all bank accounts
  - GET http://localhost:8080/bank
- getById: returns the bank with the given id
  - GET http://localhost:8080/bank/{id}
- create: performs the registration of a new bank account
  - POST http://localhost:8080/bank
- update: performs the update of a existing bank account
  - PUT http://localhost:8080/bank
- deactivate: deactivate a existing bank account
  - PUT http://localhost:8080/bank/deactivate/{id}
- activate: activate a existing bank account
  - PUT http://localhost:8080/bank/activate/{id}

### Finance APIs - Transaction
- getById: returns the transaction with the given id
  - GET http://localhost:8080/transaction/{id}
- getAllByFilters: returns all transaction by informed filters
  - GET http://localhost:8080/transaction
- create: performs the registration of a new transaction
  - POST http://localhost:8080/transaction
- deleteById: updates the transaction as deleted and reverses the value in the respective bank account
  - DELETE http://localhost:8080/transaction/{id}