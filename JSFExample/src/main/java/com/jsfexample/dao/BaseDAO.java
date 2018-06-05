package com.jsfexample.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public interface BaseDAO {

    String PERSISTENCE_UNIT_NAME = "JSFJPACrud";
    EntityManager entityMgrObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    EntityTransaction transactionObj = entityMgrObj.getTransaction();
}
