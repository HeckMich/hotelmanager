package at.fhburgenland.entities;

import at.fhburgenland.EMFSingleton;
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
        // TODO implement createFromUserInput in at.fhburgenland.entities.Guest
        return null;
    }
}
