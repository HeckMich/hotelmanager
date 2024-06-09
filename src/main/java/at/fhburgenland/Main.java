package at.fhburgenland;

import at.fhburgenland.entities.*;
import at.fhburgenland.handlers.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class Main {

    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("hotelmanagement");

    public static void main(String[] args) {
//        System.out.println("Test");
////        RoomHandler.createRoom(43,9, BigDecimal.valueOf(999.10));
//        RoomHandler.deleteRoom(43);
////        ReservationHandler.createReservation(43,1, Date.valueOf("2024-01-01"),Date.valueOf("2024-01-02"));
//        ReservationHandler.deleteReservation(11);
//        readAllRooms();
//        readAllEvents();
//        readAllReservations();
//        GuestHandler.readAllGuest();
//        readAllPlz();
//        readAllInvoices();
//        readAllBookedServices();
//        readAllServiceTypes();
//        readAllJobs();
//        readAllEmployees();
//        readAllPlannedMaintenances();
//        readAllMaintenanceTypes();

        Guest x = GuestHandler.createGuest("6020","test","test",1,"test");
        System.out.println(GuestHandler.readGuest(x.getGuest_id()));
        GuestHandler.updateGuest(x.getGuest_id(),"6020","test2","test2",1,"test2");
        System.out.println(GuestHandler.readGuest(x.getGuest_id()));
        GuestHandler.deleteGuest(x.getGuest_id());
        GuestHandler.readAllGuest();

        ServiceType y = ServiceTypeHandler.createServiceType("Test", BigDecimal.valueOf(0.1));
        System.out.println(ServiceTypeHandler.readServiceType(y.getService_id()));
        ServiceTypeHandler.updateServiceType(y.getService_id(),"Test2", BigDecimal.valueOf(2.9));
        System.out.println(ServiceTypeHandler.readServiceType(y.getService_id()));
        ServiceTypeHandler.deleteServiceType(y.getService_id());
        ServiceTypeHandler.readAllServiceTypes();

        Event z = EventHandler.createEvent("Test", Date.valueOf("2024-01-01"));
        System.out.println(EventHandler.readEvent(z.getEvent_id()));
        EventHandler.updateEvent(z.getEvent_id(),"Test2", Date.valueOf("2024-01-01"));
        System.out.println(EventHandler.readEvent(z.getEvent_id()));
        EventHandler.deleteEvent(z.getEvent_id());
        EventHandler.readAllEvents();
        
    }

    public static void readAllRooms(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT x FROM room x";
        List<Room> list = null;
        try {
            TypedQuery<Room> tq = em.createQuery(query, Room.class);
            list = tq.getResultList();
            list.forEach(x -> System.out.println(
                    "Nr: " + x.getRoom_nr() + "\n" +
                            "Capacity: " + x.getMax_occupants() + "\n" +
                            "Cost: " + x.getCost() + "\n"
            ));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
    public static void readAllEvents(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT x FROM event x";
        List<Event> list = null;
        try {
            TypedQuery<Event> tq = em.createQuery(query, Event.class);
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
    public static void readAllReservations(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT x FROM reservation x";
        List<Reservation> list = null;
        try {
            TypedQuery<Reservation> tq = em.createQuery(query, Reservation.class);
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
    public static void readAllPlz(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT x FROM plz x";
        List<Plz> list = null;
        try {
            TypedQuery<Plz> tq = em.createQuery(query, Plz.class);
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

    public static void readAllInvoices(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT x FROM invoice x";
        List<Invoice> list = null;
        try {
            TypedQuery<Invoice> tq = em.createQuery(query, Invoice.class);
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
    public static void readAllBookedServices(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT x FROM booked_service x";
        List<BookedService> list = null;
        try {
            TypedQuery<BookedService> tq = em.createQuery(query, BookedService.class);
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
    public static void readAllServiceTypes(){
        EntityManager em = EMF.createEntityManager();
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
    public static void readAllJobs(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT x FROM job x";
        List<Job> list = null;
        try {
            TypedQuery<Job> tq = em.createQuery(query, Job.class);
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
    public static void readAllEmployees(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT x FROM employee x";
        List<Employee> list = null;
        try {
            TypedQuery<Employee> tq = em.createQuery(query, Employee.class);
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
    public static void readAllMaintenanceTypes(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT x FROM maintenance_type x";
        List<MaintenanceType> list = null;
        try {
            TypedQuery<MaintenanceType> tq = em.createQuery(query, MaintenanceType.class);
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
    public static void readAllPlannedMaintenances(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT x FROM planned_maintenance x";
        List<PlannedMaintenance> list = null;
        try {
            TypedQuery<PlannedMaintenance> tq = em.createQuery(query, PlannedMaintenance.class);
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








    public static List<Job> getAllJobs(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT x FROM employee x";
        List<Job> list = null;
        try {
            TypedQuery<Job> tq = em.createQuery(query, Job.class);
            list = tq.getResultList();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }
}
