package at.fhburgenland.entities;

import at.fhburgenland.EMFSingleton;
import jakarta.persistence.*;

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

    @Override
    public String toString() {
        return "BookedService{" +
                "bookedserviceid=" + bookedserviceid +
                ", reservation=" + reservation +
                ", serviceType=" + serviceType +
                ", employee=" + employee +
                '}';
    }

    @Override
    public HotelEntity createFromUserInput() {

        return null;
    }
}
