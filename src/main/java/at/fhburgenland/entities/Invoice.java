package at.fhburgenland.entities;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.*;

@Entity(name = "invoice")
@Table(name = "invoice")
public class Invoice extends HotelEntity  {

    //TODO: Should reservation_id be PK???

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false, insertable = false, updatable = false)
    private int invoice_id;

    @Column(name = "guest_id", nullable = false, insertable = false, updatable = false)
    private int guest_id;

    @Column(name = "reservation_id", nullable = false, insertable = false, updatable = false)
    private int reservation_id;

    @Column(name = "sum", nullable = false)
    private double sum;

    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    public Invoice() {

    }

    public Invoice(int guest_id, int reservation_id, double sum) {
        this.guest_id = guest_id;
        this.reservation_id = reservation_id;
        this.sum = sum;
    }
    public Invoice(Guest guest, Reservation reservation, double sum) {
        this.guest = guest;
        this.reservation = reservation;
        this.sum = sum;
    }

    public int getInvoice_id() {
        return invoice_id;
    }


    public int getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    private void setGuest(Guest guest) {
    }

    private void setReservation(Reservation reservation) {
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoice_id=" + invoice_id +
                ", guest_id=" + guest_id +
                ", reservation_id=" + reservation_id +
                ", sum=" + sum +
                '}';
    }

    @Override
    public HotelEntity createFromUserInput() {
        Invoice entity = new Invoice();

        //GuestId
        Guest guest = HotelEntityHandler.selectEntityFromList(Guest.class);
        entity.setGuest(guest);
        //InvoiceID = automatisch

        //ReservationID
        Reservation reservation = HotelEntityHandler.selectEntityFromList(Reservation.class);
        entity.setReservation(reservation);
        //Sum [maybe late über datenbankabfrage]
        String i1 = "Please enter the the Sum the Guest has to pay";
        String e1 = "Invalid input!";
        entity.setSum(parseIntFromUser(i1,e1));

        //ColorHelper.printGreen("The sum is calculated automatically!");
        //double sumCosts = calculateSumCosts(entity);
        //entity.setSum(sumCosts);

        return entity;
    }

    /*private double calculateSumCosts(Invoice entity) {
        EntityManager em = EMFSingleton.getEntityManager();
        try {
            String query = "SUM(D.COST) + SUM(E.COST) * (a.end_date::date - a.start_date::date)";
            TypedQuery<PlannedMaintenance> tq = em.createQuery(query, PlannedMaintenance.class);
            tq.setParameter("startDate", startDate);
            tq.setParameter("endDate", endDate);
            return tq.getResultList();
        } finally {
            em.close();
        }

    }*/


}
