package at.fhburgenland.entities;

import at.fhburgenland.helpers.ColorHelper;
import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.handlers.HotelEntityHandler;
import jakarta.persistence.*;

import java.util.*;

/**
 * class for entity "reservation"
 */
@Entity(name = "reservation")
@Table(name = "reservation")
public class Reservation extends HotelEntity  {


    /**
     * PK here
     * FK in Invoice
     */
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

    /**
     * Many Reservations can be linked to many Events (see Event)
     */
    @ManyToMany(mappedBy = "reservation", cascade = CascadeType.REMOVE)
    private Set<Event> event = new HashSet<>();

    /**
     * Many Reservations can be linked to same Room.
     */
    @ManyToOne
    @JoinColumn(name = "room_nr")
    private Room room;

    /**
     * FK here
     * PK in Room
     */
    @Column(name = "room_nr", nullable = false, insertable = false, updatable = false)
    private int room_nr;


    /**
     * Many Reservations can be linked to same Guest.
     */
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;


    /**
     * FK here
     * PK in Guest
     */
    @Column(name = "guest_id", nullable = false, insertable = false, updatable = false)
    private int guest_id;

    /**
     * One Reservation can be linked to many Invoices
     */
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.REMOVE)
    private List<Invoice> invoices = new ArrayList<>();

    /**
     * A Reservation can have many BookedServices linked to it.
     */
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.REMOVE)
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

    public Reservation(Guest guest,Room room, Date start_date, Date end_date, HashSet<Event> events) {
        this.guest = guest;
        this.room = room;
        this.start_date = start_date;
        this.end_date = end_date;
        this.event = events;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public int getGuest_id() {
        return this.guest.getGuest_id();
    }

    public Guest getGuest() {
        return guest;
    }


    /**
     * Before setting a Guest, one has to look, if the Guest exists.
     * If there is no matching Guest, Error/Exception will be thrown
     * @param guest_id
     */
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

    /**
     * Before Room is set, on hase to look, if mathcin Room exists.
     * If not, Error/Exception will be thrown.
     * @param roomNr
     */
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

    /**
     * Create-Method.
     * Calls helper-methods, that prompt the user.
     * @return (Reservation)
     */
    @Override
    public HotelEntity createFromUserInput() {
        Reservation entity = new Reservation();
        //Room NR
        ColorHelper.printBlue("Please select the room for reservation:");
        changeRoomFromUser(entity);
        // Guest
        ColorHelper.printBlue("Please select the Guest for reservation:");
        changeGuestFromUser(entity);
        // Date
        changeDatesFromUser(entity);



        return entity;
    }

    /**
     * Update-Method.
     * Menu that prompts user, what to update.
     * Calls helper-methods.
     * @return (Reservation)
     */
    public HotelEntity updateFromUserInput() {
        // Select from index
        ColorHelper.printBlue("Please select the " + this.getClass().getSimpleName() +" to update:");
        Reservation entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - Room");
            ColorHelper.printYellow("2 - Guest");
            ColorHelper.printYellow("3 - Dates");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x","X" -> {
                    return entity;
                }
                case "1" ->  changeRoomFromUser(entity);
                case "2" ->  changeGuestFromUser(entity);
                case "3" ->  changeDatesFromUser(entity);
                default ->  ColorHelper.printRed(e1);
            }

        }
    }

    private static void changeRoomFromUser(Reservation entity) {
        Room room = HotelEntityHandler.selectEntityFromFullList(Room.class);
        entity.setRoom(room);
    }


    private static void changeDatesFromUser(Reservation entity) {
        // Start date
        String i1 = "Please enter the Start Date in the format dd.MM.yyy like 18.03.2024";
        entity.setStart_date(parseDateFromUser(i1,e1));
        // End date
        String i2 = "Please enter the End Date in the format dd.MM.yyy like 18.03.2024";
        entity.setEnd_date(parseDateFromUser(i2,e1));
    }

    private static void changeGuestFromUser(Reservation entity) {
        Guest guest = HotelEntityHandler.selectEntityFromFullList(Guest.class);
        entity.setGuest(guest);
    }


    @Override
    public String toString() {
        return "[" +
                "reservation_id : " + reservation_id +
                ", start_date : " + start_date +
                ", end_date : " + end_date +
                ", room_nr : " + room_nr +
                ", guest : " + guest +
                "]";
    }
}
