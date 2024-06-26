package at.fhburgenland.ui;

import at.fhburgenland.entities.*;
import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.*;

public class QueryMenu {
    private static final Scanner scanner = new Scanner(System.in);
    public static void queryOne() {
        String instructions = "Please enter the Start Date in the format dd.MM.yyy like 18.03.2024";
        String errorMessage = "Invalid Input!";
        Date startDate = HotelEntity.parseDateFromUser(instructions, errorMessage);

        String instructions2 = "Please enter the End Date in the format dd.MM.yyy like 18.03.2024";
        Date endDate = HotelEntity.parseDateFromUser(instructions2, errorMessage);

        List<PlannedMaintenance> list = findMaintenanceBetweenDates(startDate, endDate);
        ColorHelper.printBlue("The following Maintenances are schedueled in between " + startDate + " and " + endDate + ":");
        list.forEach(x -> ColorHelper.printGreen(x.toString()));
    }
    public static void queryTwo() {
        String instructions = "Please enter the Date to inspect in the format dd.MM.yyy like 18.03.2024";
        String errorMessage = "Invalid Input!";
        Date choosenDate = HotelEntity.parseDateFromUser(instructions, errorMessage);

        ColorHelper.printBlue("Analyzing reservations for date " + choosenDate + ":");
        printRoomStats(choosenDate);
    }

    public static void reservationProcess() {
        ColorHelper.printGreen("Starting Reservation Process!");
        //Check if a room is free for date-range
        //  N -> end
        //  Y -> return lis of rooms
        List<Date> dateList = fetchDatesFromUserInput();
        List<Room> availableRooms = fetchAvailableRoomsFromUserInput(dateList.get(0), dateList.get(1));
        if (availableRooms == null || availableRooms.isEmpty()) {
            ColorHelper.printRed("No room is available for the desired period! Canceling reservation process.");
            return;
        }
        ColorHelper.printBlue("Now select one of the available rooms!");
        Room selectedRoom = HotelEntityHandler.selectEntityFromList(availableRooms);
        ColorHelper.printGreen("The following room was selected: " + selectedRoom.toString());
        //Ask if preexisting guest?
        //  Y -> Choose from list
        //  N -> Query user for new guest
        Guest guest = fetchGuestFromUserInput();
        HotelEntityHandler.create(guest);
        ColorHelper.printGreen("The following guest was selected / registered: " + guest);
        //Book Event(s)  -> List
        ColorHelper.printBlue("Now optionally add events for the guest to attend:");
        HashSet<Event> eventHashSet = new HashSet<>();
        eventHashSet.addAll(fetchEventListFromUserInput(dateList.get(0), dateList.get(1)));
        Reservation reservation = new Reservation(guest, selectedRoom, dateList.get(0), dateList.get(1), eventHashSet);
        if (createReservationUserInput(reservation)) {
            ColorHelper.printGreen("Reservation confirmed: " + reservation);
            List<BookedService> bookedServices = fetchBookedServiceListFromUserInput(reservation);
            if (bookedServices != null && !bookedServices.isEmpty()) {
                ColorHelper.printGreen("Saving selected services:");
                bookedServices.forEach(x -> HotelEntityHandler.create(x));
            }
            ColorHelper.printGreen("Successfully finished reservation process!");
        } else {
            ColorHelper.printRed("Reservation canceled.");
        }
    }

    private static boolean createReservationUserInput(Reservation reservation) {
        while (true) {
            ColorHelper.printGreen("The following Reservation was prepared based on your input: " + reservation.toString());
            ColorHelper.printGreen("The following Events were booked for the reservation: ");
            HotelEntityHandler.printAsNeutralList(reservation.getEvents().stream().toList());
            ColorHelper.printBlue("Do you want to confirm this reservation and save it to the Hotel Management System? (Services will be booked after confirmation)");
            ColorHelper.printYellow("Y - Yes (Save reservation to database)");
            ColorHelper.printYellow("N - No (Cancel and return to menu)");
            String line = scanner.nextLine();
            switch (line) {
                case "Y", "y" -> {
                    return (HotelEntityHandler.create(reservation) != null);
                }
                case "N", "n" -> {
                    return false;
                }
                default -> ColorHelper.printRed("Invalid input!");
            }
        }
    }

