package at.fhburgenland.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "event")
@Table(name = "event")
public class Event {
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

    public Event(int event_id, String name, Date date, Set<Reservation> reservation) {
        this.event_id = event_id;
        this.name = name;
        this.date = date;
        this.reservation = reservation;
    }

    public Event(){

    }

    public int getEvent_id() {
        return event_id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "event_id=" + this.event_id +
                ", name='" + this.name + '\'' +
                ", date=" + this.date +
                '}';
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
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

    public Set<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(Set<Reservation> reservation) {
        this.reservation = reservation;
    }
}
