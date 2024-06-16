package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Guest;
import at.fhburgenland.entities.Reservation;
import at.fhburgenland.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jdk.jshell.spi.ExecutionControl;
import org.hibernate.event.spi.ResolveNaturalIdEvent;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
            res = new Reservation(guest, room, start_date, end_date);
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
        Reservation reservation = null;
        try {
            et = em.getTransaction();
            et.begin();
            reservation = em.find(Reservation.class, reservation_id);
            if (reservation == null)
                throw new IllegalArgumentException("The reservation with id : " + reservation_id + " was not found in DB. Deletion canceled.");
            em.remove(reservation);
            et.commit();
            System.out.println("Deleted " + reservation);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in ReservationHandler DeleteReservation: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    public static Reservation updateReservation(int guest_id, int reservation_id, Date start_date, Date end_date, int room_nr) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Reservation reservation = null;
        try {
            et = em.getTransaction();
            et.begin();
            reservation = em.find(Reservation.class, reservation_id);
            if (reservation == null)
                throw new IllegalArgumentException("The reservation with id : " + reservation_id + " was not found in DB. Update canceled.");
            reservation.setGuest(guest_id);
            reservation.setStart_date(start_date);
            reservation.setEnd_date(end_date);
            reservation.setRoom(room_nr);
            em.persist(reservation);
            et.commit();
            System.out.println("Updated " + reservation);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in ReservationHandler UpdateReservation: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return reservation;
    }

    public static Reservation readReservation(int reservation_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Reservation reservation = null;
        try {
            et = em.getTransaction();
            et.begin();
            reservation = em.find(Reservation.class, reservation_id);
            if (reservation == null)
                throw new IllegalArgumentException("The reservation with id : " + reservation_id + " was not found in DB.");
            return reservation;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in ReservationHandler ReadReservation: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static List<Reservation> readAllReservations() {
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM reservation x";
        List<Reservation> list = null;
        try {
            TypedQuery<Reservation> tq = em.createQuery(query, Reservation.class);
            list = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }
}
