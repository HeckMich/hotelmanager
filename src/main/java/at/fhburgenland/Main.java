package at.fhburgenland;


import at.fhburgenland.helpers.ColorHelper;
import at.fhburgenland.ui.CrudMenu;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        showMainMenu();
    }

    private static void showMainMenu() {
        while(true) {
            ColorHelper.printBlue("Welcome to the hotel management software! Please choose an option below:");
            ColorHelper.printYellow("1 - Manage hotel entities (CRUD)");
            ColorHelper.printYellow("2 - Maintenance Analytics over time-span (Query 1)");
            ColorHelper.printYellow("3 - Room Analytics for day (Query 2)");
            ColorHelper.printYellow("4 - Start Reservation Process (Query 3)");
            ColorHelper.printYellow("X - Quit");
            String line = scanner.nextLine();
            switch (line) {
                case "X", "x" -> System.exit(0);
                case "1" -> CrudMenu.showCrudMenu();
                case "2" -> System.out.println("Not yet implemented");
                case "3" -> System.out.println("Not yet implemented");
                case "4" -> System.out.println("Not yet implemented");
                default -> System.out.println("Invalid input. Try again.");
            }
        }
    }
}
