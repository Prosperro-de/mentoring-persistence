# Welcome to the SQL initial module

Answer the question:
- what is ACID?
- what is DB constraint?
- what types of tables relationships exists?
- difference between foreign key and primary key


### №1

* Install or run PostreSQL local server <br>
  You can use ***docker docker run -p 5433:5432 -e POSTGRES_PASSWORD=password postgres*** command for running a docker image
* Connect to the DB from whatever DB client you want

### №2

* Create tables: <br>


Libraries - id, name, address, customers and books.

Books - id, name, genre, author, customer who read the book at the time, isFree field that say if the book is available at the time, <br>
time when the book was taken, quantity of books

Customers - id, firstName, lastName, age, email, address,  <br>


Feel the tables with some information

You need to define:
- what types of relationships requires the database to keep the DB normalized
- what types of data should we use for each field
- what constraints should we apply to the tables <br><br>
  Links:
- PostgreSQL documentation: https://www.postgresql.org/docs/current/index.html
- Constraints: https://www.w3schools.com/sql/sql_constraints.aspn
-