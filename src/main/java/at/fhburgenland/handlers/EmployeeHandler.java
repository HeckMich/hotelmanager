package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Employee;
import at.fhburgenland.entities.Guest;
import at.fhburgenland.entities.Invoice;
import at.fhburgenland.entities.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EmployeeHandler {

    public static Employee createEmployee(int job_id, String first_name, String last_name) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Employee employee;
        try {
            et = em.getTransaction();
            et.begin();
            employee = new Employee(job_id,first_name,last_name);
            em.persist(employee);
            et.commit();
            System.out.println("Created Employee: " + employee);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in EmployeeHandler createEmployee: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return employee;
    }

    public static boolean deleteEmployee(int employee_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Employee employee = null;
        try {
            et = em.getTransaction();
            et.begin();
            employee = em.find(Employee.class, employee_id);
            if (employee == null)
                throw new IllegalArgumentException("The Employee with id : " + employee_id + " was not found in DB. Deletion canceled.");
            em.remove(employee);
            et.commit();
            System.out.println("Deleted " + employee);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in EmployeeHandler DeleteEmployee " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    public static Employee updateEmployee(int employee_id, int job_id, String first_name, String last_name) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Employee employee = null;
        try {
            et = em.getTransaction();
            et.begin();
            employee = em.find(Employee.class, employee_id);
            if (employee == null)
                throw new IllegalArgumentException("The Employee with id : " + employee_id + " was not found in DB. Update canceled.");
            employee.setJob_id(job_id);
            employee.setFirst_name(first_name);
            employee.setLast_name(last_name);
            em.persist(employee);
            et.commit();
            System.out.println("Updated " + employee);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in EmployeeHandler UpdateEmployee: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return employee;
    }

    public static Employee readEmployee(int employee_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Employee employee = null;
        try {
            et = em.getTransaction();
            et.begin();
            employee = em.find(Employee.class, employee_id);
            if (employee == null)
                throw new IllegalArgumentException("The Employee with id : " + employee_id + " was not found in DB.");
            return employee;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in EmployeeHandler ReadEmployee: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static List<Employee> readAllEmployees() {
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM employee x";
        List<Employee> list = null;
        try {
            TypedQuery<Employee> tq = em.createQuery(query, Employee.class);
            list = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }
}
