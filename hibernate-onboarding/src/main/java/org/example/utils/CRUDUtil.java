package org.example.utils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.example.entities.Book;
import org.example.entities.Customer;
import org.example.entities.Library;

public class CRUDUtil {

    private static final String NAME = "name";

    private static final String AUTHOR = "author";

    private static final String GENRE = "genre";

    private static final String ADDRESS = "address";

    private static final String SURNAME = "surname";

    private static final String EMAIL = "email";

    private final ReflectionUtil reflection = new ReflectionUtil();

    private List<Field> fields;

    private final Scanner scanner = new Scanner(System.in);

    public <E> E createEntity(E entity) throws IllegalAccessException {
        fields = reflection.getEntityFields(entity);
        for (Field field : fields) {
            System.out.println("Enter value for field-> " + field.getName());
            Object value = reflection.castStringToFieldType(field, scanner.next());
            field.setAccessible(true);
            field.set(entity, value);
        }
        return entity;
    }


    /**
     *
     */


    public static void main(String[] args) {

        CRUDUtil c = new CRUDUtil();
        System.out.println(c.generateLibraryWithBooksAndCustomers(1,5,5));
    }

    public Library generateLibraryWithBooksAndCustomers(int identifier, int bookCount, int customerCount){
        Library library = Library.builder()
            .name(String.join("-", NAME, String.valueOf(identifier)))
            .address(String.join("-", ADDRESS, String.valueOf(identifier))).build();

        Set<Book> books = generateBatchOfBooks(bookCount, library);
        Set<Customer> customers = generateBatchOfCustomers(customerCount, library);

        library.setBooks(books);
        library.setCustomers(customers);

        return library;


    }



    private Set<Book> generateBatchOfBooks(int batchSize, Library library){
        Set<Book> books = new HashSet<>();
        for (int i=0;i<batchSize;i++){
            books.add(generateBook(i, library));
        }
        return books;
    }

    private Set<Customer> generateBatchOfCustomers(int batchSize, Library library){
        Set<Customer> customers = new HashSet<>();
        for (int i=0;i<batchSize;i++){
            customers.add(generateCustomer(i, library));
        }
        return customers;
    }


    private static Customer generateCustomer(int identifier, Library library) {
        String strIdentifier = String.valueOf(identifier);

        return Customer.builder()
            .firstName(String.join("-", NAME, strIdentifier))
            .lastName(String.join("-", SURNAME, strIdentifier))
            .library(library)
            .age(1)
            .email(String.join("-", EMAIL, strIdentifier))
            .address(String.join("-", ADDRESS, strIdentifier))
            .build();
    }

    private static Book generateBook(int identifier, Library library) {
        String strIdentifier = String.valueOf(identifier);

        return Book.builder()
            .name(String.join("-", NAME, strIdentifier))
            .author(String.join("-", AUTHOR, strIdentifier))
            .genre(String.join("-", GENRE, strIdentifier))
            .isFree(true)
            .quantity(1)
            .takenTime(new Date())
            .library(library)
            .price(50)
            .build();
    }

    private static Library generateLibrary(int identifier, Set<Book> books, Set<Customer> customers) {
        String strIdentifier = String.valueOf(identifier);

        return Library.builder()
            .name(String.join("-", NAME, strIdentifier))
            .address(String.join("-", ADDRESS, strIdentifier))
            .books(books)
            .customers(customers)
            .build();

    }
}
