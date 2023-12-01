package org.example.DAO;

import org.hibernate.Session;
@FunctionalInterface
interface TransactionOperation {
    void execute(Session session);

}