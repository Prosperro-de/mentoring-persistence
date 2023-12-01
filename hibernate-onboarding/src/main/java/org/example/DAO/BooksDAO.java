package org.example.DAO;

import java.util.List;

import org.example.entities.Book;


public class BooksDAO extends BaseDAO<Book> {

    public List<Book> getAll() {
        return entityManager.createQuery("FROM Book", Book.class).getResultList();
    }


}
