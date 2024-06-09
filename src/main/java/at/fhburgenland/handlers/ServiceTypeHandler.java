package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Guest;
import at.fhburgenland.entities.Plz;
import at.fhburgenland.entities.Room;
import at.fhburgenland.entities.ServiceType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.util.List;

public class ServiceTypeHandler {
    public static ServiceType createServiceType(String name, BigDecimal cost) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        ServiceType serviceType;
        try {
            et = em.getTransaction();
            et.begin();
            serviceType = new ServiceType(name, cost);
            em.persist(serviceType);
            et.commit();
            System.out.println("Created ServiceType: " + serviceType);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in ServiceTypeHandler createServiceType: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return serviceType;
    }

    public static boolean deleteServiceType(int service_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        ServiceType serviceType = null;
        try {
            et = em.getTransaction();
            et.begin();
            serviceType = em.find(ServiceType.class, service_id);
            if (serviceType == null) throw new IllegalArgumentException("The serviceType with id : " + service_id + " was not found in DB. Deletion canceled." );
            em.remove(serviceType);
            et.commit();
            System.out.println("Deleted " + serviceType);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in ServiceTypeHandler deleteServiceType: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }
    public static ServiceType updateServiceType(int service_id, String name, BigDecimal cost) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        ServiceType serviceType = null;
        try {
            et = em.getTransaction();
            et.begin();
            serviceType = em.find(ServiceType.class, service_id);
            if (serviceType == null) throw new IllegalArgumentException("The serviceType with id : " + service_id + " was not found in DB. Update canceled." );
            serviceType.setCost(cost);
            serviceType.setName(name);
            em.persist(serviceType);
            et.commit();
            System.out.println("Updated " + serviceType);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in ServiceTypeHandler updateServiceType: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return serviceType;
    }
    public static ServiceType readServiceType(int service_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        ServiceType serviceType = null;
        try {
            et = em.getTransaction();
            et.begin();
            serviceType = em.find(ServiceType.class, service_id);
            if (serviceType == null) throw new IllegalArgumentException("The guest with id : " + service_id + " was not found in DB." );
            return serviceType;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in ServiceTypeHandler ReadServiceType: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static void readAllServiceTypes(){
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM service_type x";
        List<ServiceType> list = null;
        try {
            TypedQuery<ServiceType> tq = em.createQuery(query, ServiceType.class);
            list = tq.getResultList();
            list.forEach(x -> System.out.println(
                    x.toString()
            ));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