    private static List<BookedService> fetchBookedServiceListFromUserInput(Reservation reservation) {
        List<BookedService> bookedServices = new ArrayList<>();
        while (true) {
            ColorHelper.printBlue("Do you want to book an / another Service for this reservation?");
            ColorHelper.printYellow("Y - Yes (Show list of available services)");
            ColorHelper.printYellow("N - No (Continue with reservation process)");
            String line = scanner.nextLine();
            switch (line) {
                case "Y", "y" -> {
                    BookedService b = BookedService.createFromUserInputForReservationProcess(reservation);
                    if (b == null) {
                        ColorHelper.printRed("Service booking aborted! Restart process or continue.");

                    } else {
                        bookedServices.add(b);
                        ColorHelper.printGreen("Added the following Service to reservation: " + b.getServiceType());
                    }
                }
                case "N", "n" -> {
                    return bookedServices;
                }
                default -> ColorHelper.printRed("Invalid input!");
            }
        }
    }

    private static List<Event> fetchEventListFromUserInput(Date startDate, Date endDate) {
        List<Event> events = new ArrayList<>();
        while (true) {
            ColorHelper.printBlue("Do you want to add an / another event to the reservation?");
            ColorHelper.printYellow("Y - Yes (Show list of available events during reservation)");
            ColorHelper.printYellow("N - No (Continue with reservation process)");
            String line = scanner.nextLine();
            switch (line) {
                case "Y", "y" -> {
                    List<Event> availableEvents = Event.getEventsForReservation(startDate, endDate);
                    if (availableEvents == null || availableEvents.isEmpty()) {
                        ColorHelper.printRed("No events available on reserved dates. Continuing with booking.");
                        return events;
                    } else {
                        Event e = HotelEntityHandler.selectEntityFromList(availableEvents);
                        events.add(e);
                        ColorHelper.printGreen("Added the following event to reservation: " + e);
                    }
                }
                case "N", "n" -> {
                    return events;
                }
                default -> ColorHelper.printRed("Invalid input!");
            }
        }
    }

    private static Guest fetchGuestFromUserInput() {
        while (true) {
            ColorHelper.printBlue("Is the Guest already registered in the Hotel Management System?");
            ColorHelper.printYellow("Y - Yes (Show list of registered guests)");
            ColorHelper.printYellow("N - No (Register new guest)");
            String line = scanner.nextLine();
            switch (line) {
                case "Y", "y" -> {
                    return HotelEntityHandler.selectEntityFromFullList(Guest.class);
                }
                case "N", "n" -> {
                    Guest g = new Guest();
                    return (Guest)g.createFromUserInput();
                }
                default -> ColorHelper.printRed("Invalid input!");
            }
        }
    }

    private static List<Date> fetchDatesFromUserInput() {
        String instructions = "Please enter the desired Start Date of the reservation in the format dd.MM.yyyy like 18.03.2024";
        String errorMessage = "Invalid Input!";
        String instructions2 = "Please enter the desired End Date of the reservation in the format dd.MM.yyyy like 18.03.2024";
        Date startDate;
        Date endDate;
        while (true) {
            startDate = HotelEntity.parseDateFromUser(instructions, errorMessage);
            endDate = HotelEntity.parseDateFromUser(instructions2, errorMessage);
            if (!startDate.after(endDate)) {
                List<Date> list = new ArrayList<>();
                list.add(startDate);
                list.add(endDate);
                return list;
            }
            ColorHelper.printRed("The Start Date needs to be before the End Date or on the same day! Try again!");
        }
    }

