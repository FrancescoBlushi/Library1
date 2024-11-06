import org.checkerframework.checker.units.qual.C;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionTest {

    private static ConnectionTest instance;
    private EntityManagerFactory factory;

    private ConnectionTest() {
        factory = Persistence.createEntityManagerFactory("LibraryTest");
    }

    public static synchronized ConnectionTest getInstance() {
        if (instance == null) {
            instance = new ConnectionTest();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
