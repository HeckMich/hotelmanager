package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Event;
import at.fhburgenland.entities.Plz;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class PlzHandler {
    public static Plz createPlz(String plz_string, String city) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Plz plz;
        try {
            et = em.getTransaction();
            et.begin();

            plz = new Plz(plz_string, city);
            em.persist(plz);
            et.commit();
            System.out.println("Created Plz: " + plz);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in PlzHandler createPlz: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return plz;
    }

    public static boolean deletePlz(int plz_string) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Plz plz = null;
        try {
            et = em.getTransaction();
            et.begin();
            plz = em.find(Plz.class, plz_string);
            if (plz == null)
                throw new IllegalArgumentException("The Plz with id : " + plz_string + " was not found in DB. Deletion canceled.");
            em.remove(plz);
            et.commit();
            System.out.println("Deleted " + plz);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in PlzHandler DeletePlz: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    public static Plz updatePlz(String plz_string, String city) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Plz plz = null;
        try {
            et = em.getTransaction();
            et.begin();
            plz = em.find(Plz.class, plz_string);
            if (plz == null)
                throw new IllegalArgumentException("The Plz with id : " + plz_string + " was not found in DB. Update canceled.");
            plz.setCity(city);
            em.persist(plz);
            et.commit();
            System.out.println("Updated " + plz);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in PlzHandler UpdatePlz: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return plz;
    }

    public static Plz readPlz(int plz_string) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Plz plz = null;
        try {
            et = em.getTransaction();
            et.begin();
            plz = em.find(Plz.class, plz_string);
            if (plz == null)
                throw new IllegalArgumentException("The Plz with id : " + plz_string + " was not found in DB.");
            return plz;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in PlzHandler EventPlz: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static List<Plz> readAllPlzs() {
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM plz x";
        List<Plz> list = null;
        try {
            TypedQuery<Plz> tq = em.createQuery(query, Plz.class);
            list = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }

}
