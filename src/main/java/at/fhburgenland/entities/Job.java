package at.fhburgenland.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "job")
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id", nullable = false)
    private int job_id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "can_do_service", joinColumns = {@JoinColumn(name = "job_id")}, inverseJoinColumns = {@JoinColumn(name = "service_id")})
    private Set<ServiceType> serviceTypes = new HashSet<>();

    @OneToMany(mappedBy = "job")
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany(mappedBy = "jobs")
    private Set<MaintenanceType> maintenanceTypes = new HashSet<>();

    public Job() {

    }

    public Job(int job_id, String name) {
        this.job_id = job_id;
        this.name = name;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public Set<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(Set<ServiceType> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addServiceType(ServiceType serviceType) {
        this.serviceTypes.add(serviceType);
        serviceType.getJobs().add(this);
    }
    public void removeServiceType(ServiceType serviceType) {
        this.serviceTypes.remove(serviceType);
        serviceType.getJobs().remove(this);
    }

    @Override
    public String toString() {
        return "Job{" +
                "job_id=" + job_id +
                ", name='" + name + '\'' +
                '}';
    }
}
