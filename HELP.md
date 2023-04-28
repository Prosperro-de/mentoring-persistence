# Welcome to the SQL initial module


### №1

* Install or run PostreSQL local server <br>
  You can use ***docker docker run -p 5433:5432 -e POSTGRES_PASSWORD=password postgres*** command for running a docker image
* Connect to the DB from whatever DB client you want

### №2

* Create tables: <br>


Libraries - has id, name, address, customers and books.

Books - has id, name, genre, author, customer who read the book at the time, isFree field that say if the book is available at the time, time when the book was taken

Customers <br> - has id, firstName, lastName, email, address <br>

You need to define:
- what types of relationships requires the database to keep the DB normalized
- what types of data should we use for each field
- what constraints should we apply to the tables <br><br>
  Links:
- PostgreSQL documentation: https://www.postgresql.org/docs/current/index.html
- Constraints: https://www.w3schools.com/sql/sql_constraints.asp