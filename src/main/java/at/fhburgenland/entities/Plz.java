package at.fhburgenland.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "plz")
@Table(name = "plz")
public class Plz {

    @Id
    @Column(name = "plz", nullable = false, length = 10)
    private String plz;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @OneToMany(mappedBy = "plz")
    private List<Guest> guests = new ArrayList<>();

    public Plz() {
    }

    public Plz(String plz, String city) {
        this.plz = plz;
        this.city = city;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Plz{" +
                "plz='" + plz + '\'' +
                ", city='" + city + '\'' +
                '}';
    }


}
