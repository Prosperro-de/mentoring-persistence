package org.example.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Id;

import org.example.exceptions.ReflectionException;

public class ReflectionUtil {


    public <U, T extends Annotation> boolean isClassHasAnnotation(U entity, Class<T> annotationClass) {
        return entity.getClass().isAnnotationPresent(annotationClass);
    }

    public <U, T extends Annotation> Object getAnnotatedFieldValue(U entity, Class<T> annotationClass) {
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(annotationClass)) {

                try {
                    field.setAccessible(true);
                    return field.get(entity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new ReflectionException("Exception while getting entity value");
                }
            }
        }
        throw new ReflectionException("Any - " +
            annotationClass.getSimpleName() +
            " annotation was found inside entity");
    }

    public <U> String getClassName(U entity) {
        return entity.getClass().getSimpleName();
    }


    public <U> List<Field> getEntityFields(U entity) {
        Class<?> clazz = entity.getClass();
        return Arrays.stream(clazz.getDeclaredFields()).toList();
    }

    public <T> void setFieldValues(T entity, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = entity.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(entity, value);
    }

    public <U> Long getIdField(U entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(Id.class) && f.getType() == Long.class) {
                f.setAccessible(true);
                return (Long) getNotNullField(f, entity);
            }
        }
        throw new ReflectionException("Id field not found");
    }


    public <U> List<Field> getNotNullFields(U entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        return Arrays.stream(fields).filter(Objects::nonNull).toList();
    }

    public <E> E copyValues(E entityFromDb, E entityFromApp) {
        List<Field> list = getEntityFields(entityFromDb);
        for (Field field : list) {

            field.setAccessible(true);
            Object value = getField(field, entityFromApp);
            setField(field, entityFromDb, value);

        }
        return entityFromDb;
    }

    public <T> T castStringToFieldType(Field field, String stringValue) {
        Class<T> targetType = (Class<T>) getFieldType(field);
        return convertStringToType(stringValue, targetType);
    }

    private static <T> T convertStringToType(String stringValue, Class<T> targetType) {
        if (targetType.equals(Integer.class)) {
            return targetType.cast(Integer.valueOf(stringValue));
        } else if (targetType.equals(Double.class)) {
            return targetType.cast(Double.valueOf(stringValue));
        } else if (targetType.equals(Long.class)) {
            return targetType.cast(Long.valueOf(stringValue));
        } else if (targetType.equals(String.class)) {
            return targetType.cast(stringValue);
        } else if (targetType.equals(Date.class)) {
            return targetType.cast(parseDate(stringValue));
        } else if (targetType.equals(Boolean.class)) {
            return targetType.cast(Boolean.valueOf(stringValue));
        }

        System.out.println(String.format("Type %s not supported", targetType));
        return null;
    }

    private static Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString, e);
        }
    }

    private Type getFieldType(Field field) {
        return field.getType();
    }

    private Object getField(Field field, Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new ReflectionException("Can not get field from obj: " + obj);
        }
    }


    private void setField(Field field, Object obj, Object value) {
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(
                String.format("Can not set value %s, to object %s", value, obj)
            );
        }
    }

    private Object getNotNullField(Field field, Object obj) {
        try {
            Object fieldValue = field.get(obj);
            checkIfValueIsNotNull(fieldValue);
            return fieldValue;
        } catch (IllegalAccessException e) {
            throw new ReflectionException("Id field is not maked with annotation or does not exist at all");
        }
    }

    private void checkIfValueIsNotNull(Object o) {
        if (o == null) {
            throw new RuntimeException("Null value in return");
        }
    }
}
