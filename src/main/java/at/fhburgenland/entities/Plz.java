package at.fhburgenland.entities;

import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "plz")
@Table(name = "plz")
public class Plz extends HotelEntity  {

    @Id
    @Column(name = "plz", nullable = false, length = 10)
    private String plz;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @OneToMany(mappedBy = "plz", cascade = CascadeType.REMOVE)
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
    public void setPlz(String newplz) {
        this.plz = newplz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Override
    public HotelEntity createFromUserInput() {
        Plz entity = new Plz();
        //Plz
        String i1 = "Please enter the new PLZ. Enter a number between 1 and 10 digits long:";
        String e1 = "Invalid input!";
        entity.setPlz(parseStringFixedLengthFromUser(i1,e1,1,10));
        // City
        String i2 = "Please enter the name of the city. Use between 1 and 100 characters";
        entity.setCity(parseStringFixedLengthFromUser(i2,e1,1,100));

        return entity;
    }

    @Override
    public String toString() {
        return "[" +
                "plz : '" + plz + '\'' +
                ", city : '" + city + '\'' +
                "]";
    }
}
