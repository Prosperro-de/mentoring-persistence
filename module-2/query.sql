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
