package at.fhburgenland;


import at.fhburgenland.helpers.ColorHelper;
import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.ui.CrudMenu;
import at.fhburgenland.ui.QueryMenu;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        EMFSingleton.getEntityManager(); //Initializing EntityManager so Hibernate connection is opened at program start
        showMainMenu();
    }

    private static void showMainMenu() {
        while(true) {
            ColorHelper.printBlue("----------------------------------------------------------------------");
            ColorHelper.printBlue("Welcome to the Hotel Management System! Please choose an option below:");
            ColorHelper.printBlue("----------------------------------------------------------------------");
            ColorHelper.printYellow("1 - Manage hotel entities (CRUD)");
            ColorHelper.printYellow("2 - Maintenance Analytics over time-span (Query 1)");
            ColorHelper.printYellow("3 - Room Analytics for a day (Query 2)");
            ColorHelper.printYellow("4 - Start Reservation Process (Query 3)");
            ColorHelper.printOrange("X - Quit");
            String line = scanner.nextLine();
            switch (line) {
                case "X", "x" -> System.exit(0);
                case "1" -> CrudMenu.showCrudMenu();
                case "2" -> QueryMenu.queryOne();
                case "3" -> QueryMenu.queryTwo();
                case "4" -> QueryMenu.reservationProcess();
                default -> ColorHelper.printRed("Invalid input. Try again.");
            }
        }
    }
}
