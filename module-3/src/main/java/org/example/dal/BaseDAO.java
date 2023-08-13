package org.example.dal;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


import org.example.entities.Customer;
import org.example.entities.Id;
import org.example.entities.Table;

public abstract class BaseDAO<T> {
    private static final String INSERT_INTO = "INSERT INTO";

    private static final String SPACE = " ";

    private static final String VOWELS_LETTERS = "AEIOUaeiou";

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
        String insertQuery = createInsertQuery(newEntity).toString();
        System.out.println(insertQuery);
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newEntity;
    }

    public <U> StringBuilder createInsertQuery(U entity) throws IllegalAccessException {
        StringBuilder tableName = detectTableName(entity);
        StringBuilder columnsAndValues = detectColumnsAndValues(entity);

        return new StringBuilder().append(INSERT_INTO).append(SPACE).append(tableName)
            .append(SPACE).append(columnsAndValues);
    }

    /**
     * @param entity class
     * @return Table name,if table name is not specified
     * it will be generated based on entity name,
     * table name should be
     * entity name in plural
     */
    private <U> StringBuilder detectTableName(U entity) {
        String className = entity.getClass().getSimpleName();
        String tableName;

        if (isClassHaveTableAnnotation(entity)) {
          return new StringBuilder(entity.getClass().getAnnotation(Table.class).name());

        } else if (className.endsWith("s") || className.endsWith("sh") || className.endsWith("ch")
            || className.endsWith("x") || className.endsWith("z")) {
            tableName = className + "es";
        } else if (className.endsWith("y") && className.length() > 1 &&
            !isVowel(className.charAt(className.length() - 2))) {
            tableName = className.substring(0, className.length() - 1) + "ies";
        } else {
            tableName = className + "s";
        }

        return new StringBuilder(tableName);
    }

    private <U> boolean isClassHaveTableAnnotation(U entity) {
        if (entity.getClass().isAnnotationPresent(Table.class)) {
            Table tableAnnotation = entity.getClass().getAnnotation(Table.class);
            return !tableAnnotation.name().isEmpty();
        }
        return false;
    }

    private boolean isVowel(char c) {
        return VOWELS_LETTERS.indexOf(c) != -1;
    }


    private <U> StringBuilder detectColumnsAndValues(U entity) throws IllegalAccessException {
        List<Field> fields = getEntityFieldsWithOutId(entity);

        StringBuilder COLUMNS = new StringBuilder();
        StringBuilder VALUES = new StringBuilder();

        for (Field field : fields) {
            field.setAccessible(true);
            if (isFieldValuePresent(field, entity)) {
                COLUMNS.append(field.getName()).append(",").append(SPACE);
                VALUES.append("'").append(field.get(entity)).append("'").append(",").append(SPACE);
            }
        }
        removeLastTwoCharacters(COLUMNS);
        removeLastTwoCharacters(VALUES);

        return new StringBuilder().append("(").append(COLUMNS).append(")").append(SPACE)
            .append("VALUES").append(SPACE).append("(").append(VALUES).append(")");
    }

    private void removeLastTwoCharacters(StringBuilder stringBuilder) {
        if (stringBuilder.length() >= 2) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
    }

    private boolean isFieldValuePresent(Field field, Object entity) throws IllegalAccessException {
        field.setAccessible(true);

        Object fieldValue = field.get(entity);
        if (fieldValue == null) {
            return false;
        }
        if (field.getType().isPrimitive()) {
            return ((Number) fieldValue).longValue() != 0L;
        } else {
            return true;
        }
    }

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

    public <U> List<Field> getEntityFieldsWithOutId(U entity) {
        Class<?> clazz = entity.getClass();
        return Arrays.stream(clazz.getDeclaredFields()).filter(f -> !f.isAnnotationPresent(Id.class)).toList();
    }


    abstract List<T> findAll();

    public abstract Customer findById(int id);

    public abstract void save(Customer entity) throws IllegalAccessException;

    abstract void update(T entity);

    abstract void delete(T entity);
}
