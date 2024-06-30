package at.fhburgenland.ui;

import at.fhburgenland.entities.*;
import at.fhburgenland.handlers.*;
import at.fhburgenland.helpers.ColorHelper;

import java.util.Scanner;

public class CrudMenu {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Shows a menu in which the user selects an entity type to interact with
     */
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

    /**
     * Displays a Menu with the 4 CRUD options to possibly perform
     * @param entity an entity of the type to perform the CRUD operations on
     */
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
                    ColorHelper.printRed("Be careful when using CRUD to create: Not all logical restrictions are checked by the program.");
                    HotelEntity e = entity.createFromUserInput();
                    if (e != null) {
                        HotelEntityHandler.create(e);
                    }
                }
                case "2" -> readEntityByIdPrompt(entity);
                case "3" -> {
                    if (HotelEntityHandler.readAll(entity.getClass()).isEmpty()) {
                        ColorHelper.printRed("No " + entity.getClass().getSimpleName() + " found to update!");
                    } else {
                        ColorHelper.printRed("Be careful when using CRUD to update: Not all logical restrictions are checked by the program.");
                        HotelEntity e = entity.updateFromUserInput();
                        if (e != null) {
                            HotelEntityHandler.update(e);
                        }
                    }
                }
                case "4" -> {
                    if (HotelEntityHandler.readAll(entity.getClass()).isEmpty()) {
                        ColorHelper.printRed("No " + entity.getClass().getSimpleName() + " found to delete!");
                    } else {
                        ColorHelper.printBlue("Choose the " + entity.getClass().getSimpleName() + " to delete.");
                        ColorHelper.printRed("Be careful, deleting one entity can have a cascading effect on others! For example deleting a room will also delete all associated reservations.");
                        HotelEntity x = HotelEntityHandler.selectEntityFromFullList(entity.getClass());
                        if (x == null || !HotelEntityHandler.delete(x)){
                            ColorHelper.printRed("Error while deleting!");
                        }
                    }
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
                        HotelEntity result = HotelEntityHandler.read((Class<HotelEntity>) entity.getClass(), i);
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
        return switch (type) {
            case 1 -> new Room();
            case 2 -> new Reservation();
            case 3 -> new Invoice();
            case 4 -> new Event();
            case 5 -> new Plz();
            case 6 -> new Guest();
            case 7 -> new MaintenanceType();
            case 8 -> new Job();
            case 9 -> new ServiceType();
            case 10 -> new BookedService();
            case 11 -> new Employee();
            case 12 -> new PlannedMaintenance();
            default -> throw new IllegalArgumentException("Not a valid type");
        };
    }
}
