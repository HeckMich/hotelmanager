package at.fhburgenland.entities;

import at.fhburgenland.helpers.ColorHelper;
import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.ui.QueryMenu;
import jakarta.persistence.*;

import java.security.Provider;
import java.util.List;

/**
 * Class for Entity "booked_service"
 */
@Entity(name = "booked_service")
@Table(name = "booked_service")
public class BookedService extends HotelEntity {

    /**
     * PK of BookedService
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booked_service_id", updatable = false, nullable = false)
    private int bookedserviceid;


    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    /**
     * FK here
     * PK in Reservation
     */
    @Column(name = "reservation_id", insertable = false, updatable = false)
    private int reservation_id;

    /**
     * FK here
     * PK in Service
     */
    @Column(name = "service_id", insertable = false, updatable = false)
    private int service_id;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceType serviceType;


    /**
     * FK here
     * PK in Employee
     */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    public BookedService() {

    }
    public BookedService(Reservation reservation, ServiceType serviceType, Employee employee) {
        this.reservation = reservation;
        this.serviceType = serviceType;
        this.employee = employee;
    }

    public Reservation getReservation() {
        return reservation;
    }

    /**
     * sets Reservation by searching first for an existing Reservation
     * if no matching Reservation is found, Error will be thrown
     * @param reservation_id
     */
    public void setReservation(int reservation_id) {
        EntityManager EM = EMFSingleton.getEntityManager();
        Reservation reservation1 = EM.find(Reservation.class, reservation_id);
        if (reservation1 != null) {
            this.reservation = reservation1;
        } else {
            throw new IllegalArgumentException("No reservation found with id: " + reservation_id);
        }
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    /**
     * sets ServiceType by searching first for an existing ServiceType
     *  if no matching ServiceType is found, Error will be thrown
     * @param service_id
     */
    public void setServiceType(int service_id) {
        EntityManager EM = EMFSingleton.getEntityManager();
        ServiceType serviceType1 = EM.find(ServiceType.class, service_id);
        if (serviceType1 != null) {
            this.serviceType = serviceType1;
        } else {
            throw new IllegalArgumentException("No serviceType found with id: " + service_id);
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * sets Eployee by searching first for an existing Employee
     * if no matching Employee is found, Error will be thrown
     * @param employee_id
     */
    public void setEmployee(int employee_id) {
        EntityManager EM = EMFSingleton.getEntityManager();
        Employee employee1 = EM.find(Employee.class, employee_id);
        if (employee1 != null) {
            this.employee = employee1;
        } else {
            throw new IllegalArgumentException("No employee found with id: " + employee_id);
        }
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * new BookedService is created by prompting the user.
     * For this: other methods are called (see below)
     * @return (BookedService)
     */
    @Override
    public HotelEntity createFromUserInput() {
        BookedService entity = new BookedService();
        //Reservation ID
        changeReservationID(entity);
        changeServiceTypeAndEmployee(entity);
        return entity;
    }

    private static void changeServiceTypeAndEmployee(BookedService entity) {
        while (true) {
            //Service ID
            ServiceType stype = changeServiceType(entity);
            //Employe ID
            Employee e = getEmployeeRestricted(entity,stype);
            if (e == null) {
                ColorHelper.printRed("Please choose a different service.");
            } else {
                entity.setEmployee(e);
                break;
            }
        }
    }

    private static ServiceType changeServiceType(BookedService entity) {
        System.out.println("Choose ServiceId from List");
        ServiceType serviceType = HotelEntityHandler.selectEntityFromFullList(ServiceType.class);
        entity.setServiceType(serviceType);
        return serviceType;
    }

    private static void changeReservationID(BookedService entity) {
        System.out.println("Choose a Reservation ID from List");
        Reservation reservation = HotelEntityHandler.selectEntityFromFullList(Reservation.class);
        entity.setReservation(reservation);
    }

    /**
     * shows List of qualified and available employees
     * @param entity
     * @param serviceType
     * @return (Employee)
     */
    private static Employee getEmployeeRestricted(BookedService entity, ServiceType serviceType) {
        System.out.println("Please choose which qualified employee should perform the service: ");
        List<Employee> employeeOptions = QueryMenu.getEmployeesForServiceType(serviceType);
        if (employeeOptions == null || employeeOptions.isEmpty()) {
            ColorHelper.printRed("No employees available!");
            return null;
        }
        Employee employee = HotelEntityHandler.selectEntityFromList(employeeOptions);
        return employee;
    }


    /**
     * Creates a BookedService from user input in a manner suitable for the Reservation process (query 3)
     * @param reservation
     * @return (BookedService)
     */
    public static BookedService createFromUserInputForReservationProcess(Reservation reservation) {
        BookedService entity = new BookedService();
        //Reservation
        entity.setReservation(reservation);
        //Service
        System.out.println("Please choose which service to add to the reservation:");
        ServiceType serviceType = HotelEntityHandler.selectEntityFromFullList(ServiceType.class);
        entity.setServiceType(serviceType);
        //Employe
        System.out.println("Please choose which qualified employee should perform the service: ");
        List<Employee> employeeOptions = QueryMenu.getEmployeesForServiceType(serviceType);
        if (employeeOptions == null || employeeOptions.isEmpty()) {
            ColorHelper.printRed("No employees available!");
            return null;
        }
        Employee employee = HotelEntityHandler.selectEntityFromList(employeeOptions);
        entity.setEmployee(employee);
        return entity;
    }

    @Override
    public HotelEntity updateFromUserInput() {
        // Select Room from index
        ColorHelper.printBlue("Please select the booked service to update:");
        BookedService entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - Service Type and Employee");
            ColorHelper.printYellow("2 - ReservationID");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x","X" -> {
                    return entity;
                }
                case "1" ->  changeServiceTypeAndEmployee(entity);
                case "2" ->  changeReservationID(entity);
                default ->  ColorHelper.printRed(e1);
            }
        }
    }

    @Override
    public String toString() {
        return "[" +
                "ID: " + bookedserviceid +
                ", reservation : " + (reservation == null ? reservation_id : "[ ID: "+reservation.getReservation_id()) + ", RoomNr: " + reservation.getRoom_nr() + "]" +
                ", serviceType : " + (serviceType == null ? service_id : "[ ID: " + serviceType.getService_id() + ", Type: " + serviceType.getName() + "]") +
                ", employee : " + employee +
                "]";
    }
}
