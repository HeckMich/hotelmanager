package at.fhburgenland.entities;

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
        // TODO implement createFromUserInput in at.fhburgenland.entities.Invoice
        return null;
    }
}
