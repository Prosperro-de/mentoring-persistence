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

public abstract class BaseDAO<T> {
    private final QueryBuilderUtil queryBuilderUtil = new QueryBuilderUtil();


    public T getEntity(T entity, String sql) {
        List<String> fields = getEntityFieldsNames(entity);

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                for (String field : fields) {
                    Object value = resultSet.getObject(field);
                    setFieldValues(entity, field, value);
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
        System.out.println(insertQuery);


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

    /**
     *  entity class
     * @return Table name,if table name is not specified
     * it will be generated based on entity name,
     * table name should be
     * entity name in plural
     */

    private static <T> void setFieldValues(T entity, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = entity.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(entity, value);
    }

    private <U> List<String> getEntityFieldsNames(U entity) {
        Class<?> clazz = entity.getClass();
        return Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).toList();
    }

    abstract List<T> findAll();

    public abstract T findById(int id);

    public abstract void save(T entity) throws IllegalAccessException;

    abstract void update(T entity);

    abstract void delete(T entity);
}
