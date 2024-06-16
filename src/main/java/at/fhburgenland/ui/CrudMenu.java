package at.fhburgenland.ui;

import at.fhburgenland.handlers.*;
import at.fhburgenland.helpers.ColorHelper;

import java.util.Scanner;

public class CrudMenu {
    private static Scanner scanner = new Scanner(System.in);
    public static void showCrudMenu() {
        while(true) {
            ColorHelper.printBlue("Please choose an entity to view or edit:");
            ColorHelper.printYellow("1  - Room");
            ColorHelper.printYellow("2  - Reservation");
            ColorHelper.printYellow("3  - Invoice");
            ColorHelper.printYellow("4  - Event");
            ColorHelper.printYellow("5  - Plz");
            ColorHelper.printYellow("6  - Guest");
            ColorHelper.printYellow("7  - MaintenanceType");
            ColorHelper.printYellow("8  - Job");
            ColorHelper.printYellow("9  - ServiceType");
            ColorHelper.printYellow("10 - BookedService");
            ColorHelper.printYellow("11 - Employee");
            ColorHelper.printYellow("12 - PlannedMaintenance");
            ColorHelper.printYellow("X  - Cancel");

            String line = scanner.nextLine();

            try {
                int type = Integer.parseInt(line);
                if (type > 0 && type <= 12) {
                    showCrudOptions(type);
                    break;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException x) {
                ColorHelper.printRed(("Please enter a number in the provided range!"));
            }
        }
    }

    private static void showCrudOptions(int type) {
        while(true) {
            ColorHelper.printBlue("Choose the CRUD option to perform:");
            ColorHelper.printYellow("1 - CREATE");
            ColorHelper.printYellow("2 - READ");
            ColorHelper.printYellow("3 - UPDATE");
            ColorHelper.printYellow("4 - DELETE");
            ColorHelper.printYellow("X - Cancel");
            String line = scanner.nextLine();
            switch (line) {
                case "X", "x" -> System.exit(0);
                case "1" -> callRead(type);
                case "2" -> callRead(type);
                case "3" -> callRead(type);
                case "4" -> callDelete(type);
                default -> ColorHelper.printRed("Invalid input. Try again.");
            }
        }
    }



    private static void callRead(int type) {
        int id = queryUserForID();
        switch (type) {
            case 1 -> ColorHelper.printGreen(RoomHandler.readRoom(id).toString());
            case 2 -> ColorHelper.printGreen(ReservationHandler.readReservation(id).toString());
            case 3 -> ColorHelper.printGreen(InvoiceHandler.readInvoice(id).toString());
            case 4 -> ColorHelper.printGreen(EventHandler.readEvent(id).toString());
            case 5 -> ColorHelper.printGreen(PlzHandler.readPlz(id).toString());
            case 6 -> ColorHelper.printGreen(GuestHandler.readGuest(id).toString());
            case 7 -> ColorHelper.printGreen(MaintenanceTypeHandler.readMaintenanceType(id).toString());
            case 8 -> ColorHelper.printGreen(JobHandler.readJob(id).toString());
            case 9 -> ColorHelper.printGreen(ServiceTypeHandler.readServiceType(id).toString());
            case 10 -> ColorHelper.printGreen(BookedServiceHandler.readBookedService(id).toString());
            case 11 -> ColorHelper.printGreen(EmployeeHandler.readEmployee(id).toString());
            case 12 -> ColorHelper.printGreen(PlannedMaintenanceHandler.readPlannedMaintenance(id).toString());
        }
    }

    private static void callDelete(int type) {

    }
    private static int queryUserForID() {
        while(true) {
            ColorHelper.printBlue("Please enter the ID of the entity you want to select:");
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException x) {
                ColorHelper.printRed(("Please enter a number matching the desired ID."));
            }
        }
    }

}
