package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.util.List;

public class BookedServiceHandler {
    public static BookedService createBookedService(int reservation_id, int service_id, int employee_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        BookedService bookedService;
        try {
            et = em.getTransaction();
            et.begin();
            Reservation reservation = em.find(Reservation.class, reservation_id);
            if (reservation == null) throw new IllegalArgumentException("No reservation found with id: " + reservation_id);
            ServiceType serviceType = em.find(ServiceType.class, service_id);
            if (serviceType == null) throw new IllegalArgumentException("No serviceType found with id: " + service_id);
            Employee employee = em.find(Employee.class, employee_id);
            if (employee == null) throw new IllegalArgumentException("No employee found with id: " + employee_id);
            bookedService = new BookedService(reservation,serviceType,employee);
            em.persist(bookedService);
            et.commit();
            System.out.println("Created BookedService: " + bookedService);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in BookedServiceHandler createBookedService: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return bookedService;
    }

    public static boolean deleteBookedService(int booked_service_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        BookedService bookedService = null;
        try {
            et = em.getTransaction();
            et.begin();
            bookedService = em.find(BookedService.class, booked_service_id);
            if (bookedService == null) throw new IllegalArgumentException("The BookedService with id : " + booked_service_id + " was not found in DB. Deletion canceled." );
            em.remove(bookedService);
            et.commit();
            System.out.println("Deleted " + bookedService);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in BookedServiceHandler deleteBookedService: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }
    public static BookedService updateBookedService(int booked_service_id, int reservation_id, int service_id, int employee_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        BookedService bookedService = null;
        try {
            et = em.getTransaction();
            et.begin();
            bookedService = em.find(BookedService.class, booked_service_id);
            if (bookedService == null) throw new IllegalArgumentException("The BookedService with id : " + service_id + " was not found in DB. Update canceled." );
            bookedService.setReservation(reservation_id);
            bookedService.setServiceType(service_id);
            bookedService.setEmployee(employee_id);
            em.persist(bookedService);
            et.commit();
            System.out.println("Updated " + bookedService);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in BookedServiceHandler updateBookedService: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return bookedService;
    }
    public static BookedService readBookedService(int booked_service_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        BookedService bookedService = null;
        try {
            et = em.getTransaction();
            et.begin();
            bookedService = em.find(BookedService.class, booked_service_id);
            if (bookedService == null) throw new IllegalArgumentException("The BookedService with id : " + booked_service_id + " was not found in DB." );
            return bookedService;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in BookedServiceHandler ReadBookedService: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static List<BookedService> readAllBookedService(){
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM bookedService x";
        List<BookedService> list = null;
        try {
            TypedQuery<BookedService> tq = em.createQuery(query, BookedService.class);
            list = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }
}
