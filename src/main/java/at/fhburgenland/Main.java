package at.fhburgenland;

import at.fhburgenland.entities.Room;
import jakarta.persistence.*;

import java.util.List;

public class Main {

    private static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("hotelmanagement");

    public static void main(String[] args) {
        System.out.println("Test");
        readAllPersons();
    }

    public static void readAllPersons(){
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
}
