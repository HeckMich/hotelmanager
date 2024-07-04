package at.fhburgenland.entities;

import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import at.fhburgenland.helpers.EMFSingleton;
import jakarta.persistence.*;

import java.util.*;

/**
 * class for entity "event"
 */
@Entity(name = "event")
@Table(name = "event")
public class Event extends HotelEntity {

    /**
     * PK here
     * FK in "will_attend" (no class for it, because between-table)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int event_id;
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    /**
     * between-table to connect Event and Reservation
     */
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "will_attend", joinColumns = {@JoinColumn(name = "event_id")}, inverseJoinColumns = {@JoinColumn(name = "reservation_id")})
    private Set<Reservation> reservation = new HashSet<>();

    public Event() {
    }

    /**
     * provides List of Events that take Place in a certain time-frame
     *
     * @param startDate start date
     * @param endDate   end date
     * @return (List < Event >) events between startdate and enddate
     */
    public static List<Event> getEventsForReservation(Date startDate, Date endDate) {
        EntityManager em = EMFSingleton.getEntityManager();
        String jpql = "SELECT a FROM event a " +
                "WHERE a.date BETWEEN :startDate AND :endDate";

        TypedQuery<Event> query = em.createQuery(jpql, Event.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        return query.getResultList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setReservation(Set<Reservation> reservation) {
        this.reservation = reservation;
    }

    /**
     * Crate-method.
     * Calls varios helper-methods, that prompt the user (see below)
     *
     * @return (Event)
     */
    @Override
    public HotelEntity createFromUserInput() {
        Event entity = new Event();
        //Name
        changeEventName(entity);
        // Date
        changeDate(entity);

        return entity;
    }

    private static void changeDate(Event entity) {
        String i2 = "Please enter the Date in the format dd.MM.yyy like 18.03.2024";
        entity.setDate(parseDateFromUser(i2, e1));
    }

    private static void changeEventName(Event entity) {
        String i1 = "Please enter the name of the Event:";
        entity.setName(parseStringFixedLengthFromUser(i1, e1, 1, 100));
    }

    /**
     * Update-Method.
     * Prompts user with menu, what to update.
     * Calls various helper-methods.
     *
     * @return updated HotelEntity as written to DB
     */
    @Override
    public HotelEntity updateFromUserInput() {
        // Select Room from index
        ColorHelper.printBlue("Please select the event to update:");
        Event entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - Event Name");
            ColorHelper.printYellow("2 - Date");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x", "X" -> {
                    return entity;
                }
                case "1" -> changeEventName(entity);
                case "2" -> changeDate(entity);
                default -> ColorHelper.printRed(e1);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event event)) return false;
        return event_id == event.event_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(event_id);
    }

    @Override
    public String toString() {
        return "[" +
                "event_id : " + event_id +
                ", name : '" + name + '\'' +
                ", date : " + date +
                "]";
    }

    public Collection<Reservation> getReservation() {
        return this.reservation;
    }
}
