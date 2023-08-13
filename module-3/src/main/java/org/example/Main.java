package org.example;

import org.example.dal.CustomerDAO;
import org.example.entities.Customer;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = Customer.builder()
            .first_name("Andrii")
            .last_name("Urban")
            .age(15)
            .email("email@gmail")
            .address("Legnicka555")
            .build();
        System.out.println(customerDAO.findById(5));

        customerDAO.save(customer);


    }
}