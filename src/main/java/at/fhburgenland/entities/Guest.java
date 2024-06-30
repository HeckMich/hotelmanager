package at.fhburgenland.entities;

import at.fhburgenland.helpers.ColorHelper;
import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.handlers.HotelEntityHandler;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * class for entity "guest"
 */
@Entity(name = "guest")
@Table(name = "guest")
public class Guest extends HotelEntity  {

    /**
     * PK here
     * FK in Invoice and Reservation
     */
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

    /**
     * FK here
     * PK in PLZ
     */
    @Column(name = "plz", nullable = false, insertable = false, updatable = false)
    private String plz_;

    /**
     * one Guest can have many Reservations
     */
    @OneToMany(mappedBy = "guest", cascade = CascadeType.REMOVE)
    private Set<Reservation> reservations = new HashSet<>();

    /**
     * Many Guests can have the same PLZ
     */
    @ManyToOne
    @JoinColumn(name = "plz")
    private Plz plz;

    /**
     * One Guest can have many Invoices
     */
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


    /**
     * Crate-method.
     * Calls various helper-methods, that prompt the user.
     * @return (Guest)
     */
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
        String i3 = "Please enter the Name of the Street the Guest is living on";
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

    /**
     * update-method to change the PLZ.
     * User has the choice between choosing a PLz from PLZs already in the Database,
     * or enter a new one.
     * @param entity
     */
    private static void changePlz(Guest entity) {
        List<Plz> listAllPlz = HotelEntityHandler.readAll(Plz.class);
        ColorHelper.printBlue("The following PLZ are already in the system:");
        HotelEntityHandler.printAsNeutralList(listAllPlz);
        Plz plz = new Plz();
        boolean choosing = true;
        while (choosing) {
            ColorHelper.printBlue("Choose an existing PLZ for the new Guest?");
            ColorHelper.printYellow("Y - yes (choose from list)");
            ColorHelper.printYellow("N - no (enter new PLZ)");
            String line = scanner.nextLine();
            switch (line) {
                case "Y", "y" -> {
                    plz =  HotelEntityHandler.selectEntityFromFullList(Plz.class);
                    if (plz != null) choosing = false;
                    else {
                        ColorHelper.printRed("No PLZ available to select!");
                        plz = new Plz();
                    }
                }
                case "N", "n" -> {
                    plz = (Plz)HotelEntityHandler.create(plz.createFromUserInput());
                    if(plz != null) {
                        choosing = false;
                    } else {
                        plz = new Plz();
                        ColorHelper.printRed("This PLZ already exists in the system!");
                    }
                }
                default -> ColorHelper.printRed("Invalid input!");
            }
        }
        entity.setPlz(plz);
    }

    /**
     * Update-Method.
     * User gets prompted by menu, what to change.
     * Calls various helper-methods, that prompt the user.
     * @return (Guest)
     */
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
                ", plz : '" + (plz == null ? plz_ : plz.getPlz()) + '\'' +
                "]";
    }
}
