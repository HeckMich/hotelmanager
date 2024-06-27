package at.fhburgenland.entities;

import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * class for entity "plz"
 */
@Entity(name = "plz")
@Table(name = "plz")
public class Plz extends HotelEntity  {


    /**
     * PK here
     * FK in Guest
     */
    @Id
    @Column(name = "plz", nullable = false, length = 10)
    private String plz;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    /**
     * One PLZ can be connected to many different Guests (see Guest)
     */
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

    /**
     * Update-Method.
     * User gets prompted with menu, what to change.
     * Calls helper-method.
     * @return (Plz)
     */
    public HotelEntity updateFromUserInput() {
        // Select from index
        ColorHelper.printBlue("Please select the " + this.getClass().getSimpleName() +" to update:");
        Plz entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - City");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x","X" -> {
                    return entity;
                }
                case "1" ->  changeCity(entity);
                default ->  ColorHelper.printRed(e1);
            }

        }
    }

    /**
     * Create-Method.
     * User can input desired plz for new entry.
     * Calls helper-method to input name of city.
     * @return
     */
    @Override
    public HotelEntity createFromUserInput() {
        Plz entity = new Plz();
        //Plz
        String i1 = "Please enter the new PLZ. Enter a number between 1 and 10 digits long:";
        entity.setPlz(parseStringFixedLengthFromUser(i1,e1,1,10));
        // City
        changeCity(entity);
        return entity;
    }

    private static void changeCity(Plz entity) {
        String i2 = "Please enter the name of the city. Use between 1 and 100 characters";
        entity.setCity(parseStringFixedLengthFromUser(i2, e1,1,100));
    }

    @Override
    public String toString() {
        return "[" +
                "plz : '" + plz + '\'' +
                ", city : '" + city + '\'' +
                "]";
    }
}
