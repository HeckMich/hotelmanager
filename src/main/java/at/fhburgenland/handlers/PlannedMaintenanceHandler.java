package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Guest;
import at.fhburgenland.entities.PlannedMaintenance;
import at.fhburgenland.entities.Plz;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class PlannedMaintenanceHandler {
    public static PlannedMaintenance createPlannedMaintenance(int maint_id, int m_type_id, Date start_date, Date end_date, int room_nr, int employee_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        PlannedMaintenance plannedMaintenance;
        try {
            et = em.getTransaction();
            et.begin();

            plannedMaintenance = new PlannedMaintenance(maint_id, m_type_id, start_date, end_date, room_nr, employee_id);
            em.persist(plannedMaintenance);
            et.commit();
            System.out.println("Created Guest: " + plannedMaintenance);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in PlannedMaintenanceHandler CreatePlannedMaintenance: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return plannedMaintenance;
    }

    public static boolean deletePlannedMaintenance(int maint_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        PlannedMaintenance plannedMaintenance = null;
        try {
            et = em.getTransaction();
            et.begin();
            plannedMaintenance = em.find(PlannedMaintenance.class, maint_id);
            if (plannedMaintenance == null) throw new IllegalArgumentException("The PlannedMaintenance with id : " + maint_id + " was not found in DB. Deletion canceled." );
            em.remove(plannedMaintenance);
            et.commit();
            System.out.println("Deleted " + plannedMaintenance);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in PlannedMaintenanceHandler DeletePlannedMaintenance: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }
    public static PlannedMaintenance updatePlannedMaintenance(int maint_id, int m_type_id, Date start_date, Date end_date, int room_nr, int employee_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        PlannedMaintenance plannedMaintenance = null;
        try {
            et = em.getTransaction();
            et.begin();
            plannedMaintenance = em.find(PlannedMaintenance.class, maint_id);
            if (plannedMaintenance == null) throw new IllegalArgumentException("The PlannedMaintenance with id : " + maint_id + " was not found in DB. Update canceled." );
            plannedMaintenance.setM_type_id(m_type_id);
            plannedMaintenance.setStart_date(start_date);
            plannedMaintenance.setEnd_date(end_date);
            plannedMaintenance.setRoom_nr(room_nr);
            plannedMaintenance.setEmployee_id(employee_id);

            em.persist(plannedMaintenance);
            et.commit();
            System.out.println("Updated " + plannedMaintenance);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in PlannedMaintenanceHandler UpdatePlannedMaintenance: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return plannedMaintenance;
    }
    public static PlannedMaintenance readPlannedMaintenance(int maint_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        PlannedMaintenance plannedMaintenance = null;
        try {
            et = em.getTransaction();
            et.begin();
            plannedMaintenance = em.find(PlannedMaintenance.class, maint_id);
            if (plannedMaintenance == null) throw new IllegalArgumentException("The PlannedMaintenance with id : " + maint_id + " was not found in DB." );
            return plannedMaintenance;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in PlannedMaintenanceHandler ReadPlannedMaintenance: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static List<PlannedMaintenance> readAllPlannedMaintenances(){
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM guest x";
        List<PlannedMaintenance> list = null;
        try {
            TypedQuery<PlannedMaintenance> tq = em.createQuery(query, PlannedMaintenance.class);
            list = tq.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }
}
