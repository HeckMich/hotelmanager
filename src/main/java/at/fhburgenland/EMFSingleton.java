package at.fhburgenland;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * A EntityManagerFactory Singleton which always returns a new EntityManager from a single EntityManagerFactory instance.
 */
public class EMFSingleton {

    private static volatile EntityManagerFactory EMF;

    private EMFSingleton() {
    }

    /**
     * Get a new EntityManager from a single central createEntityManagerFactory
     * @return new EntityManager
     */
    public static EntityManager getEntityManager() {
        if (EMF == null) {
            synchronized (EMFSingleton.class) {
                if (EMF == null) {
                    EMF = Persistence.createEntityManagerFactory("hotelmanagement");
                }
            }
        }
        return EMF.createEntityManager();
    }

    /**
     * Close EntityManagerFactory
     * Should be called before shutdown
     */
    public static void close() {
        if (EMF != null) {
            EMF.close();
        }
    }

}
