package at.fhburgenland.entities;

import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity(name = "room")
@Table( name = "room")
public class Room extends HotelEntity  {

    @Id
    @Column(name = "room_nr", nullable = false)
    private int room_nr;

    @Column(name = "max_occupants", nullable = false)
    private int max_occupants;

    @Column(name = "cost", nullable = false, precision = 5, scale = 2)
    private BigDecimal cost;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private List<PlannedMaintenance> plannedMaintenances = new ArrayList<>();


    public Room() {
        // TODO Initialization of fields of Room
    }

    public Room(int room_nr, int max_occupants, BigDecimal cost) {
        this.room_nr = room_nr;
        this.max_occupants = max_occupants;
        this.cost = cost;
    }

    public int getRoom_nr() {
        return room_nr;
    }

    public void setRoom_nr(int room_nr) {
        this.room_nr = room_nr;
    }

    public int getMax_occupants() {
        return max_occupants;
    }

    public void setMax_occupants(int max_occupants) {
        this.max_occupants = max_occupants;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public HotelEntity createFromUserInput() {
        Room entity = new Room();
        //Room NR
        changeRoomNr(entity);
        // Cost
        changeCost(entity);
        // Max Occupants
        changeMaxOccupants(entity);
        return entity;
    }

    public HotelEntity updateFromUserInput() {
        // Select from index
        ColorHelper.printBlue("Please select the " + this.getClass().getSimpleName() +" to update:");
        Room entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - Cost");
            ColorHelper.printYellow("2 - Max Occupants");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x","X" -> {
                    return entity;
                }
                case "1" ->  changeCost(entity);
                case "2" ->  changeMaxOccupants(entity);
                default ->  ColorHelper.printRed(e1);
            }

        }
    }

    private void changeCost(Room entity) {
        String i2 = "Please enter the new cost for the room. Only enter a number using '.' for up to two decimal points!";
        entity.setCost(parseBigDecimalFromUser(i2,e1));
    }
    private void changeRoomNr(Room entity) {
        String i1 = "Please enter the Room-Number of the new room. Only enter a number. Do not enter a Room-Number which already exists!";
        entity.setRoom_nr(parseIntFromUser(i1,e1));
    }
    private void changeMaxOccupants(Room entity) {
        String i3 = "Please enter the maximum number of occupants for the room. Only enter a number.";
        entity.setMax_occupants(parseIntFromUser(i3,e1));
    }

    @Override
    public String toString() {
        return "[" +
                "room_nr : " + room_nr +
                ", max_occupants : " + max_occupants +
                ", cost : " + cost +
                "]";
    }
}
