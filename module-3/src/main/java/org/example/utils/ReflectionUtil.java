package org.example.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.example.entities.Id;
import org.example.entities.Table;
import org.example.exceptions.ReflectionException;

public class ReflectionUtil {

    public <U> boolean isClassHasNotEmptyTableAnnotation(U entity) {
        if (entity.getClass().isAnnotationPresent(Table.class)) {
            Table tableAnnotation = entity.getClass().getAnnotation(Table.class);
            return !tableAnnotation.name().isEmpty();
        }
        return false;
    }

    public <U, T extends Annotation> boolean isClassHasAnnotation(U entity, Class<T> annotationClass){
        return entity.getClass().isAnnotationPresent(annotationClass);
    }

    public <U, T extends Annotation> Object getAnnotatedFieldValue(U entity, Class<T> annotationClass) {
        Field[] fields = entity.getClass().getDeclaredFields();

        for(Field field: fields){
            if(field.isAnnotationPresent(annotationClass)){

                try {
                    field.setAccessible(true);
                    return field.get(entity);
                }
                catch (IllegalAccessException e){
                    e.printStackTrace();
                    throw new ReflectionException("Exception while getting id value");
                }
            }
        }
        return null;
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

    public  <U> List<String> getEntityFieldsNames(U entity) {
        Class<?> clazz = entity.getClass();
        return Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).toList();
    }

    public  <T> void setFieldValues(T entity, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = entity.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(entity, value);
    }


    public <U> Long getIdField(U entity){
        if(isClassHasAnnotation(entity, Id.class)){
            return (Long) getAnnotatedFieldValue(entity, Id.class);
        }
        else {
            throw new ReflectionException("No id annotation was found");
        }
    }

    public <U> List<Field> getNotNullFields(U entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        return Arrays.stream(fields).filter(Objects::nonNull).toList();
    }


}
