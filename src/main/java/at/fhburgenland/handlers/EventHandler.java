package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Event;
import at.fhburgenland.entities.Guest;
import at.fhburgenland.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EventHandler {
    public static Event createEvent(String name, Date date) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Event event;
        try {
            et = em.getTransaction();
            et.begin();

            event = new Event(name, date);
            em.persist(event);
            et.commit();
            System.out.println("Created Event: " + event);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in EventHandler createEvent: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return event;
    }

    public static boolean deleteEvent(int event_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Event event = null;
        try {
            et = em.getTransaction();
            et.begin();
            event = em.find(Event.class, event_id);
            if (event == null) throw new IllegalArgumentException("The event with id : " + event_id + " was not found in DB. Deletion canceled." );
            em.remove(event);
            et.commit();
            System.out.println("Deleted " + event);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in EventHandler DeleteEvent: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    public static Event updateEvent(int event_id, String name, Date date) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Event event = null;
        try {
            et = em.getTransaction();
            et.begin();
            event = em.find(Event.class, event_id);
            if (event == null) throw new IllegalArgumentException("The event with id : " + event_id + " was not found in DB. Update canceled." );
            event.setName(name);
            event.setDate(date);

            em.persist(event);
            et.commit();
            System.out.println("Updated " + event);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in EventHandler UpdateEvent: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return event;
    }

    public static Event readEvent(int event_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Event event = null;
        try {
            et = em.getTransaction();
            et.begin();
            event = em.find(Event.class, event_id);
            if (event == null)
                throw new IllegalArgumentException("The event with id : " + event_id + " was not found in DB.");
            return event;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in EventHandler EventGuest: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

        public static void readAllEvents(){
            EntityManager em = EMFSingleton.getEntityManager();
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

}
