package at.fhburgenland.entities;

import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "job")
@Table(name = "job")
public class Job extends HotelEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id", nullable = false)
    private int job_id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "can_do_service", joinColumns = {@JoinColumn(name = "job_id")}, inverseJoinColumns = {@JoinColumn(name = "service_id")})
    private Set<ServiceType> serviceTypes = new HashSet<>();

    @OneToMany(mappedBy = "job")
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany(mappedBy = "jobs")
    private Set<MaintenanceType> maintenanceTypes = new HashSet<>();

    public Job() {

    }

    public Job(String name) {
        this.name = name;
    }

    public int getJob_id() {
        return job_id;
    }


    public Set<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(Set<ServiceType> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addServiceType(ServiceType serviceType) {
        this.serviceTypes.add(serviceType);
        serviceType.getJobs().add(this);
    }
    public void removeServiceType(ServiceType serviceType) {
        this.serviceTypes.remove(serviceType);
        serviceType.getJobs().remove(this);
    }

    @Override
    public HotelEntity createFromUserInput() {
        Job entity = new Job();
        // Name
        changeName(entity);

        return entity;
    }

    private void changeName(Job entity) {
        String i1 = "Please enter the name of the Job:";
        entity.setName(parseStringFromUser(i1,e1));
    }

    public HotelEntity updateFromUserInput() {
        // Select from index
        ColorHelper.printBlue("Please select the " + this.getClass().getSimpleName() +" to update:");
        Job entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - Name");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x","X" -> {
                    return entity;
                }
                case "1" ->  changeName(entity);
                default ->  ColorHelper.printRed(e1);
            }

        }
    }

    @Override
    public String toString() {
        return "[" +
                "job_id : " + job_id +
                ", name : '" + name + '\'' +
                "]";
    }
}
