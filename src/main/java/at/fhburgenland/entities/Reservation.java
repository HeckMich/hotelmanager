package at.fhburgenland.entities;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.handlers.HotelEntityHandler;
import jakarta.persistence.*;

import java.util.*;

@Entity(name = "reservation")
@Table(name = "reservation")
public class Reservation extends HotelEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int reservation_id;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date start_date;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date end_date;

    @ManyToMany(mappedBy = "reservation")
    private Set<Event> event = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "room_nr")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @OneToMany(mappedBy = "reservation")
    private List<Invoice> invoices = new ArrayList<>();

    @OneToMany(mappedBy = "reservation")
    private List<BookedService> bookedServices  = new ArrayList<>();

    public Reservation() {
    }
    
    public Reservation(Guest guest,Room room, Date start_date, Date end_date) {
        this.guest = guest;
        this.room = room;
        this.start_date = start_date;
        this.end_date = end_date;
        this.event = new HashSet<>();
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public int getGuest_id() {
        return this.guest.getGuest_id();
    }

    public void setGuest(int guest_id) {
        EntityManager EM = EMFSingleton.getEntityManager();
        Guest guest = EM.find(Guest.class, guest_id);
        if (guest != null) {
            this.guest = guest;
        } else {
            throw new IllegalArgumentException("No guest found with guest_id: " + guest_id);
        }
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
        return this.room.getRoom_nr();
    }

    public void setRoom(int roomNr) {
        EntityManager EM = EMFSingleton.getEntityManager();
        Room room = EM.find(Room.class, roomNr);
        if (room != null) {
            this.room = room;
        } else {
            throw new IllegalArgumentException("No room found with room number: " + roomNr);
        }
    }

    public Set<Event> getEvents() {
        return event;
    }

    public void setEvent(Set<Event> event) {
        this.event = event;
    }

    public void addEvent(Event event) {
        this.event.add(event);
        event.getReservations().add(this);
    }
    public void removeEvent(Event event) {
        this.event.remove(event);
        event.getReservations().remove(this);
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_id=" + reservation_id +
                ", guest_id=" + this.guest.getGuest_id() +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", room_nr=" + this.room.getRoom_nr() +
                '}';
    }

    @Override
    public HotelEntity createFromUserInput() {
        Reservation entity = new Reservation();

        //Room NR
        Room room = HotelEntityHandler.selectEntityFromList(Room.class);
        entity.setRoom(room);
        // Guest
        Guest guest = HotelEntityHandler.selectEntityFromList(Guest.class);
        entity.setGuest(guest);
        // Start date
        String i1 = "Please enter the Start Date in the format dd.MM.yyy like 18.03.2024";
        String e1 = "Invalid input!";
        entity.setStart_date(parseDateFromUser(i1,e1));
        // End date
        String i2 = "Please enter the End Date in the format dd.MM.yyy like 18.03.2024";
        entity.setEnd_date(parseDateFromUser(i2,e1));

        return entity;
    }
}
