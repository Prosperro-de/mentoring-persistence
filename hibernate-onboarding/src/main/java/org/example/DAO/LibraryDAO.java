package org.example.DAO;

import java.util.List;

import org.example.entities.Library;

public class LibraryDAO extends BaseDAO<Library> {

    public List<Library> getAll() {
        return entityManager.createQuery("FROM Library ", Library.class).getResultList();
    }

}
