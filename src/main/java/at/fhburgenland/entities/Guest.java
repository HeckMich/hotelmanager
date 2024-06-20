package at.fhburgenland.entities;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.handlers.HotelEntityHandler;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "guest")
@Table(name = "guest")
public class Guest extends HotelEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id", nullable = false)
    private int guest_id;

    @Column(name = "last_name", length = 50, nullable = false)
    private String last_name;

    @Column(name = "first_name", length = 50, nullable = false)
    private String first_name;

    @Column(name = "house_number", nullable = false)
    private int house_number;

    @Column(name = "street", length = 100, nullable = false)
    private String street;

    @Column(name = "plz", nullable = false, insertable = false, updatable = false)
    private String plz_;

    @OneToMany(mappedBy = "guest")
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "plz")
    private Plz plz;

    @OneToMany(mappedBy = "guest")
    private List<Invoice> invoices = new ArrayList<>();

    public Guest() {
    }

    public Guest(String plz, String last_name, String first_name, int house_number, String street) {
        this.plz_ = plz;
        this.last_name = last_name;
        this.first_name = first_name;
        this.house_number = house_number;
        this.street = street;
    }
    public Guest(Plz plz, String last_name, String first_name, int house_number, String street) {
        this.plz = plz;
        this.last_name = last_name;
        this.first_name = first_name;
        this.house_number = house_number;
        this.street = street;
    }

    public int getGuest_id() {
        return guest_id;
    }

    public String getPlz() {
        return this.plz.getPlz();
    }

    public void setPlz(String plz) {
        EntityManager EM = EMFSingleton.getEntityManager();
        Plz plz_obj = EM.find(Plz.class, plz);
        if (plz != null) {
            this.plz = plz_obj;
        } else {
            throw new IllegalArgumentException("No plz found with: " + plz);
        }
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getHouse_number() {
        return house_number;
    }

    public void setHouse_number(int house_number) {
        this.house_number = house_number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPlz(Plz plz) {
        this.plz = plz;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "guest_id=" + guest_id +
                ", plz='" + plz + '\'' +
                ", last_name='" + last_name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", house_number=" + house_number +
                ", street='" + street + '\'' +
                '}';
    }


    @Override
    public HotelEntity createFromUserInput() {
        Guest entity = new Guest();

        //PLZ
        Plz plz = HotelEntityHandler.selectEntityFromList(Plz.class);
        entity.setPlz(plz);
        //GuestID=automatisch
        //First Name
        String i1 = "Please enter the First Name of the Guest";
        String e1 = "Invalid input";
        entity.setFirst_name(parseStringFixedLengthFromUser(i1, e1, 1, 50));
        //Last Name
        String i2 = "Please enter the Last Name of the Guest";
        entity.setLast_name(parseStringFixedLengthFromUser(i2, e1, 1, 50));

        //Street
        String i3 = "Please enter the Name of the Streeet the Guest is living on";
        entity.setStreet(parseStringFixedLengthFromUser(i3, e1, 1, 100));

        //HOuseNumber
        String i4 = "Please enter the House Number of the Guest";
        entity.setHouse_number(parseIntFromUser(i4, e1));
        return entity;
    }
}
