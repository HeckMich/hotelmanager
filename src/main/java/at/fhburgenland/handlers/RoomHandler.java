package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Reservation;
import at.fhburgenland.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RoomHandler {
    public RoomHandler() {
    }
    public static Room createRoom(int room_nr, int max_occupants, BigDecimal cost) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Room room;
        try {
            et = em.getTransaction();
            et.begin();
            room = new Room(room_nr, max_occupants, cost);
            em.persist(room);
            et.commit();
            System.out.println("Created Room: " + room);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in RoomHandler createRoom: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return room;
    }
    public static boolean deleteRoom(int room_nr) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Room room = null;
        try {
            et = em.getTransaction();
            et.begin();
            room = em.find(Room.class, room_nr);
            em.remove(room);
            et.commit();
            System.out.println("deleted " + room);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in RoomHandler deleteRoom: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    public static Room updateRoom(int room_nr, int max_occupants, BigDecimal cost) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Room room = null;
        try {
            et = em.getTransaction();
            et.begin();
            room = em.find(Room.class, room_nr);
            if (room == null)
                throw new IllegalArgumentException("The Room with id : " + room_nr + " was not found in DB. Update canceled.");
            room.setMax_occupants(max_occupants);
            room.setCost(cost);
            em.persist(room);
            et.commit();
            System.out.println("Updated " + room);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in RoomHandler UpdateRoom: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return room;
    }

    public static Room readRoom(int room_nr) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Room room = null;
        try {
            et = em.getTransaction();
            et.begin();
            room = em.find(Room.class, room_nr);
            if (room == null)
                throw new IllegalArgumentException("The Room with id : " + room_nr + " was not found in DB.");
            return room;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in RoomHandler ReadRoom: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static List<Room> readAllRoom() {
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM room x";
        List<Room> list = null;
        try {
            TypedQuery<Room> tq = em.createQuery(query, Room.class);
            list = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }
}
