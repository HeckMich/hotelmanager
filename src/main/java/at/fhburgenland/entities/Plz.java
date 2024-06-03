package at.fhburgenland.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "plz")
@Table(name = "plz")
public class Plz {

    @Id
    @Column(name = "plz", nullable = false, length = 10)
    private String plz;

    @Column(name = "city", nullable = false, length = 100)
    private String city;


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
