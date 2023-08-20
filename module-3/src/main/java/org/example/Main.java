package org.example;

import org.example.dal.CustomerDAO;
import org.example.entities.Customer;
import org.example.exceptions.BaseDaoException;
import org.example.utils.QueryBuilderUtil;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        CustomerDAO customerDAO = new CustomerDAO();
        QueryBuilderUtil util = new QueryBuilderUtil();
        Customer customer = Customer.builder()
            .first_name("Andrii")
            .last_name("Urban")
            .age(15)
            .email("email@gmail")
            .address("Legnicka555")
            .build();
        //System.out.println(customerDAO.findById(5));

//        try {
//            customerDAO.update(customer);
//        } catch (BaseDaoException e) {
//            throw new BaseDaoException("error", e);
//        }
        System.out.println(util.buildInsertQuery(customer));

        System.out.println(customerDAO.insertEntity(customer));
    }


}