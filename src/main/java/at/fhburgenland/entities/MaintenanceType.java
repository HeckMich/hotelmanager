package at.fhburgenland.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "maintenance_type")
@Table(name = "maintenance_type")
public class MaintenanceType extends HotelEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_type_id", nullable = false)
    private int m_type_id;

    @Column(name = "maintenance_type", length = 40, nullable = false)
    private String maintenance_type;

    @ManyToMany
    @JoinTable(name = "can_do_maintenance", joinColumns = {@JoinColumn(name = "m_type_id")}, inverseJoinColumns = {@JoinColumn(name = "job_id")})
    private Set<Job> jobs = new HashSet<>();

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

    @Override
    public HotelEntity createFromUserInput() {
        MaintenanceType entity = new MaintenanceType();

        //m_type_id = automatisch
        //Bezeichnung
        String i1 = "Please enter the Name/Description of the Maintenance";
        String e1 = "Invalid input!";
        entity.setMaintenance_type(parseStringFixedLengthFromUser(i1, e1, 1, 40));

        return entity;
    }

    @Override
    public String toString() {
        return "[" +
                "m_type_id : " + m_type_id +
                ", maintenance_type : '" + maintenance_type + '\'' +
                "]";
    }
}
