package at.fhburgenland.ui;

import at.fhburgenland.entities.*;
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
                    showCrudOptions(emptyEntityFromType(type));
                    break;
                } else {
                    throw new NumberFormatException();
                }
            } catch (Exception x) {
                ColorHelper.printRed(("Please enter a number in the provided range!"));
            }
        }
    }

    private static HotelEntity emptyEntityFromType(int type) {
        switch (type) {
            case 1  : return new Room();
            case 2  : return new Reservation();
            case 3  : return new Invoice();
            case 4  : return new Event();
            case 5  : return new Plz();
            case 6  : return new Guest();
            case 7  : return new MaintenanceType();
            case 8  : return new Job();
            case 9  : return new ServiceType();
            case 10 : return new BookedService();
            case 11 : return new Employee();
            case 12 : return new PlannedMaintenance();
            default: throw new IllegalArgumentException("Not a valid type");
        }
    }


    private static void showCrudOptions(HotelEntity entity) {
        while(true) {
            ColorHelper.printBlue("Choose the CRUD option to perform:");
            ColorHelper.printYellow("1 - CREATE");
            ColorHelper.printYellow("2 - READ");
            ColorHelper.printYellow("3 - UPDATE");
            ColorHelper.printYellow("4 - DELETE");
            ColorHelper.printYellow("X - Cancel");
            String line = scanner.nextLine();
            switch (line) {
                case "X", "x" -> {
                    return;
                }
                case "1" -> HotelEntityHandler.create(entity.createFromUserInput());
                case "2" -> readEntityByIdPromt(entity);
                case "3" -> HotelEntityHandler.update(entity.createFromUserInput()); //Update methode instead ??
                case "4" -> HotelEntityHandler.delete(HotelEntityHandler.selectEntityFromFullList(entity.getClass()));
                default -> ColorHelper.printRed("Invalid input. Try again.");
            }
        }
    }

    private static void readEntityByIdPromt(HotelEntity entity) {
        while(true) {
            ColorHelper.printBlue("Please enter the ID of the " + entity.getClass().getSimpleName() + "to read.");
            ColorHelper.printBlue("Or press L to print list of all " + entity.getClass().getSimpleName() );
            String line = scanner.nextLine();
            switch (line) {
                case "X", "x" -> {
                    return;
                }
                case "L", "l" -> {
                    HotelEntityHandler.printAllAsIndexedList(entity.getClass());
                    return;
                }
                default -> {
                    try {
                        int i = Integer.parseInt(line);
                        HotelEntity result = HotelEntityHandler.read(entity.getClass(), i);
                        ColorHelper.printGreen("The following " + entity.getClass().getSimpleName() + " was read from DB:");
                        ColorHelper.printGreen(result.toString());
                        return;
                    } catch (NumberFormatException x) {
                        ColorHelper.printRed("Invalid input!");
                    }
                }
            }
        }
    }


//    private static void callRead(int type) {
//        int id = queryUserForID();
//        switch (type) {
//            case 1 -> ColorHelper.printGreen(RoomHandler.readRoom(id).toString());
//            case 2 -> ColorHelper.printGreen(ReservationHandler.readReservation(id).toString());
//            case 3 -> ColorHelper.printGreen(InvoiceHandler.readInvoice(id).toString());
//            case 4 -> ColorHelper.printGreen(EventHandler.readEvent(id).toString());
//            case 5 -> ColorHelper.printGreen(PlzHandler.readPlz(id).toString());
//            case 6 -> ColorHelper.printGreen(GuestHandler.readGuest(id).toString());
//            case 7 -> ColorHelper.printGreen(MaintenanceTypeHandler.readMaintenanceType(id).toString());
//            case 8 -> ColorHelper.printGreen(JobHandler.readJob(id).toString());
//            case 9 -> ColorHelper.printGreen(ServiceTypeHandler.readServiceType(id).toString());
////            case 10 -> ColorHelper.printGreen(BookedServiceHandler.readBookedService(id).toString());
//            case 11 -> ColorHelper.printGreen(EmployeeHandler.readEmployee(id).toString());
//            case 12 -> ColorHelper.printGreen(PlannedMaintenanceHandler.readPlannedMaintenance(id).toString());
//        }
//    }
//
//    private static void callDelete(int type) {
//        int id = queryUserForID();
//        switch (type) {
//            case 1 ->  ColorHelper.printGreen("Deleted item with id " + id + " successfully: " + RoomHandler.deleteRoom(id));
//            case 2 ->  ColorHelper.printGreen("Deleted item with id " + id + " successfully: " + ReservationHandler.deleteReservation(id));
//            case 3 ->  ColorHelper.printGreen("Deleted item with id " + id + " successfully: " + InvoiceHandler.deleteInvoice(id));
//            case 4 ->  ColorHelper.printGreen("Deleted item with id " + id + " successfully: " + EventHandler.deleteEvent(id));
//            case 5 ->  ColorHelper.printGreen("Deleted item with id " + id + " successfully: " + PlzHandler.deletePlz(id));
//            case 6 ->  ColorHelper.printGreen("Deleted item with id " + id + " successfully: " + GuestHandler.deleteGuest(id));
//            case 7 ->  ColorHelper.printGreen("Deleted item with id " + id + " successfully: " + MaintenanceTypeHandler.deleteMaintenanceType(id));
//            case 8 ->  ColorHelper.printGreen("Deleted item with id " + id + " successfully: " + JobHandler.deleteJob(id));
//            case 9 ->  ColorHelper.printGreen("Deleted item with id " + id + " successfully: " + ServiceTypeHandler.deleteServiceType(id));
////            case 10 -> ColorHelper.printGreen("Deleted item with id " + id + " successfully: " + BookedServiceHandler.(id));
//            case 11 -> ColorHelper.printGreen("Deleted item with id " + id + " successfully: " + EmployeeHandler.deleteEmployee(id));
//            case 12 -> ColorHelper.printGreen("Deleted item with id " + id + " successfully: " + PlannedMaintenanceHandler.deletePlannedMaintenance(id));
//        }
//    }
//    private static int queryUserForID() {
//        while(true) {
//            ColorHelper.printBlue("Please enter the ID of the entity you want to select:");
//            String line = scanner.nextLine();
//            try {
//                return Integer.parseInt(line);
//            } catch (NumberFormatException x) {
//                ColorHelper.printRed(("Please enter a number matching the desired ID."));
//            }
//        }
//    }

//    private static void printListOf() {
//        while(true) {
//            ColorHelper.printBlue("Please enter the ID of the entity you want to select:");
//            String line = scanner.nextLine();
//            try {
//                return Integer.parseInt(line);
//            } catch (NumberFormatException x) {
//                ColorHelper.printRed(("Please enter a number matching the desired ID."));
//            }
//        }
//    }

}
