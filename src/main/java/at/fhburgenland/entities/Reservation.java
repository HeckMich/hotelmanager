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

    @OneToOne(mappedBy = "reservation")
    private Room room;

    public Reservation() {
        // TODO Initialization of fields of reservation
    }

    // TODO Implement body of reservation
}
