package at.fhburgenland.entities;

import jakarta.persistence.*;

@Entity(name = "employee")
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private int employee_id;

    @Column(name = "job_id", nullable = false)
    private int job_id;

    @Column(name = "first_name", length = 20, nullable = false)
    private String first_name;

    @Column(name = "last_name", length = 30, nullable = false)
    private String last_name;

    public Employee() {

    }

    public Employee(int employee_id, int job_id, String first_name, String last_name) {
        this.employee_id = employee_id;
        this.job_id = job_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employee_id=" + employee_id +
                ", job_id=" + job_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}
