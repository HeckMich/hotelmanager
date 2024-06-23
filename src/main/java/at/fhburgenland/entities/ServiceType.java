package at.fhburgenland.entities;

import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "service_type")
@Table(name = "service_type")
public class ServiceType extends HotelEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private int service_id;


    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "cost", nullable = false, precision = 5, scale = 2)
    private BigDecimal cost;

    @OneToMany(mappedBy = "serviceType", cascade = CascadeType.REMOVE)
    private List<BookedService> bookedServices  = new ArrayList<>();

    @ManyToMany(mappedBy = "serviceTypes", cascade = CascadeType.REMOVE)
    private Set<Job> jobs = new HashSet<>();

    public ServiceType() {

    }

    public ServiceType(String name, BigDecimal cost) {
        this.name = name;
        this.cost = cost;
    }

    public List<BookedService> getBookedServices() {
        return bookedServices;
    }

    public void setBookedServices(List<BookedService> bookedServices) {
        this.bookedServices = bookedServices;
    }

    public int getService_id() {
        return service_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public void addJob(Job job) {
        this.jobs.add(job);
        job.getServiceTypes().add(this);
    }
    public void removeJob(Job job) {
        this.jobs.remove(job);
        job.getServiceTypes().remove(this);
    }

    @Override
    public HotelEntity createFromUserInput() {
        ServiceType entity = new ServiceType();
        //Name
        changeNameFromUser(entity);
        // Cost
        changeCostFromUser(entity);

        return entity;
    }

    private static void changeCostFromUser(ServiceType entity) {
        String i2 = "Please enter the cost of the Service Type. Only enter a number using '.' for up to two decimal points!";
        entity.setCost(parseBigDecimalFromUser(i2,e1));
    }

    private void changeNameFromUser(ServiceType entity) {
        String i1 = "Please enter the name of the Service Type:";
        entity.setName(parseStringFromUser(i1,e1));
    }

    public HotelEntity updateFromUserInput() {
        // Select from index
        ColorHelper.printBlue("Please select the " + this.getClass().getSimpleName() +" to update:");
        ServiceType entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - Name");
            ColorHelper.printYellow("2 - Cost");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x","X" -> {
                    return entity;
                }
                case "1" ->  changeNameFromUser(entity);
                case "2" ->  changeCostFromUser(entity);
                default ->  ColorHelper.printRed(e1);
            }
        }
    }

    @Override
    public String toString() {
        return "[" +
                "service_id : " + service_id +
                ", name : '" + name + '\'' +
                ", cost : " + cost +
                "]";
    }
}