    private static List<Room> fetchAvailableRoomsFromUserInput(Date startDate, Date endDate) {
        EntityManager em = EMFSingleton.getEntityManager();
        try {
            String query = "SELECT a FROM room a " +
                    "WHERE NOT EXISTS (" +
                    "SELECT 1 FROM reservation b " +
                    "WHERE b.room = a AND " +
                    "b.start_date <= :endDate AND " +
                    "b.end_date >= :startDate" +
                    ") ORDER BY a.room_nr";
            TypedQuery<Room> tq = em.createQuery(query, Room.class);
            tq.setParameter("startDate", startDate);
            tq.setParameter("endDate", endDate);
            return tq.getResultList();
        } catch (Exception ex) {
            System.err.println("ERROR in QueryMenu fetchAvailableRoomsFromUserQuery: " + ex.getMessage());
            throw ex;
        } finally {
            em.close();
        }

    }

    private static void printRoomStats(Date date) {
        EntityManager em = EMFSingleton.getEntityManager();
        try {
            String sql =
                    "SELECT\n" +
                            "\tCOUNT(*) NUMBER_BOOKED_ROOMS,\n" +
                            "\t(\n" +
                            "\t\tSELECT\n" +
                            "\t\t\tCOUNT(*)\n" +
                            "\t\tFROM\n" +
                            "\t\t\troom\n" +
                            "\t) - COUNT(*) NUMBER_EMPTY_ROOMS,\n" +
                            "\tCOALESCE(SUM(cost), 0) EXPECTED_EARNINGS,\n" +
                            "\t(\n" +
                            "\t\tSELECT\n" +
                            "\t\t\tSUM(cost)\n" +
                            "\t\tFROM\n" +
                            "\t\t\troom\n" +
                            "\t) - COALESCE(SUM(cost), 0) LOST_EARNINGS\n" +
                            "FROM\n" +
                            "\treservation A\n" +
                            "\tfull outer JOIN room B ON A.room_nr = B.room_nr\n" +
                            "WHERE\n" +
                            "\t:targetDate BETWEEN start_date AND end_date";

            Query query = em.createQuery(sql);
            query.setParameter("targetDate", date);

            List<Object[]> results = query.getResultList();
            if (!results.isEmpty()) {
                Object[] result = results.get(0);
                ColorHelper.printGreen("Number of booked rooms: " + result[0]);
                ColorHelper.printGreen("Number of empty rooms: " + result[1]);
                ColorHelper.printGreen("Expected earnings: " + result[2]);
                ColorHelper.printGreen("Lost earnings: " + result[3]);
            } else {
                ColorHelper.printRed("No data found for the given date.");
            }
        } finally {
            em.close();
        }
    }

    private static List<PlannedMaintenance> findMaintenanceBetweenDates(Date startDate, Date endDate) {
        EntityManager em = EMFSingleton.getEntityManager();
        try {
            String query = "SELECT pm FROM planned_maintenance pm WHERE pm.start_date <= :endDate AND pm.end_date >= :startDate";
            TypedQuery<PlannedMaintenance> tq = em.createQuery(query, PlannedMaintenance.class);
            tq.setParameter("startDate", startDate);
            tq.setParameter("endDate", endDate);
            return tq.getResultList();
        } catch (Exception ex) {
            System.err.println("ERROR in QueryMenu findMaintenanceBetweenDates: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static List<Employee> getEmployeesForServiceType(ServiceType serviceType) {
        EntityManager em = EMFSingleton.getEntityManager();
        int serviceTypeId = serviceType.getService_id();

        String jpql = "SELECT a FROM employee a " +
                "WHERE EXISTS (" +
                "SELECT b FROM job b " +
                "JOIN b.serviceTypes c " +
                "WHERE c.id = :serviceTypeId AND a.job = b)";

        TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
        query.setParameter("serviceTypeId", serviceTypeId);

        return query.getResultList();
    }
}
