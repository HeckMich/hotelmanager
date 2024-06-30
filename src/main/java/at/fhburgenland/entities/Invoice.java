package at.fhburgenland.entities;
import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import at.fhburgenland.helpers.EMFSingleton;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * class for entity "invoice"
 */
@Entity(name = "invoice")
@Table(name = "invoice")
public class Invoice extends HotelEntity  {

    /**
     * PK here
     * (FK nowhere)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false, insertable = false, updatable = false)
    private int invoice_id;

    /**
     * FK here
     * PK in Guest
     */
    @Column(name = "guest_id", nullable = false, insertable = false, updatable = false)
    private int guest_id;

    /**
     * FK here
     * PK in Reservation
     */
    @Column(name = "reservation_id", nullable = false, insertable = false, updatable = false)
    private int reservation_id;

    @Column(name = "sum", nullable = false)
    private BigDecimal sum;

    /**
     * Many Invoices can be connected to one Guest
     */
    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    /**
     * Many invoices can be connected to one Reservation
     */
    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    public Reservation getReservation() {
        return reservation;
    }

    public Invoice() {

    }

    public Invoice(int guest_id, int reservation_id, BigDecimal sum) {
        this.guest_id = guest_id;
        this.reservation_id = reservation_id;
        this.sum = sum;
    }
    public Invoice(Guest guest, Reservation reservation, BigDecimal sum) {
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

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    private void setGuest(Guest guest) {
        this.guest = guest;
    }

    private void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    /**
     * Create-Method.
     * Ivoice is created by choosing Reservation.
     * Sum is calculated automatically by calling helper-method
     * @return (Invoice)
     */
    @Override
    public HotelEntity createFromUserInput() {
        Invoice entity = new Invoice();

        //InvoiceID = automatic

        //ReservationID
        ColorHelper.printBlue("Please choose a reservation to create the invoice for. Only reservations which do not yet have an Invoice associated are available.");
        if(!changeReservation(entity)) {
            return null;
        }

        //GuestId = automatic
        ColorHelper.printGreen("The correct Guest for the reservation was selected automatically!");
        entity.setGuest(entity.reservation.getGuest());
        entity.setGuest_id(entity.reservation.getGuest_id());

        //Sum = automatic
        BigDecimal sumCosts = calculateSumCosts(entity);
        entity.setSum(sumCosts);
        ColorHelper.printGreen("The invoice sum was automatically calculated to be: " + sumCosts);

        return entity;
    }

    private static boolean changeReservation(Invoice entity) {
        List<Reservation> reservationsWithoutInvoice = getReservationsWithoutInvoice();
        if (reservationsWithoutInvoice == null || reservationsWithoutInvoice.isEmpty()) {
            ColorHelper.printRed("No reservations available for selection! Canceling process.");
            return false;
        }
        Reservation reservation = HotelEntityHandler.selectEntityFromList(reservationsWithoutInvoice);
        entity.setReservation(reservation);
        entity.setReservation_id(reservation.getReservation_id());
        return true;
    }

    /**
     * Returns List of Reservations that don't have a Invoice yet.
     * @return (List<Reservation>)
     */
    private static List<Reservation> getReservationsWithoutInvoice() {
        EntityManager em = EMFSingleton.getEntityManager();
        try {
            String query = "SELECT a FROM reservation a " +
                    "WHERE NOT EXISTS (" +
                    "SELECT b FROM invoice b " +
                    "WHERE b.reservation = a)";
            TypedQuery<Reservation> tq = em.createQuery(query, Reservation.class);
            return tq.getResultList();
        } catch (Exception ex) {
            System.err.println("ERROR in getReservationsWithoutInvoice: " + ex.getMessage());
            throw ex;
        } finally {
            em.close();
        }
    }


    /**
     * Update-Method.
     * prompts user with menu, what to update.
     * By choosing 1 - a new invoice will be created to matching Reservation.
     * @return (Invoice)
     */
    public HotelEntity updateFromUserInput() {
        // Select from index
        ColorHelper.printBlue("Please select the " + this.getClass().getSimpleName() +" to update:");
        Invoice entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change? (Guest and Sum are determined automatically)");
            ColorHelper.printYellow("1 - Reservation");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x","X" -> {
                    return entity;
                }
                case "1" ->  entity = (Invoice)entity.createFromUserInput();
                default ->  ColorHelper.printRed(e1);
            }
        }
    }

    /**
     * Calculates sum by getting room-prices and booked Services from Database.
     * @param entity (Invoice)
     * @return (BigDecimal)
     */
    private BigDecimal calculateSumCosts(Invoice entity) {
        EntityManager em = EMFSingleton.getEntityManager();
        try {
            Date startDate = entity.getReservation().getStart_date();
            Date endDate = entity.getReservation().getEnd_date();

            long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
            int diffInDays = (int)TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            int resID = entity.getReservation().getReservation_id();

            String sql = "\t\tSELECT\n" +
                         "\tCOALESCE(SUM(D.cost), 0) + COALESCE(SUM(E.cost), 0) * "+ diffInDays +" expenses\n" +
                         "FROM\n" +
                         "\treservation A\n" +
                         "\tFULL OUTER JOIN booked_service C ON A.reservation_id = C.reservation_id\n" +
                         "\tFULL JOIN service_type D ON D.service_id = C.service_id\n" +
                         "\tJOIN room E ON A.room_nr = E.room_nr\n" +
                         "WHERE\n" +
                         "\tA.reservation_id = :resID\n" +
                         "GROUP BY\n" +
                         "\tA.guest_id,\n" +
                         "\tA.reservation_id";
            Query query = em.createQuery(sql);
            query.setParameter("resID", resID);

            List<Object> results = query.getResultList();
            if (!results.isEmpty()) {
                return (BigDecimal) results.get(0);
            }
            return BigDecimal.ZERO;
        } catch (Exception x) {
            return BigDecimal.ZERO;
        } finally {
            em.close();
        }
    }


    @Override
    public String toString() {
        return "[" +
                "invoice_id : " + invoice_id +
                ", guest_id : " + (guest == null ? guest_id : "[GuestID: " + guest.getGuest_id()+", First Name: " + guest.getFirst_name() + ", Last Name:  " + guest.getLast_name() + "]") +
                ", reservation_id : " + (reservation == null ?  reservation_id : "[ReservationID: " + reservation.getReservation_id()+", RoomNr: " + reservation.getRoom_nr() + "]") +
                ", sum: " + sum +
                "]";
    }
}
