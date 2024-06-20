package at.fhburgenland.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
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

    @ManyToMany
    @JoinTable(name = "will_attend", joinColumns = {@JoinColumn(name = "event_id")}, inverseJoinColumns = {@JoinColumn(name = "reservation_id")})
    private Set<Reservation> reservation = new HashSet<>();

    public Event(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public Event(){

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
    public String toString() {
        return "Event{" +
                "event_id=" + this.event_id +
                ", name='" + this.name + '\'' +
                ", date=" + this.date +
                '}';
    }

    @Override
    public HotelEntity createFromUserInput() {
        // TODO implement createFromUserInput in at.fhburgenland.entities.Event
        return null;
    }
}
