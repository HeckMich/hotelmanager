package at.fhburgenland.entities;

import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.*;

import java.util.Date;

/**
 * class for entity "planned_maintenance"
 */
@Entity(name = "planned_maintenance")
@Table(name = "planned_maintenance")
public class PlannedMaintenance extends HotelEntity {

    /**
     * PK here
     * [FK nowhere]
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maint_id", nullable = false)
    private int maint_id;

    /**
     * FK here
     * PK in MaintenanceType
     */
    @Column(name = "m_type_id", nullable = false, insertable = false, updatable = false)
    private int m_type_id;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date start_date;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date end_date;

    /**
     * FK here
     * PK in Room
     */
    @Column(name = "room_nr", nullable = false, updatable = false, insertable = false)
    private int room_nr;


    /**
     * FK here
     * PK in Employee
     */
    @Column(name = "employee_id", nullable = false, updatable = false, insertable = false)
    private int employee_id;

    /**
     * Many PlannedMaintenances can be connected to one Employee
     */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;


    /**
     * Many PlannedMaintenances can be from one MaintenanceType
     */
    @ManyToOne
    @JoinColumn(name = "m_type_id")
    private MaintenanceType maintenanceType;


    /**
     * Many PlannedMaintenaces can be done in one Room
     */
    @ManyToOne
    @JoinColumn(name = "room_nr")
    private Room room;

    public PlannedMaintenance() {

    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }


    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
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

    /**
     * Crate-Method.
     * Calls helper-methods, that prompt user.
     *
     * @return (PlannedMaintenance)
     */
    public HotelEntity createFromUserInput() {
        PlannedMaintenance entity = new PlannedMaintenance();

        // m-Type
        changeMType(entity);
        // Room NR
        changeRoomNr(entity);
        // Employee
        changeEmployee(entity);
        // Dates
        changeDates(entity);

        return entity;
    }

    private static void changeDates(PlannedMaintenance entity) {
        // Start date
        String i1 = "Please enter the Start Date in the format dd.MM.yyy like 18.03.2024";
        entity.setStart_date(parseDateFromUser(i1, e1));
        // End date
        String i2 = "Please enter the End Date in the format dd.MM.yyy like 18.03.2024";
        entity.setEnd_date(parseDateFromUser(i2, e1));
    }

    private static void changeEmployee(PlannedMaintenance entity) {
        Employee employee = HotelEntityHandler.selectEntityFromFullList(Employee.class);
        entity.setEmployee(employee);
    }

    private static void changeRoomNr(PlannedMaintenance entity) {
        Room room = HotelEntityHandler.selectEntityFromFullList(Room.class);
        entity.setRoom(room);
    }

    private static void changeMType(PlannedMaintenance entity) {
        ColorHelper.printBlue("Select a Maintenance type to use.");
        MaintenanceType mt = HotelEntityHandler.selectEntityFromFullList(MaintenanceType.class);
        entity.setMaintenanceType(mt);
    }

    /**
     * Update-Method.
     * User gets prompted with menu, what to update.
     * Calls helper-methods.
     *
     * @return
     */
    public HotelEntity updateFromUserInput() {
        // Select from index
        ColorHelper.printBlue("Please select the " + this.getClass().getSimpleName() + " to update:");
        PlannedMaintenance entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - Maintenance Type");
            ColorHelper.printYellow("2 - Room");
            ColorHelper.printYellow("3 - Employee");
            ColorHelper.printYellow("4 - Dates");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x", "X" -> {
                    return entity;
                }
                case "1" -> changeMType(entity);
                case "2" -> changeRoomNr(entity);
                case "3" -> changeEmployee(entity);
                case "4" -> changeDates(entity);
                default -> ColorHelper.printRed(e1);
            }

        }
    }

    @Override
    public String toString() {
        return "[" +
                "maint_id : " + maint_id +
                ", maintenance-type : " + (maintenanceType == null ? m_type_id : "[Maintenance Type: " + maintenanceType.getMaintenance_type() + ", ID: " + maintenanceType.getM_type_id() + "]") +
                ", start_date : " + start_date +
                ", end_date : " + end_date +
                ", room_nr : " + (room == null ? room_nr : "[" + room.getRoom_nr() + "]") +
                ", employee : " + (employee == null ? employee_id : "[First Name: " + employee.getFirst_name() + ", Last Name: " + employee.getLast_name() + ", ID: " + employee.getEmployee_id() + "]") +
                "]";
    }
}
