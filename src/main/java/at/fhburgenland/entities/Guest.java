package at.fhburgenland.entities;

import at.fhburgenland.helpers.ColorHelper;
import at.fhburgenland.helpers.EMFSingleton;
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

    @OneToMany(mappedBy = "guest", cascade = CascadeType.REMOVE)
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "plz")
    private Plz plz;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.REMOVE)
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
    public HotelEntity createFromUserInput() {
        Guest entity = new Guest();

        //PLZ
        changePlz(entity);
        //First Name
        changeFirstName(entity);
        //Last Name
        changeLastName(entity);

        //Street
        changeStreet(entity);

        //HouseNumber
        changeHouseNumber(entity);
        return entity;
    }

    private static void changeHouseNumber(Guest entity) {
        String i4 = "Please enter the House Number of the Guest";
        entity.setHouse_number(parseIntFromUser(i4, e1));
    }

    private static void changeStreet(Guest entity) {
        String i3 = "Please enter the Name of the Streeet the Guest is living on";
        entity.setStreet(parseStringFixedLengthFromUser(i3, e1, 1, 100));
    }

    private static void changeLastName(Guest entity) {
        String i2 = "Please enter the Last Name of the Guest";
        entity.setLast_name(parseStringFixedLengthFromUser(i2, e1, 1, 50));
    }

    private static void changeFirstName(Guest entity) {
        String i1 = "Please enter the First Name of the Guest";
        entity.setFirst_name(parseStringFixedLengthFromUser(i1, e1, 1, 50));
    }

    private static void changePlz(Guest entity) {
        HotelEntityHandler.printAllAsIndexedList(Plz.class);
        Plz plz = new Plz();
        boolean choosing = true;
        while (choosing) {
            ColorHelper.printBlue("Choose existing Plz?");
            ColorHelper.printYellow("Y - yes (choose from list)");
            ColorHelper.printYellow("N - no (enter new Plz)");
            String line = scanner.next();
            switch (line) {
                case "Y", "y" -> {
                    plz =  HotelEntityHandler.selectEntityFromFullList(Plz.class);
                    choosing = false;
                }
                case "N", "n" -> {
                    plz = (Plz)HotelEntityHandler.create(plz.createFromUserInput());
                    choosing = false;
                }
                default -> ColorHelper.printRed("Invalid input!");
            }
        }
        entity.setPlz(plz);
    }

    public HotelEntity updateFromUserInput() {
        // Select Room from index
        ColorHelper.printBlue("Please select the guest to update:");
        Guest entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - PLZ");
            ColorHelper.printYellow("2 - First Name");
            ColorHelper.printYellow("3 - Last Name");
            ColorHelper.printYellow("4 - Street");
            ColorHelper.printYellow("5 - House Number");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x","X" -> {
                    return entity;
                }
                case "1" ->  changePlz(entity);
                case "2" ->  changeFirstName(entity);
                case "3" ->  changeLastName(entity);
                case "4" ->  changeStreet(entity);
                case "5" ->  changeHouseNumber(entity);
                default ->  ColorHelper.printRed(e1);
            }

        }
    }


    @Override
    public String toString() {
        return "[" +
                "guest_id : " + guest_id +
                ", last_name : '" + last_name + '\'' +
                ", first_name : '" + first_name + '\'' +
                ", house_number : " + house_number +
                ", street : '" + street + '\'' +
                ", plz : '" + plz_ + '\'' +
                "]";
    }
}
