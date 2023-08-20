package org.example.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.example.entities.Id;
import org.example.entities.Table;

public class ReflectionUtil {

    public <U> boolean isClassHasTableAnnotation(U entity) {
        if (entity.getClass().isAnnotationPresent(Table.class)) {
            Table tableAnnotation = entity.getClass().getAnnotation(Table.class);
            return !tableAnnotation.name().isEmpty();
        }
        return false;
    }

    public <U> String getClassName(U entity) {
        return entity.getClass().getSimpleName();
    }

    public <U> List<Field> getNotNUllFieldsNamesWithOutId(U entity) {
        Class<?> clazz = entity.getClass();
        return Arrays.stream(clazz.getDeclaredFields())
            .filter(Objects::nonNull)
            .filter(f -> !f.isAnnotationPresent(Id.class))
            .toList();
    }


    public <U> List<Field> getNotNullFields(U entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        return Arrays.stream(fields).filter(Objects::nonNull).toList();
    }


}
