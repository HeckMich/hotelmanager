package at.fhburgenland.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "reservation")
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int reservation_id;

    @Column(name = "guest_id")
    private int guest_id;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date start_date;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date end_date;

    @Column(name = "room_nr", nullable = false)
    private int room_nr;

    @ManyToMany(mappedBy = "reservation")
    private Set<Event> event = new HashSet<>();

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getRoom_nr() {
        return room_nr;
    }

    public void setRoom_nr(int room_nr) {
        this.room_nr = room_nr;
    }

    public Set<Event> getEvent() {
        return event;
    }

    public void setEvent(Set<Event> event) {
        this.event = event;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Reservation(int reservation_id, int guest_id, Date start_date, Date end_date, int room_nr, Set<Event> event, Room room) {
        this.reservation_id = reservation_id;
        this.guest_id = guest_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.room_nr = room_nr;
        this.event = event;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_id=" + reservation_id +
                ", guest_id=" + guest_id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", room_nr=" + room_nr +
                ", room=" + room +
                '}';
    }

    @OneToOne(mappedBy = "reservation")
    private Room room;

    public Reservation() {
    }
}
