package at.fhburgenland.ui;

import at.fhburgenland.entities.*;
import at.fhburgenland.handlers.HotelEntityHandler;
import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.*;

/**
 * Contains the logic for the three User Queries defined in the assignment.
 * Queries are implemented as a combination of user input and SQL selects.
 */
public class QueryMenu {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Performs a query for Maintenances on a given date based on user input.
     * Displays a list of all Maintenances planned in the provided date range.
     */
    public static void queryOne() {
        //Parse start and end date from user
        List<Date> datelist = fetchDatesFromUserInput();
        Date startDate = datelist.get(0);
        Date endDate = datelist.get(1);
        //Fetch list of maintenances from DB
        List<PlannedMaintenance> list = findMaintenanceBetweenDates(startDate, endDate);
        //Print result to console
        ColorHelper.printBlue("The following Maintenances are scheduled in between " + startDate + " and " + endDate + ":");
        if (list.isEmpty()) ColorHelper.printRed("No maintenances scheduled in this time span.");
        list.forEach(x -> ColorHelper.printGreen(x.toString()));
    }

    /**
     * Performs a query analyzing the number of booked and empty rooms and the lost/earned costs.
     * Prints the results to console.
     */
    public static void queryTwo() {
        String instructions = "Please enter the Date to inspect in the format dd.MM.yyy like 18.03.2024";
        String errorMessage = "Invalid Input!";
        //Parse date to analyze from user
        Date choosenDate = HotelEntity.parseDateFromUser(instructions, errorMessage);
        ColorHelper.printBlue("Analyzing reservations for date " + choosenDate + ":");
        //Fetch stats from SQL and print to console
        printRoomStats(choosenDate);
    }

    /**
     * "QUERY 3"
     * Processes a new reservation by querying the user for all the necessary information.
     * Creates the reservation on DB if all information is valid.
     * Can also create new Guest and Plz in the process
     */
    public static void reservationProcess() {
        ColorHelper.printGreen("Starting Reservation Process!");
        //Parse reservation Dates from user
        List<Date> dateList = fetchDatesFromUserInput();
        //Fetch free rooms in date-range from DB
        List<Room> availableRooms = fetchAvailableRoomsFromUserInput(dateList.get(0), dateList.get(1));
        //Check if a room is free for date-range
        //  N -> end
        //  Y -> return lis of rooms
        if (availableRooms == null || availableRooms.isEmpty()) {
            ColorHelper.printRed("No room is available (not reserved or in maintenance) for the desired period. Canceling reservation process.");
            return;
        }
        //Let user select one of available rooms
        ColorHelper.printBlue("Now select one of the available rooms!");
        Room selectedRoom = HotelEntityHandler.selectEntityFromList(availableRooms);
        ColorHelper.printGreen("The following room was selected: " + selectedRoom.toString());
        // Parse guest to use from user input
        // -> optionally create new and persist on DB
        Guest guest = fetchGuestFromUserInput();
        ColorHelper.printGreen("The following guest was selected / registered: " + guest);
        //Book Event(s)
        // -> Get list from user
        ColorHelper.printBlue("Now optionally add events for the guest to attend:");
        List<Event> fetchedEvents = fetchEventListFromUserInput(dateList.get(0), dateList.get(1));
        // -> Add to reservation
        HashSet<Event> eventHashSet = new HashSet<>();
        eventHashSet.addAll(fetchedEvents);
        Reservation reservation = new Reservation(guest, selectedRoom, dateList.get(0), dateList.get(1), eventHashSet);
        //Persist reservation and events if user agrees
        if (createReservationUserInput(reservation)) {
            ColorHelper.printGreen("Reservation confirmed: " + reservation);
            //Fetch BookedServices from user and persist
            List<BookedService> bookedServices = fetchBookedServiceListFromUserInput(reservation);
            if (bookedServices != null && !bookedServices.isEmpty()) {
                ColorHelper.printGreen("Saving selected services:");
                bookedServices.forEach(HotelEntityHandler::create);
            }
            ColorHelper.printGreen("------------------------------------------");
            ColorHelper.printGreen("Successfully finished reservation process!");
        } else {
            ColorHelper.printRed("Reservation canceled.");
        }
    }

