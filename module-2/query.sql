link to pastebin-> https://pastebin.com/uSQnt7EX


##########################

ALTER TABLE books
ADD COLUMN customer_id BIGINT,
ADD CONSTRAINT fk_customer_id FOREIGN KEY(customer_id) REFERENCES customers(id)

##########################

SELECT * FROM books WHERE library_id = 2

##########################

ALTER TABLE books
ADD COLUMN price INT,
ADD CONSTRAINT positive_price CHECK (price >=0)

##########################

SELECT COUNT(*) AS total_num_of_books FROM books WHERE library_id=2

##########################

INSERT INTO books (library_id,name,genre,author,is_free) VALUES
      (2,'name2','genre','author',true),
	  (2,'name3','genre','author',true),
	  (3,'name4','genre','author',true),
	  (5,'name5','genre','author',true),
	  (6,'name6','genre','author',true),
	  (7,'name7','genre','author',true),
	  (8,'name8','genre','author',true),
	  (9,'name9','genre','author',true),
      (10,'name10','genre','author',true),
	  (11,'name11','genre','author',true),
	  (12,'name12','genre','author',true),
	  (13,'name13','genre','author',true),
	  (14,'name14','genre','author',true),
	  (15,'name15','genre','author',true)
    
##########################

INSERT INTO customers (first_name,last_name,age,email,address) VALUES
      ('first_name1','first_name1', 14 , 'email@awes','address1'),
	   ('first_name2','first_name2', 12 , 'email@da','address2'),
	   ('first_name3','first_name3', 13 , 'email@ascad','address3'),
	   ('first_name4','first_name4', 15 , 'email@asvzd','address4'),
	   ('first_name5','first_name5', 14 , 'email@qwdsasc','address5'),
	   ('first_name6','first_name6', 15 , 'email@asced','address6');

##########################
BEGIN;

UPDATE customers
SET age = 30
WHERE id = 14;

COMMIT;
##########################

BEGIN;

UPDATE customers
SET age = 20
WHERE id = 14;

ROLLBACK;

SELECT * FROM customers WHERE id=14 

##########################


BEGIN;

UPDATE customers SET age = 88 WHERE id = 14

##########################

BEGIN;

UPDATE customers SET age = 88 WHERE id = 14

##########################

BEGIN;

SELECT * FROM customers WHERE id = 14 FOR UPDATE

##########################

BEGIN;

UPDATE customers SET age = 88  WHERE id = 14

##########################
UPDATE books SET customer_id=13

UPDATE books SET customer_id=14 where id>6

##########################

SELECT books.* FROM books
JOIN customers ON books.customer_id = customers.id WHERE customers.id = 13
##########################
ALTER TABLE books
ADD column books_quantity varchar(255)
##########################
UPDATE books SET books_quantity=30
##########################
ALTER TABLE books
ALTER COLUMN books_quantity TYPE integer USING books_quantity::integer
##########################
SELECT libraries.id AS library_id, SUM(books.books_quantity) AS book_count FROM libraries
JOIN books ON libraries.id = books.library_id
GROUP BY libraries.id


(to cover all libs)
SELECT libraries.id AS library_id, SUM(books.books_quantity) AS book_count FROM libraries
LEFT JOIN books ON libraries.id = books.library_id
GROUP BY libraries.id

##########################
UPDATE books SET price = 110 WHERE id= 10
##########################
SELECT customers.id, customers.first_name, customers.last_name
FROM customers
JOIN ( 
	SELECT customer_id, MAX(price) as max_price
	FROM books
    GROUP BY customer_id
    ) as  subquery
ON customers.id= subquery.customer_id
JOIN books on customers.id = books.customer_id and  books.price =  subquery.max_price





