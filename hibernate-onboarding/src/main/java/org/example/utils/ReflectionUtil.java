package org.example.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.example.exceptions.ReflectionException;

public class ReflectionUtil {


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
                    throw new ReflectionException("Exception while getting entity value");
                }
            }
        }
        throw new ReflectionException("Any - "+
            annotationClass.getSimpleName()+
            " annotation was found inside entity");
    }

    public <U> String getClassName(U entity) {
        return entity.getClass().getSimpleName();
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
       return null;
    }

    public <U> List<Field> getNotNullFields(U entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        return Arrays.stream(fields).filter(Objects::nonNull).toList();
    }


}
