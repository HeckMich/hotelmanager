package at.fhburgenland.entities;

import jakarta.persistence.*;

@Entity(name = "booked_service")
@Table(name = "booked_service")
public class BookedService {

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

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
}
