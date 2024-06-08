package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;

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
    public static Room updateRoom(int room_nr) {
        throw new UnsupportedOperationException();
    }
    public static Room readRoom(int room_nr) {
        throw new UnsupportedOperationException();
    }
}
