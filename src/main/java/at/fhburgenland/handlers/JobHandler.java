package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Event;
import at.fhburgenland.entities.Guest;
import at.fhburgenland.entities.Job;
import at.fhburgenland.entities.Plz;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class JobHandler {
    public static Job createJob(int job_id, String name) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Job job;
        try {
            et = em.getTransaction();
            et.begin();

            job = new Job(job_id, name);
            em.persist(job);
            et.commit();
            System.out.println("Created job: " + job);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in JobHandler createJob: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return job;
    }

    public static boolean deleteJob(int job_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Job job = null;
        try {
            et = em.getTransaction();
            et.begin();
            job = em.find(Job.class, job_id);
            if (job == null) throw new IllegalArgumentException("The job with id : " + job_id + " was not found in DB. Deletion canceled." );
            em.remove(job);
            et.commit();
            System.out.println("Deleted " + job);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in JobHandler DeleteJob: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    public static Job updateJob(int job_id, String name) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Job job = null;
        try {
            et = em.getTransaction();
            et.begin();
            job = em.find(Job.class, job_id);
            if (job == null) throw new IllegalArgumentException("The job with id : " + job_id + " was not found in DB. Update canceled." );
            job.setName(name);

            em.persist(job);
            et.commit();
            System.out.println("Updated " + job);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in JobHandler UpdateJob: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return job;
    }

    public static Job readJob(int job_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Job job = null;
        try {
            et = em.getTransaction();
            et.begin();
            job = em.find(Job.class, job_id);
            if (job == null)
                throw new IllegalArgumentException("The job with id : " + job_id + " was not found in DB.");
            return job;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in JobHandler readJOb: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static List<Job> readAllJobs(){
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM event x";
        List<Job> list = null;
        try {
            TypedQuery<Job> tq = em.createQuery(query, Job.class);
            list = tq.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return list;

    }

}
