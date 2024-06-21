package at.fhburgenland.entities;

import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "planned_maintenance")
@Table(name = "planned_maintenance")
public class PlannedMaintenance extends HotelEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maint_id", nullable = false)
    private int maint_id;

    @Column(name = "m_type_id", nullable = false, insertable = false, updatable = false)
    private int m_type_id;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date start_date;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date end_date;

    @Column(name = "room_nr", nullable = false, updatable = false, insertable = false)
    private int room_nr;

    @Column(name = "employee_id", nullable = false, updatable = false, insertable = false)
    private int employee_id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "m_type_id")
    private MaintenanceType maintenanceType;

    @ManyToOne
    @JoinColumn(name = "room_nr")
    private Room room;

    public PlannedMaintenance() {

    }


    public PlannedMaintenance(int maint_id, int m_type_id, Date start_date, Date end_date, int room_nr, int employee_id) {
        this.maint_id = maint_id;
        this.m_type_id = m_type_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.room_nr = room_nr;
        this.employee_id = employee_id;
    }

    public int getMaint_id() {
        return maint_id;
    }



    public int getM_type_id() {
        return m_type_id;
    }

    public void setM_type_id(int m_type_id) {
        this.m_type_id = m_type_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getRoom_nr() {
        return room_nr;
    }

    public void setRoom_nr(int room_nr) {
        this.room_nr = room_nr;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setMaintenanceType(MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "PlannedMaintenance{" +
                "maint_id=" + maint_id +
                ", m_type_id=" + m_type_id +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", room_nr=" + room_nr +
                ", employee_id=" + employee_id +
                '}';
    }

    public HotelEntity createFromUserInput() {
        PlannedMaintenance entity = new PlannedMaintenance();

        // m-Type
        ColorHelper.printBlue("Select a Maintenance type to use.");
        MaintenanceType mt = HotelEntityHandler.selectEntityFromFullList(MaintenanceType.class);
        entity.setMaintenanceType(mt);
        // Room NR
        Room room = HotelEntityHandler.selectEntityFromFullList(Room.class);
        entity.setRoom(room);
        // Employee
        Employee employee = HotelEntityHandler.selectEntityFromFullList(Employee.class);
        entity.setEmployee(employee);
        // Start date
        String i1 = "Please enter the Start Date in the format dd.MM.yyy like 18.03.2024";
        String e1 = "Invalid input!";
        entity.setStart_date(parseDateFromUser(i1,e1));
        // End date
        String i2 = "Please enter the End Date in the format dd.MM.yyy like 18.03.2024";
        entity.setEnd_date(parseDateFromUser(i2,e1));

        return entity;
    }
}
