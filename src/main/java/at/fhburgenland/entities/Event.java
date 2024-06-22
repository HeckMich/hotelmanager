package at.fhburgenland.entities;

import at.fhburgenland.helpers.EMFSingleton;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "event")
@Table(name = "event")
public class Event extends HotelEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int event_id;
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "will_attend", joinColumns = {@JoinColumn(name = "event_id")}, inverseJoinColumns = {@JoinColumn(name = "reservation_id")})
    private Set<Reservation> reservation = new HashSet<>();

    public Event(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public Event(){

    }

    public static List<Event> getEventsForReservation(Date startDate, Date endDate) {
        EntityManager em = EMFSingleton.getEntityManager();
        String jpql = "SELECT a FROM event a " +
                "WHERE a.date BETWEEN :startDate AND :endDate";

        TypedQuery<Event> query = em.createQuery(jpql, Event.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        return query.getResultList();
    }

    public int getEvent_id() {
        return event_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Reservation> getReservations() {
        return reservation;
    }

    public void setReservation(Set<Reservation> reservation) {
        this.reservation = reservation;
    }

    public void addReservation(Reservation reservation) {
        this.reservation.add(reservation);
        reservation.getEvents().add(this);
    }
    public void removeReservation(Reservation reservation) {
        this.reservation.remove(reservation);
        reservation.getEvents().remove(this);
    }

    @Override
    public HotelEntity createFromUserInput() {
        Event entity = new Event();

        //Name
        String i1 = "Please enter the name of the new Event:";
        String e1 = "Invalid input!";
        entity.setName(parseStringFixedLengthFromUser(i1,e1, 1, 100));
        // Date
        String i2 = "Please enter the Date in the format dd.MM.yyy like 18.03.2024";
        entity.setDate(parseDateFromUser(i2,e1));



        return entity;
    }

    @Override
    public String toString() {
        return "[" +
                "event_id : " + event_id +
                ", name : '" + name + '\'' +
                ", date : " + date +
                "]";
    }
}
