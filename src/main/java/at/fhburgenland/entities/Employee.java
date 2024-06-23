package at.fhburgenland.entities;

import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "employee")
@Table(name = "employee")
public class Employee extends HotelEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private int employee_id;

    @Column(name = "job_id", nullable = false, updatable = false, insertable = false)
    private int job_id;

    @Column(name = "first_name", length = 20, nullable = false)
    private String first_name;

    @Column(name = "last_name", length = 30, nullable = false)
    private String last_name;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    private List<BookedService> bookedServices  = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    private List<PlannedMaintenance> plannedMaintenances = new ArrayList<>();

    public Employee() {

    }

    public Employee(int job_id, String first_name, String last_name) {
        this.job_id = job_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public List<BookedService> getBookedServices() {
        return bookedServices;
    }

    public void setBookedServices(List<BookedService> bookedServices) {
        this.bookedServices = bookedServices;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public List<PlannedMaintenance> getPlannedMaintenances() {
        return plannedMaintenances;
    }

    public void setPlannedMaintenances(List<PlannedMaintenance> plannedMaintenances) {
        this.plannedMaintenances = plannedMaintenances;
    }

    @Override
    public HotelEntity createFromUserInput() {
        Employee entity = new Employee();

        //JobID
        changeJob(entity);
        //EMployeeID = automatisch
        // FirstName
        changeFirstName(entity);
        //LastName
        changeLastName(entity);


        return entity;
    }

    private static void changeLastName(Employee entity) {
        String i2 = "Please enter the Last Name of the new Employee";
        entity.setLast_name(parseStringFixedLengthFromUser(i2, e1, 1, 30));
    }

    private static void changeFirstName(Employee entity) {
        String i1 = "Please enter the First Name of the new Employee";
        entity.setFirst_name(parseStringFixedLengthFromUser(i1, e1, 1, 20));
    }

    private static void changeJob(Employee entity) {
        Job job = HotelEntityHandler.selectEntityFromFullList(Job.class);
        entity.setJob(job);
    }

    @Override
    public HotelEntity updateFromUserInput() {
        // Select Room from index
        ColorHelper.printBlue("Please select the employee to update:");
        Employee entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - Job");
            ColorHelper.printYellow("2 - First Name");
            ColorHelper.printYellow("3 - Last Name");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x","X" -> {
                    return entity;
                }
                case "1" ->  changeJob(entity);
                case "2" ->  changeFirstName(entity);
                case "3" ->  changeLastName(entity);
                default ->  ColorHelper.printRed(e1);
            }

        }
    }

    @Override
    public String toString() {
        return "[" +
                "employee_id : " + employee_id +
                ", first_name : '" + first_name + '\'' +
                ", last_name : '" + last_name + '\'' +
                "]";
    }
}
