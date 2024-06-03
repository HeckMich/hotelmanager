package at.fhburgenland.entities;

import jakarta.persistence.*;

@Entity(name = "guest")
@Table(name = "guest")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id", nullable = false)
    private int guest_id;

    @Column(name = "plz", length = 10, nullable = false)
    private String plz;

    @Column(name = "last_name", length = 50, nullable = false)
    private String last_name;

    @Column(name = "first_name", length = 50, nullable = false)
    private String first_name;

    @Column(name = "house_number", nullable = false)
    private int house_number;

    @Column(name = "street", length = 100, nullable = false)
    private String street;


    public Guest() {
        // TODO Initialization of fields of Guest
    }

    public Guest(int guest_id, String plz, String last_name, String first_name, int house_number, String street) {
        this.guest_id = guest_id;
        this.plz = plz;
        this.last_name = last_name;
        this.first_name = first_name;
        this.house_number = house_number;
        this.street = street;
    }

    public int getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
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


}
