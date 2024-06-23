package at.fhburgenland.entities;
import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.ColorHelper;
import at.fhburgenland.helpers.EMFSingleton;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private BigDecimal sum;

    @ManyToOne
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

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

    @Override
    public HotelEntity createFromUserInput() {
        Invoice entity = new Invoice();

        //InvoiceID = automatic

        //ReservationID
        ColorHelper.printBlue("Please choose a reservation to create the invoice for.");
        changeReservation(entity);

        //GuestId = automatic
        ColorHelper.printGreen("The correct Guest for the reservation was selected automatically!");
        entity.setGuest_id(entity.reservation.getGuest_id());

        //Sum [maybe late über DB-query]
        changeSum(entity);

//        ColorHelper.printGreen("The sum is calculated automatically!");
//        double sumCosts = calculateSumCosts(entity);
//        entity.setSum(BigDecimal.valueOf(sumCosts));

        return entity;
    }

    private static void changeSum(Invoice entity) {
        String i1 = "Please enter the the Sum the Guest has to pay";
        String e1 = "Invalid input!";
        entity.setSum(parseBigDecimalFromUser(i1,e1));
    }

    private static void changeReservation(Invoice entity) {
        Reservation reservation = HotelEntityHandler.selectEntityFromFullList(Reservation.class);
        entity.setReservation(reservation);
    }

    private static void changeGuest(Invoice entity) {
        Guest guest = HotelEntityHandler.selectEntityFromFullList(Guest.class);
        entity.setGuest(guest);
    }

    public HotelEntity updateFromUserInput() {
        // Select from index
        ColorHelper.printBlue("Please select the " + this.getClass().getSimpleName() +" to update:");
        Invoice entity = HotelEntityHandler.selectEntityFromFullList(this.getClass());
        // -> Query user which attribute they want to change
        while (true) {
            ColorHelper.printBlue("What do you want to change?");
            ColorHelper.printYellow("1 - Guest");
            ColorHelper.printYellow("2 - Reservation");
            ColorHelper.printYellow("3 - Sum");
            ColorHelper.printYellow("X - Finish");
            String line = scanner.nextLine();
            switch (line) {
                case "x","X" -> {
                    return entity;
                }
                case "1" ->  changeGuest(entity);
                case "2" ->  changeReservation(entity);
                case "3" ->  changeSum(entity);
                default ->  ColorHelper.printRed(e1);
            }

        }
    }

    private double calculateSumCosts(Invoice entity) {
        EntityManager em = EMFSingleton.getEntityManager();
        try {
            Date startDate = HotelEntityHandler.read(Reservation.class,entity.getReservation_id()).getStart_date();
            Date endDate = HotelEntityHandler.read(Reservation.class,entity.getReservation_id()).getEnd_date();

            long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            String sql = "\t\tSELECT\n" +
                    "\tCOALESCE(SUM(D.cost), 0) + COALESCE(SUM(E.cost), 0) * "+ diffInDays +" expenses\n" +
                    "FROM\n" +
                    "\tRESERVATION A\n" +
                    "\tfull outer JOIN booked_service C ON A.reservation_id = C.reservation_id\n" +
                    "\tfull JOIN SERVICE_TYPE D ON D.service_id = C.service_id\n" +
                    "\tJOIN ROOM E ON A.room_nr = E.room_nr\n" +
                    "WHERE\n" +
                    "\tA.reservation_id = 9\n" +
                    "GROUP BY\n" +
                    "\tA.guest_id,\n" +
                    "\tA.reservation_id";
            Query query = em.createQuery(sql);

            List<Object[]> results = query.getResultList();
            if (!results.isEmpty()) {
                return 0;
            }
            return 0;
        } catch (Exception x) {
            return 0;
        } finally {
            em.close();
        }
    }


    @Override
    public String toString() {
        return "[" +
                "invoice_id : " + invoice_id +
                ", guest_id : " + guest_id +
                ", reservation_id : " + reservation_id +
                ", sum : " + sum +
                "]";
    }
}
