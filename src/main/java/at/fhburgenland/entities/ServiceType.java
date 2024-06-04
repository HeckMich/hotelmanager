package at.fhburgenland.entities;

import jakarta.persistence.*;

@Entity(name = "service_type")
@Table(name = "service_type")
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private int service_id;


    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "cost", nullable = false)
    private double cost;

    public ServiceType() {

    }

    public ServiceType(int service_id, String name, double cost) {
        this.service_id = service_id;
        this.name = name;
        this.cost = cost;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "ServiceType{" +
                "service_id=" + service_id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
