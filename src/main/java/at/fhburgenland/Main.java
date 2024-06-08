package at.fhburgenland;

import at.fhburgenland.entities.Event;
import at.fhburgenland.entities.Reservation;
import at.fhburgenland.entities.Room;
import at.fhburgenland.handlers.ReservationHandler;
import at.fhburgenland.handlers.RoomHandler;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class Main {

    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("hotelmanagement");

    public static void main(String[] args) {
        System.out.println("Test");
        //RoomHandler.createRoom(202,9, BigDecimal.valueOf(999.10));
        //RoomHandler.deleteRoom(77);
        //ReservationHandler.createReservation(101,1, Date.valueOf("2024-01-01"),Date.valueOf("2024-01-02"));
        ReservationHandler.deleteReservation(11);
        readAllRooms();
        readAllEvents();
        readAllReservations();
    }

    public static void readAllRooms(){
        EntityManager em = EMF.createEntityManager();
        String query = "SELECT x FROM room x";
        List<Room> list = null;
        try {
            TypedQuery<Room> tq = em.createQuery(query, Room.class);
            list = tq.getResultList();
            list.forEach(room -> System.out.println(
                    "Nr: " + room.getRoom_nr() + "\n" +
                            "Capacity: " + room.getMax_occupants() + "\n" +
                            "Cost: " + room.getCost() + "\n"
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
            list.forEach(event -> System.out.println(
                    event.toString()
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
            list.forEach(reservation -> System.out.println(
                    reservation.toString()
            ));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}
