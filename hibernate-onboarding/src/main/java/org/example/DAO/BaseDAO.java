package org.example.DAO;

import org.example.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.example.utils.ReflectionUtil;



public abstract class BaseDAO<E>  {
    protected static final  SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
    private static final ReflectionUtil reflectionUtil = new ReflectionUtil();
    public void getEntityByID(E entity){}
}
