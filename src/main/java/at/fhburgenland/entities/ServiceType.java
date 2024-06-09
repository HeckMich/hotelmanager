package at.fhburgenland.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "service_type")
@Table(name = "service_type")
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id", nullable = false)
    private int service_id;


    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "cost", nullable = false)
    private double cost;

    @OneToMany(mappedBy = "serviceType")
    private List<BookedService> bookedServices  = new ArrayList<>();

    @ManyToMany(mappedBy = "serviceTypes")
    private Set<Job> jobs = new HashSet<>();

    public ServiceType() {

    }

    public ServiceType(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public List<BookedService> getBookedServices() {
        return bookedServices;
    }

    public void setBookedServices(List<BookedService> bookedServices) {
        this.bookedServices = bookedServices;
    }

    public int getService_id() {
        return service_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public void addJob(Job job) {
        this.jobs.add(job);
        job.getServiceTypes().add(this);
    }
    public void removeJob(Job job) {
        this.jobs.remove(job);
        job.getServiceTypes().remove(this);
    }

    @Override
    public String toString() {
        return "ServiceType{" +
                "service_id=" + service_id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