    /**
     * Persist passed reservation on DB if user agrees. Cancel otherwise
     *
     * @param reservation reservation to persist
     * @return true if user agreed and persist was successful, false otherwise
     */
    private static boolean createReservationUserInput(Reservation reservation) {
        while (true) {
            ColorHelper.printGreen("The following Reservation was prepared based on your input: " + reservation.toString());
            List<Event> events = reservation.getEvents().stream().toList();
            if (!events.isEmpty()) {
                ColorHelper.printGreen("The following Events were booked for the reservation: ");
                HotelEntityHandler.printAsNeutralList(reservation.getEvents().stream().toList());
            }
            ColorHelper.printBlue("Do you want to confirm this reservation and save it to the Hotel Management System? (Services will be booked after confirmation)");
            ColorHelper.printYellow("Y - Yes (Save reservation to database)");
            ColorHelper.printYellow("N - No (Cancel and return to menu)");
            String line = scanner.nextLine();
            switch (line) {
                case "Y", "y" -> {
                    return createReservationWithEvents(reservation); //Persist reservation and linked events
                }
                case "N", "n" -> {
                    return false;
                }
                default -> ColorHelper.printRed("Invalid input!");
            }
        }
    }

    /**
     * Takes a Reservation entity, extracts its Event set, manages each event, and then persists the reservation (including events).
     * @param reservation a Reservation entity containing a set of Event entities
     * @return true if reservation (including events) was successfully added to DB, false if not
     */
    public static boolean createReservationWithEvents(Reservation reservation) {
        Set<Event> events = reservation.getEvents();
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();

            // Manage events and update both sides of the relationship
            // -> Otherwise will_attend table (manyToMany) is not updated correctly
            Set<Event> managedEvents = new HashSet<>();
            for (Event event : events) {
                Event managedEvent = em.contains(event) ? event : em.merge(event); //manage event with em
                managedEvents.add(managedEvent);
                managedEvent.getReservation().add(reservation);
            }
            reservation.setEvents(managedEvents);  // add fully managed events to reservation

            // Persist reservation on DB
            em.persist(reservation);
            et.commit();
            ColorHelper.printGreen("Reservation and events successfully saved.");
            return true;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            ColorHelper.printRed("Error in QueryMenu / createReservationWithEvents: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Create a list of BookedServices with reservation based on user input.
     * User can book one or more services
     *
     * @param reservation Reservation to use in new BookedServices
     * @return List of BookedServices (can be empty)
     */
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

    /**
     * Lets user choose available events in defined date range and returns them as a list.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return A list of Events (can be empty)
     */
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
                        if (!events.contains(e)) {
                            events.add(e);
                            ColorHelper.printGreen("Added the following event to the reservation: " + e);
                        } else {
                            ColorHelper.printRed("The following event was has already been added to the reservation and can not be added again: " + e);
                        }
                    }
                }
                case "N", "n" -> {
                    return events;
                }
                default -> ColorHelper.printRed("Invalid input!");
            }
        }
    }

    /**
     * Lets user choose a guest from list or create a new one
     *
     * @return Chosen or created guest
     */
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
                    g = (Guest) g.createFromUserInput();
                    HotelEntityHandler.create(g); //persist new guest on database
                    return g;
                }
                default -> ColorHelper.printRed("Invalid input!");
            }
        }
    }

    /**
     * Get a start and end date from user input.
     * Dates have to be valid and start date must be before (or at) end date. No other conditions are checked.
     * (Dates like 01.01.0000 or 31.12.12912 are valid)
     *
     * @return a List containing exactly 2 dates. start is at index 0 and end at 1
     */
    private static List<Date> fetchDatesFromUserInput() {
        String instructions = "Please enter the desired Start Date in the format dd.MM.yyyy like 18.03.2024";
        String errorMessage = "Invalid Input!";
        String instructions2 = "Please enter the desired End Date in the format dd.MM.yyyy like 18.03.2024";
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
            ColorHelper.printRed("Chosen start date: " + startDate + ", Chosen end date: " + endDate);
        }
    }

    /**
     * Fetch a list of available rooms in given date range.
     * Rooms are available if there is no overlapping reservation or PlannedMaintenance.
     *
     * @param startDate start date
     * @param endDate   end date
     * @return A list of Rooms which are not reserved  or under maintenance during defined time range.
     */
    private static List<Room> fetchAvailableRoomsFromUserInput(Date startDate, Date endDate) {
        EntityManager em = EMFSingleton.getEntityManager();
        try {
            String query = """
                    SELECT a FROM room a
                        WHERE NOT EXISTS (
                            SELECT 1 FROM reservation b
                                WHERE b.room = a AND
                                b.start_date <= :endDate AND
                                b.end_date >= :startDate
                    )
                    AND NOT EXISTS (
                            SELECT 1 FROM planned_maintenance c
                                WHERE c.room = a AND
                                c.start_date <= :endDate AND
                                c.end_date >= :startDate
                    )
                     ORDER BY a.room_nr""";
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

    /**
     * Fetches and prints analytics for all rooms on a defined Date (Query 2)
     * The following data is analyzed:
     * -> Number of booked rooms on date
     * -> Number of empty rooms
     * -> Sum of "lost earnings" (cost of all empty rooms)
     * -> Sum of earnings (cost of all reserved rooms)
     *
     * @param date day for which to do the analysis
     */
    private static void printRoomStats(Date date) {
        EntityManager em = EMFSingleton.getEntityManager();
        try {
            String sql =
                    """
                            SELECT
                            COUNT(*) NUMBER_BOOKED_ROOMS,
                                (
                                    SELECT
                                        COUNT(*)
                                    FROM
                                        room
                                ) - COUNT(*) NUMBER_EMPTY_ROOMS,
                                COALESCE(SUM(cost), 0) EXPECTED_EARNINGS,
                                (
                                SELECT
                                        SUM(cost)
                                    FROM
                                        room
                                ) - COALESCE(SUM(cost), 0) LOST_EARNINGS
                            FROM
                                reservation A
                                full outer JOIN room B ON A.room_nr = B.room_nr
                            WHERE
                                :targetDate BETWEEN start_date AND end_date""";

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

    /**
     * Fetch a list of all PlannedMaintenances and associate employees in the defined date range
     *
     * @param startDate start date
     * @param endDate   end date
     * @return A list of PlannedMaintenances (also containing linked employees). Can be empty.
     */
    private static List<PlannedMaintenance> findMaintenanceBetweenDates(Date startDate, Date endDate) {
        EntityManager em = EMFSingleton.getEntityManager();
        try {
            //Joining to employee so employee name can be displayed (Feedback in last FOGL lesson)
            String query = """
                    SELECT x FROM planned_maintenance x
                    JOIN FETCH x.employee e
                        WHERE x.start_date <= :endDate
                        AND x.end_date >= :startDate""";
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

    /**
     * Get a list of Employees which have the right job to perform the given ServiceType.
     *
     * @param serviceType needed to perform
     * @return List of Employees (can be empty)
     */
    public static List<Employee> getEmployeesForServiceType(ServiceType serviceType) {
        EntityManager em = EMFSingleton.getEntityManager();
        int serviceTypeId = serviceType.getService_id();

        String jpql = """
                SELECT a FROM employee a
                    WHERE EXISTS (
                        SELECT b FROM job b
                        JOIN b.serviceTypes c
                            WHERE c.id = :serviceTypeId AND a.job = b
                    )""";

        TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
        query.setParameter("serviceTypeId", serviceTypeId);

        return query.getResultList();
    }
}
