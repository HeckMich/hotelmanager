package at.fhburgenland.entities;

import jakarta.persistence.*;

@Entity(name = "job")
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id", nullable = false)
    private int job_id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Job{" +
                "job_id=" + job_id +
                ", name='" + name + '\'' +
                '}';
    }
}
