package org.example.dal;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


import org.example.exceptions.BaseDAOInsertException;
import org.example.utils.QueryBuilderUtil;
import org.example.utils.ReflectionUtil;

public abstract class BaseDAO<T> {
    private final QueryBuilderUtil queryBuilderUtil = new QueryBuilderUtil();

    private final ReflectionUtil reflectionUtil = new ReflectionUtil();
    public T getEntity(T entity, String sql) {
        List<String> fields = reflectionUtil.getEntityFieldsNames(entity);

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                for (String field : fields) {
                    Object value = resultSet.getObject(field);
                    reflectionUtil.setFieldValues(entity, field, value);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    public T insertEntity(T newEntity) throws IllegalAccessException {
        String insertQuery = queryBuilderUtil.buildInsertQuery(newEntity);
        List<Field> fields = queryBuilderUtil.getNotNUllFieldsNamesWithOutId(newEntity);


        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            for(int i = 0; i<fields.size(); i++){
                fields.get(i).setAccessible(true);
                preparedStatement.setObject(i+1,fields.get(i).get(newEntity));
            }

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new BaseDAOInsertException("Exception while INSERT operation");
        }
        return newEntity;
    }


    abstract List<T> findAll();

    public abstract T findById(int id);

    public abstract void save(T entity) throws IllegalAccessException;

    abstract void update(T entity);

    abstract void delete(T entity);
}
