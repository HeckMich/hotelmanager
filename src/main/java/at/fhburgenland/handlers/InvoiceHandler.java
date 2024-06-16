package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.Guest;
import at.fhburgenland.entities.Invoice;
import at.fhburgenland.entities.Reservation;
import at.fhburgenland.entities.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;

public class InvoiceHandler {

    public static Invoice createInvoice(int guest_id, int reservation_id, double sum) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Invoice invoice;
        try {
            et = em.getTransaction();
            et.begin();
            Reservation reservation = em.find(Reservation.class, reservation_id);
            Guest guest = em.find(Guest.class, guest_id);
            if (reservation == null) throw new IllegalArgumentException("No reservation found with id: " + reservation_id);
            if (guest == null) throw new IllegalArgumentException("No guest found with guest_id: " + guest_id);
            invoice = new Invoice(guest_id, reservation_id, sum);
            em.persist(invoice);
            et.commit();
            System.out.println("Created Invoice: " + invoice);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in InvoiceHandler createInvoice: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return invoice;
    }

    public static boolean deleteInvoice(int invoice_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Invoice invoice = null;
        try {
            et = em.getTransaction();
            et.begin();
            invoice = em.find(Invoice.class, invoice_id);
            if (invoice == null)
                throw new IllegalArgumentException("The Invoice with id : " + invoice_id + " was not found in DB. Deletion canceled.");
            em.remove(invoice);
            et.commit();
            System.out.println("Deleted " + invoice);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in InvoiceHandler DeleteInvoice " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    public static Invoice updateInvoice(int invoice_id, int guest_id, int reservation_id, double sum) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Invoice invoice = null;
        try {
            et = em.getTransaction();
            et.begin();
            invoice = em.find(Invoice.class, invoice_id);
            if (invoice == null)
                throw new IllegalArgumentException("The Invoice with id : " + reservation_id + " was not found in DB. Update canceled.");
            invoice.setGuest_id(guest_id);
            invoice.setSum(sum);
            invoice.setReservation_id(reservation_id);
            em.persist(invoice);
            et.commit();
            System.out.println("Updated " + invoice);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in InvoiceHandler UpdateInvoice: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return invoice;
    }

    public static Invoice readInvoice(int invoice_id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        Invoice invoice = null;
        try {
            et = em.getTransaction();
            et.begin();
            invoice = em.find(Invoice.class, invoice_id);
            if (invoice == null)
                throw new IllegalArgumentException("The invoice with id : " + invoice_id + " was not found in DB.");
            return invoice;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in InvoiceHandler ReadInvoice: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static List<Invoice> readAllInvoice() {
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM invoice x";
        List<Invoice> list = null;
        try {
            TypedQuery<Invoice> tq = em.createQuery(query, Invoice.class);
            list = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }
}
