package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Job;
import at.fhburgenland.entities.MaintenanceType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MaintenanceTypeHandler {
    public static MaintenanceType createMaintenanceType(int m_type_id, String maintenance_type) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        MaintenanceType maintenanceType;
        try {
            et = em.getTransaction();
            et.begin();

            maintenanceType = new MaintenanceType(m_type_id, maintenance_type);
            em.persist(maintenanceType);
            et.commit();
            System.out.println("Created MaintenanceType: " + maintenanceType);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in MaintenanceTypeHandler createMaintenanceType: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return maintenanceType;
    }

    public static boolean deleteMaintenanceType(int m_type_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        MaintenanceType maintenanceType = null;
        try {
            et = em.getTransaction();
            et.begin();
            maintenanceType = em.find(MaintenanceType.class, m_type_id);
            if (maintenanceType == null) throw new IllegalArgumentException("The MaintenanceType with id : " + m_type_id + " was not found in DB. Deletion canceled." );
            em.remove(maintenanceType);
            et.commit();
            System.out.println("Deleted " + maintenanceType);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in MaintenanceTypeHandler DeleteMaintenanceType: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    public static MaintenanceType updateMaintenanceType(int m_type_id, String maintenance_type) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        MaintenanceType maintenanceType = null;
        try {
            et = em.getTransaction();
            et.begin();
            maintenanceType = em.find(MaintenanceType.class, m_type_id);
            if (maintenanceType == null) throw new IllegalArgumentException("The maintenanceType with id : " + m_type_id + " was not found in DB. Update canceled." );
            maintenanceType.setMaintenance_type(maintenance_type);

            em.persist(maintenanceType);
            et.commit();
            System.out.println("Updated " + maintenanceType);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in MaintenanceHandler UpdateMaintenance: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return maintenanceType;
    }

    public static MaintenanceType readMaintenanceType(int m_type_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
       MaintenanceType maintenanceType = null;
        try {
            et = em.getTransaction();
            et.begin();
            maintenanceType = em.find(MaintenanceType.class, m_type_id);
            if (maintenanceType == null)
                throw new IllegalArgumentException("The MaintenanceType with id : " + m_type_id + " was not found in DB.");
            return maintenanceType;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in MaintenanceTypeHandler readMaintenancetype: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static List<MaintenanceType> readAllMaintenanceTypes(){
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM event x";
        List<MaintenanceType> list = null;
        try {
            TypedQuery<MaintenanceType> tq = em.createQuery(query, MaintenanceType.class);
            list = tq.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }

        return list;

    }
}
