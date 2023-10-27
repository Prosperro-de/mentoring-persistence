package org.example.mappers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.example.entities.Book;
import org.example.entities.Customer;
import org.example.entities.Entity;
import org.example.entities.Library;
import org.example.utils.ReflectionUtil;

import static org.example.mappers.UpdateMapper.KnownEntityClasses.CUSTOMER_ENTITY;

public class UpdateMapper<E> {

    ReflectionUtil reflectionUtil = new ReflectionUtil();

    public boolean updateEntity(E newEntity, E oldEntity) {
//        List<Field> newEntityFields = reflectionUtil.getNotNullFields(newEntity);
//
//        List<Field> oldEntityFields = reflectionUtil.getNotNullFields(oldEntity);
//        System.out.println(newEntityFields);
//        System.out.println(oldEntityFields.get(0));
//        return newEntityFields.get(0).equals(oldEntityFields.get(0));
        //String className = newEntity.getClass();
       // System.out.println(className);
        System.out.println(Customer.class.getName());
       // System.out.println(Arrays.toString(KnownEntityClasses.valueOf()));

        Entity c = EntityFactory.createEntity(KnownEntityClasses.BOOK_ENTITY);
        System.out.println(c);
        return false;
    }

    private boolean ifEntityClassIsKnown(E entity) {
        return isPresentInEnum(entity.getClass());
    }

    private boolean isPresentInEnum(Class<?> className) {
        for (KnownEntityClasses knownClass : KnownEntityClasses.values()) {
            if (knownClass.getEntityClass().getName().equals(className.getName())) {
                return true;
            }
        }
        return false;
    }

    enum KnownEntityClasses {
        CUSTOMER_ENTITY(Customer.class),
        LIBRARY_ENTITY(Library.class),
        BOOK_ENTITY(Book.class);
        private final Class<?> entityClass;

        KnownEntityClasses(Class<?> entityClass) {
            this.entityClass = entityClass;
        }

        public Class<?> getEntityClass() {
            return entityClass;
        }

    }

    //    public static class EntityFactory {
//
//        public static Entity createEntity(String entityClassName) {
//            return switch (KnownEntityClasses.valueOf(entityClassName)) {
//                case CUSTOMER_ENTITY -> new Customer();
//                case LIBRARY_ENTITY -> new Library();
//                case BOOK_ENTITY -> new Book();
//                default -> throw new IllegalArgumentException("Unknown entity name: " + entityClassName);
//            };
//        }
//    }
    public class EntityFactory {

        public static Entity createEntity(KnownEntityClasses entityClass) {
            try {
                return (Entity) entityClass.getEntityClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalArgumentException("Unable to create an instance of: " + entityClass.name(), e);
            }
        }
    }
}
