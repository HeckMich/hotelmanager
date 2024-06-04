package at.fhburgenland.entities;

import jakarta.persistence.*;

@Entity(name = "maintenance_type")
@Table(name = "maintenance_type")
public class MaintenanceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "m_type_id", nullable = false)
    private int m_type_id;

    @Column(name = "maintenance_type", length = 40, nullable = false)
    private String maintenance_type;

    public MaintenanceType() {

    }

    public MaintenanceType(int m_type_id, String maintenance_type) {
        this.m_type_id = m_type_id;
        this.maintenance_type = maintenance_type;
    }

    public int getM_type_id() {
        return m_type_id;
    }

    public void setM_type_id(int m_type_id) {
        this.m_type_id = m_type_id;
    }

    public String getMaintenance_type() {
        return maintenance_type;
    }

    public void setMaintenance_type(String maintenance_type) {
        this.maintenance_type = maintenance_type;
    }

    @Override
    public String toString() {
        return "MaintenanceType{" +
                "m_type_id=" + m_type_id +
                ", maintenance_type='" + maintenance_type + '\'' +
                '}';
    }
}
