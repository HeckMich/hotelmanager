package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Guest;
import at.fhburgenland.entities.Plz;
import at.fhburgenland.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.util.List;

public class GuestHandler {
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
    public static Guest createGuest(String plz, String last_name, String first_name, int house_number, String street) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Guest guest;
        try {
            et = em.getTransaction();
            et.begin();
            Plz plz_obj = em.find(Plz.class, plz);
            if (plz_obj == null) throw new IllegalArgumentException("The plz: " + plz + " was not found in DB. Please Crete" );
            guest = new Guest(plz_obj, last_name, first_name, house_number, street);
            em.persist(guest);
            et.commit();
            System.out.println("Created Guest: " + guest);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in GuestHandler CreateGuest: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return guest;
    }

    public static boolean deleteGuest(int guest_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Guest guest = null;
        try {
            et = em.getTransaction();
            et.begin();
            guest = em.find(Guest.class, guest_id);
            if (guest == null) throw new IllegalArgumentException("The guest with id : " + guest_id + " was not found in DB. Deletion canceled." );
            em.remove(guest);
            et.commit();
            System.out.println("Deleted " + guest);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in GuestHandler DeleteGuest: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }
    public static Guest updateGuest(int guest_id, String plz, String last_name, String first_name, int house_number, String street) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Guest guest = null;
        try {
            et = em.getTransaction();
            et.begin();
            guest = em.find(Guest.class, guest_id);
            if (guest == null) throw new IllegalArgumentException("The guest with id : " + guest_id + " was not found in DB. Update canceled." );
            guest.setFirst_name(first_name);
            guest.setLast_name(last_name);
            guest.setPlz(plz);
            guest.setHouse_number(house_number);
            guest.setStreet(street);
            em.persist(guest);
            et.commit();
            System.out.println("Updated " + guest);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in GuestHandler UpdateGuest: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return guest;
    }
    public static Guest readGuest(int guest_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Guest guest = null;
        try {
            et = em.getTransaction();
            et.begin();
            guest = em.find(Guest.class, guest_id);
            if (guest == null) throw new IllegalArgumentException("The guest with id : " + guest_id + " was not found in DB." );
            return guest;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in GuestHandler ReadGuest: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static void readAllGuest(){
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM guest x";
        List<Guest> list = null;
        try {
            TypedQuery<Guest> tq = em.createQuery(query, Guest.class);
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
