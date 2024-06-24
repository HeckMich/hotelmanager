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
            ColorHelper.printOrange("X - Return");

            String line = scanner.nextLine();

            try {
                if (line.toLowerCase().matches("x")) {
                    return;
                }
                int type = Integer.parseInt(line);
                if (type > 0 && type <= 12) {
                    showCrudOptions(emptyEntityFromType(type));
                } else {
                    throw new NumberFormatException();
                }
            } catch (Exception x) {
                ColorHelper.printRed(("Please enter a number in the provided range!"));
            }
        }
    }

    private static void showCrudOptions(HotelEntity entity) {
        while(true) {
            ColorHelper.printBlue("Choose the CRUD option to perform on " + entity.getClass().getSimpleName() + ":");
            ColorHelper.printYellow("1 - CREATE");
            ColorHelper.printYellow("2 - READ");
            ColorHelper.printYellow("3 - UPDATE");
            ColorHelper.printYellow("4 - DELETE");
            ColorHelper.printOrange("X - Return");
            String line = scanner.nextLine();
            switch (line) {
                case "X", "x" -> {
                    return;
                }
                case "1" -> {
                    HotelEntity e = entity.createFromUserInput();
                    if (e != null) {
                        HotelEntityHandler.create(e);
                    }
                }
                case "2" -> readEntityByIdPrompt(entity);
                case "3" -> {
                    HotelEntity e = entity.updateFromUserInput();
                    if (e != null) {
                        HotelEntityHandler.update(e);
                    }
                }
                case "4" -> {
                    ColorHelper.printBlue("Choose the " + entity.getClass().getSimpleName() + " to delete. Be careful, deleting one entity can have a cascading effect on others! Like deleting a room will also delete all associated reservations.");
                    HotelEntityHandler.delete(HotelEntityHandler.selectEntityFromFullList(entity.getClass()));
                }
                default -> ColorHelper.printRed("Invalid input. Try again.");
            }
        }
    }

    private static void readEntityByIdPrompt(HotelEntity entity) {
        while(true) {
            ColorHelper.printBlue("Please enter the ID of the " + entity.getClass().getSimpleName() + " to read.");
            ColorHelper.printBlue("Or press L to print list of all " + entity.getClass().getSimpleName() + "s" );
            String line = scanner.nextLine();
            switch (line) {
                case "X", "x" -> {
                    return;
                }
                case "L", "l" -> {
                    HotelEntityHandler.printAsNeutralList(HotelEntityHandler.readAll(entity.getClass()));
                    return;
                }
                default -> {
                    try {
                        int i = Integer.parseInt(line);
                        HotelEntity result = HotelEntityHandler.read(entity.getClass(), i);
                        if (result != null) {
                            ColorHelper.printGreen("The following " + entity.getClass().getSimpleName() + " was read from DB:");
                            ColorHelper.printGreen(result.toString());
                        } else {
                            ColorHelper.printRed("No result found!");
                        }
                        return;
                    } catch (NumberFormatException x) {
                        ColorHelper.printRed("Invalid input!");
                    }
                }
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
}
