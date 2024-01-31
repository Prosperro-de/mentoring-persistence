package org.example;

import org.example.dal.CustomerDAO;
import org.example.entities.Customer;
import org.example.exceptions.BaseDaoException;
import org.example.mappers.UpdateMapper;
import org.example.utils.QueryBuilderUtil;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        CustomerDAO customerDAO = new CustomerDAO();
        QueryBuilderUtil util = new QueryBuilderUtil();
        UpdateMapper<Customer> customerUpdateMapper = new UpdateMapper<>();
        Customer customer = Customer.builder()
            .first_name("Andrii")
            .last_name("Urban")
            .age(15)
            .email("email@gmail")
            .address("Legnicka555")
            .build();

        Customer customer1 = Customer.builder()
            .first_name("Roman")
            .last_name("Urban")
            .age(45)
            .email("email@gmail")
            .address("Legnicka555")
            .build();
        //System.out.println(customerDAO.findById(5));

        String event = "event";
        String topic = "topic";
        String e = "e";


//        try {
//            customerDAO.update(customer);
//        } catch (BaseDaoException e) {
//            throw new BaseDaoException("error", e);
//        }
        System.out.println(util.buildInsertQuery(customer));

        System.out.println(customerDAO.insertEntity(customer));
    }


}