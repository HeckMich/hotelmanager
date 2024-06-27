package at.fhburgenland.entities;

import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * class for entity "maintenance_type"
 */
@Entity(name = "maintenance_type")
@Table(name = "maintenance_type")
public class MaintenanceType extends HotelEntity  {

    /**
     * PK here
     * FK in PlannedMaintenance and "can_do_maintenance" (no class, because between-table)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_type_id", nullable = false)
    private int m_type_id;

    @Column(name = "maintenance_type", length = 40, nullable = false)
    private String maintenance_type;

    /**
     * Many MaintenanceTypes can be done by many Jobs
     */
    @ManyToMany
    @JoinTable(name = "can_do_maintenance", joinColumns = {@JoinColumn(name = "m_type_id")}, inverseJoinColumns = {@JoinColumn(name = "job_id")})
    private Set<Job> jobs = new HashSet<>();

    /**
     * One MaintenanceType can show up in many different PlannedMaintenaces
     */
    @OneToMany(mappedBy = "maintenanceType", cascade = CascadeType.REMOVE)
    private List<PlannedMaintenance> plannedMaintenances = new ArrayList<>();

    public MaintenanceType() {

    }

    public MaintenanceType(int m_type_id, String maintenance_type) {
        this.m_type_id = m_type_id;
        this.maintenance_type = maintenance_type;
    }

    public int getM_type_id() {
        return m_type_id;
    }



    public String getMaintenance_type() {
        return maintenance_type;
    }

    public void setMaintenance_type(String maintenance_type) {
        this.maintenance_type = maintenance_type;
    }

    /**
     * Create-Method.
     * Calls helper-method, that prompts user.
     * @return (MaintenanceType)
     */
    @Override
    public HotelEntity createFromUserInput() {
        MaintenanceType entity = new MaintenanceType();

        //m_type_id = automatisch
        //Bezeichnung
        changeType(entity);

        return entity;
    }

    private static void changeType(MaintenanceType entity) {
        String i1 = "Please enter the Name/Description of the Maintenance";
        entity.setMaintenance_type(parseStringFixedLengthFromUser(i1, e1, 1, 40));
    }

    /**
     * Update-Methos.
     * User gets prompted by menu, what to update.
     * @return (MaintenanceType)
     */
    public HotelEntity updateFromUserInput() {
        // Select from index
        ColorHelper.printBlue("Please select the " + this.getClass().getSimpleName() +" to update:");
        MaintenanceType entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - Maintenance Type Name");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x","X" -> {
                    return entity;
                }
                case "1" ->  changeType(entity);
                default ->  ColorHelper.printRed(e1);
            }

        }
    }

    @Override
    public String toString() {
        return "[" +
                "m_type_id : " + m_type_id +
                ", maintenance_type : '" + maintenance_type + '\'' +
                "]";
    }
}
