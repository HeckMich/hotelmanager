package at.fhburgenland.entities;

import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * class for entity "jobs"
 */
@Entity(name = "job")
@Table(name = "job")
public class Job extends HotelEntity {

    /**
     * PK here
     * FK in Employee and "can_do_maintenance" (no class, because between-table) and "can_do_service"(no class, because between-table)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id", nullable = false)
    private int job_id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    /**
     * between-table that connects Job and Service
     */
    @ManyToMany
    @JoinTable(name = "can_do_service", joinColumns = {@JoinColumn(name = "job_id")}, inverseJoinColumns = {@JoinColumn(name = "service_id")})
    private Set<ServiceType> serviceTypes = new HashSet<>();

    /**
     * One Job can have many Employees, who can do that Job
     */
    @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE)
    private List<Employee> employees = new ArrayList<>();

    /**
     * Many Jobs can do many different MaintenanceTypes
     */
    @ManyToMany(mappedBy = "jobs", cascade = CascadeType.REMOVE)
    private Set<MaintenanceType> maintenanceTypes = new HashSet<>();

    public Job() {

    }

    public Job(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * Create-Method.
     * Calls helper-method, that prompts user.
     *
     * @return (Job)
     */
    @Override
    public HotelEntity createFromUserInput() {
        Job entity = new Job();
        // Name
        changeName(entity);

        return entity;
    }

    private void changeName(Job entity) {
        String i1 = "Please enter the name of the Job:";
        entity.setName(parseStringFromUser(i1, e1));
    }

    /**
     * Update-Method.
     * Prompts user with menu, what to change.
     * Calls helper-method.
     *
     * @return
     */
    public HotelEntity updateFromUserInput() {
        // Select from index
        ColorHelper.printBlue("Please select the " + this.getClass().getSimpleName() + " to update:");
        Job entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - Name");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x", "X" -> {
                    return entity;
                }
                case "1" -> changeName(entity);
                default -> ColorHelper.printRed(e1);
            }

        }
    }

    @Override
    public String toString() {
        return "[" +
                "job_id : " + job_id +
                ", job name : '" + name + '\'' +
                "]";
    }
}
