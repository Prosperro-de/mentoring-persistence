CREATE TABLE IF NOT EXISTS Books (
                       id BIGINT PRIMARY KEY,
                       name VARCHAR(255),
                       genre VARCHAR(255),
                       author VARCHAR(255),
                       is_free BOOLEAN,
                       customer_id BIGINT,
                       taken_time TIMESTAMP,
                       quantity INT,
                       library_id BIGINT,
                       price INT,
                       FOREIGN KEY (customer_id) REFERENCES Customers(id),
                       FOREIGN KEY (library_id) REFERENCES Libraries(id)
);

CREATE TABLE Customers (
                           id BIGINT PRIMARY KEY,
                           first_name VARCHAR(255),
                           last_name VARCHAR(255),
                           library_id BIGINT,
                           age INT,
                           email VARCHAR(255),
                           address VARCHAR(255),
                           FOREIGN KEY (library_id) REFERENCES Libraries(id)
);

CREATE TABLE Libraries (
                           id BIGINT PRIMARY KEY,
                           name VARCHAR(255),
                           address VARCHAR(255)
);



ALTER TABLE books
    ADD CONSTRAINT fk_customer_id FOREIGN KEY(customer_id) REFERENCES customers(id);

ALTER TABLE books
    ADD CONSTRAINT fk_library_id FOREIGN KEY(library_id) REFERENCES libraries(id);

ALTER TABLE Customers
   ADD CONSTRAINT fk_library_id FOREIGN KEY(library_id) REFERENCES libraries(id);
