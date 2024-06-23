package at.fhburgenland.entities;

import at.fhburgenland.helpers.ColorHelper;
import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.ui.QueryMenu;
import jakarta.persistence.*;

import java.security.Provider;
import java.util.List;

@Entity(name = "booked_service")
@Table(name = "booked_service")
public class BookedService extends HotelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booked_service_id", updatable = false, nullable = false)
    private int bookedserviceid;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceType serviceType;

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

    @Override
    public HotelEntity createFromUserInput() {
        BookedService entity = new BookedService();


        //Service ID
        changeServiceType(entity);
        //Employe ID
        changeEmployee(entity);
        //Reservation ID
        changeReservationID(entity);


        return entity;
    }

    private static void changeServiceType(BookedService entity) {
        System.out.println("Choose ServiceId from List");
        ServiceType serviceType = HotelEntityHandler.selectEntityFromFullList(ServiceType.class);
        entity.setServiceType(serviceType);
    }

    private static void changeReservationID(BookedService entity) {
        System.out.println("Choose a Reservation ID from List");
        Reservation reservation = HotelEntityHandler.selectEntityFromFullList(Reservation.class);
        entity.setReservation(reservation);
    }

    private static void changeEmployee(BookedService entity) {
        System.out.println("Here is a List of Employees: ");
        Employee employee = HotelEntityHandler.selectEntityFromFullList(Employee.class);
        entity.setEmployee(employee);
    }


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
            ColorHelper.printYellow("1 - Employee");
            ColorHelper.printYellow("2 - ReservationID");
            ColorHelper.printYellow("3 - ServiceType");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x","X" -> {
                    return entity;
                }
                case "1" ->  changeEmployee(entity);
                case "2" ->  changeReservationID(entity);
                case "3" ->  changeServiceType(entity);
                default ->  ColorHelper.printRed(e1);
            }

        }
    }

    @Override
    public String toString() {
        return "[" +
                "bookedserviceid : " + bookedserviceid +
                ", reservationID : " + reservation.getReservation_id() +
                ", serviceTypeID : " + serviceType.getService_id() +
                ", employeeID : " + employee.getEmployee_id() +
                "]";
    }
}
