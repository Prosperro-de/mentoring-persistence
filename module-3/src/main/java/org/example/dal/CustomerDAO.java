package org.example.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.entities.Customer;
import org.example.exceptions.BaseDaoException;

public class CustomerDAO extends BaseDAO<Customer>{

    private final static String SELECT_FROM_CUSTOMERS_BY_ID = "SELECT * FROM Customers WHERE id=?";

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Customers");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Customer customer = Customer.builder()
                    .id((long) resultSet.getObject("id"))
                    .first_name(resultSet.getString("first_name"))
                    .last_name(resultSet.getString("last_name"))
                    .age(resultSet.getInt("age"))
                    .email(resultSet.getString("email"))
                    .address(resultSet.getString("address"))
                    .build();
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public Customer findById(int id) {
        Customer customer = new Customer();
        String query = SELECT_FROM_CUSTOMERS_BY_ID.replace("?", String.valueOf(id));
        System.out.println(query);
        return getEntity(customer,query);
    }


    @Override
    public void save(Customer entity) throws IllegalAccessException {
        insertEntity(entity);
    }

    @Override
    public void update(Customer entity) {
        throw new BaseDaoException("Method not used");
    }

    @Override
    public void delete(Customer entity) {
         super.deleteEntity(entity);
    }
}
