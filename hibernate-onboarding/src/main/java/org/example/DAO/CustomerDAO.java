package org.example.DAO;

import java.util.List;

import org.example.entities.Book;
import org.example.entities.Customer;

public class CustomerDAO extends BaseDAO<Customer>{


    public List<Customer> getAll() {
        return entityManager.createQuery("FROM Customer", Customer.class).getResultList();
    }

}
