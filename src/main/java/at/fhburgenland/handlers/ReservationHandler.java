package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Guest;
import at.fhburgenland.entities.Reservation;
import at.fhburgenland.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jdk.jshell.spi.ExecutionControl;
import org.hibernate.event.spi.ResolveNaturalIdEvent;

import java.math.BigDecimal;
import java.util.Date;

public class ReservationHandler {
    public ReservationHandler() {
    }
    public static Reservation createReservation(int room_nr, int guest_id, Date start_date, Date end_date) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Reservation res;
        try {
            et = em.getTransaction();
            et.begin();

            Room room = em.find(Room.class, room_nr);
            Guest guest = em.find(Guest.class, guest_id);

            if (room == null) throw new IllegalArgumentException("No room found with room_nr: " + room_nr);
            if (guest == null) throw new IllegalArgumentException("No guest found with guest_id: " + guest_id);

            res = new Reservation(guest,room,start_date,end_date);
            em.persist(res);
            et.commit();
            System.out.println("Created Reservation: " + res);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in ReservationHandler createReservation: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return res;
    }
    public static boolean deleteReservation(int reservation_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Reservation res = null;
        try {
            et = em.getTransaction();
            et.begin();
            res = em.find(Reservation.class, reservation_id);
            em.remove(res);
            et.commit();
            System.out.println("deleted " + res);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in ReservationHandler deleteReservation: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }
    public static Reservation updateReservation(int reservation_id) {
        throw new UnsupportedOperationException();
    }
    public static Reservation readReservation(int reservation_id) {
        throw new UnsupportedOperationException();
    }
}
