package org.Library.DaoModels;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionDB {

    private static ConnectionDB instance;
    private EntityManagerFactory factory;

    private ConnectionDB() {
        factory = Persistence.createEntityManagerFactory("Library");
    }

    public static ConnectionDB getInstance() {
        if(instance == null) {
            instance = new ConnectionDB();
        }
        return instance;
    }

    public EntityManager getEntity() {
        return factory.createEntityManager();
    }
}
