package at.fhburgenland;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "Room")
@Table( name = "room")
public class Room {

    @Id
    @Column(name = "room_nr", nullable = false)
    private int room_nr;

    @Column(name = "max_occupants", nullable = false)
    private int max_occupants;

    @Column(name = "cost", nullable = false)
    private double cost;


    public Room() {
        // TODO Initialization of fields of Room
    }

    public Room(int room_nr, int max_occupants, double cost) {
        this.room_nr = room_nr;
        this.max_occupants = max_occupants;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Room{" +
                "room_nr=" + room_nr +
                ", max_occupants=" + max_occupants +
                ", cost=" + cost +
                '}';
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
