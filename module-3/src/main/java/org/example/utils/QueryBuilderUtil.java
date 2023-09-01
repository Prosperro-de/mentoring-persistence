package org.example.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.example.entities.Table;

public class QueryBuilderUtil {

    private static final String INSERT_INTO = "INSERT INTO";

    private static final String DELETE = "DELETE";

    private static final String FROM = "FROM";

    private static final String WHERE_ID_EQUALS = "WHERE id =";

    private static final String VOWELS_LETTERS = "AEIOUaeiou";

    private static final String SPACE = " ";

    private static final String OPEN_BRACKET = "(";

    private static final String CLOSED_BRACKET = ")";

    public static final String QUESTION_MARK = "?";

    private final ReflectionUtil reflectionUtil = new ReflectionUtil();

    public <E> List<Field> getNotNUllFieldsNamesWithOutId(E entity){
        return reflectionUtil.getNotNUllFieldsNamesWithOutId(entity);
    }

    public <E> String buildInsertQuery(E entity){
        String tableName = detectTableName(entity);
        String columnsAndValues = detectColumnsAndValues(entity);


        return new StringBuilder().append(INSERT_INTO).append(SPACE)
            .append(tableName).append(SPACE)
            .append(columnsAndValues)
            .toString();
    }

    /**
     *  entity class
     * @return Table name,if table name is not specified
     * it will be generated based on entity name,
     * table name should be
     * entity name in plural
     */
    private <U> String detectTableName(U entity) {
        String className = reflectionUtil.getClassName(entity);
        String tableName;

        if (reflectionUtil.isClassHasNotEmptyTableAnnotation(entity)) {
            return entity.getClass().getAnnotation(Table.class).name();

        } else if (className.endsWith("s") || className.endsWith("sh") || className.endsWith("ch")
            || className.endsWith("x") || className.endsWith("z")) {
            tableName = className + "es";

        } else if (className.endsWith("y") && className.length() > 1 &&
            !isVowel(className.charAt(className.length() - 2))) {
            tableName = className.substring(0, className.length() - 1) + "ies";

        } else {
            tableName = className + "s";
        }

        return tableName;
    }


    private boolean isVowel(char c) {
        return VOWELS_LETTERS.indexOf(c) != -1;
    }


    public  <U> String detectColumnsAndValues(U entity){
        List<Field> fields = reflectionUtil.getNotNUllFieldsNamesWithOutId(entity);

        StringBuilder columns = new StringBuilder();
        StringBuilder questionMarks = new StringBuilder();

        List<String> fieldNames = new ArrayList<>();
        List<String> placeholders = new ArrayList<>();

        for (Field field : fields) {
            fieldNames.add(field.getName());
            placeholders.add(QUESTION_MARK);
        }

        columns.append(String.join(", ", fieldNames)).append(SPACE);
        questionMarks.append(String.join(", ", placeholders)).append(SPACE);

        return new StringBuilder()
            .append(OPEN_BRACKET).append(columns).append(CLOSED_BRACKET)
            .append(SPACE)
            .append("VALUES")
            .append(SPACE)
            .append(OPEN_BRACKET).append(questionMarks).append(CLOSED_BRACKET)
            .toString();
    }

    public <E> String buildDeleteQuery(E entity){
        String tableName = detectTableName(entity);

        return new StringBuilder()
            .append(DELETE).append(SPACE).append(FROM).append(SPACE)
            .append(tableName).append(SPACE)
            .append(WHERE_ID_EQUALS).append(SPACE)
            .append(QUESTION_MARK)
            .toString();
    }
}
