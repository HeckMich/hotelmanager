package at.fhburgenland.entities;

import at.fhburgenland.helpers.ColorHelper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * Blueprint for all Entities in hotel management system.
 * All HotelEntities can be managed by generic methods in HotelEntityHandler
 * Contains abstract methods to create and update from user input as well as some static helper methods used in extending classes.
 */
public abstract class HotelEntity {
    protected static final Scanner scanner = new Scanner(System.in);
    protected static final String e1 = "Invalid input!";

    /**
     * Defines an abstract methode which creates a new object of the specific Hotel Entity and returns it.
     * User input is likely to be involved.
     *
     * @return The created HotelEntity
     */
    public abstract HotelEntity createFromUserInput();

    /**
     * Defines an abstract methode which updates this Hotel Entity and returns itself.
     * User input is likely to be involved.
     *
     * @return The updated HotelEntity
     */
    public abstract HotelEntity updateFromUserInput();

    /**
     * gets called if Integer-Input is needed from user
     *
     * @param instructions message printed before user input
     * @param errorMessage message printed in case of invalid input
     * @return (int) int parsed from user
     */
    public static int parseIntFromUser(String instructions, String errorMessage) {
        while (true) {
            ColorHelper.printBlue(instructions);
            String line = scanner.nextLine();
            if (line != null && !line.isEmpty()) {
                try {
                    return Integer.parseInt(line);
                } catch (NumberFormatException x) {
                    ColorHelper.printRed(errorMessage);
                }

            }
        }
    }

    /**
     * gets called if BigDecimal-Input is needed
     *
     * @param instructions message printed before user input
     * @param errorMessage message printed in case of invalid input
     * @return (BigDecimal) number parsed from user
     */
    public static BigDecimal parseBigDecimalFromUser(String instructions, String errorMessage) {
        while (true) {
            ColorHelper.printBlue(instructions);
            String line = scanner.nextLine();
            if (line != null && !line.isEmpty()) {
                try {
                    return BigDecimal.valueOf(Double.parseDouble(line));
                } catch (NumberFormatException x) {
                    ColorHelper.printRed(errorMessage);
                }

            }
        }
    }

    /**
     * gets called if Date-input is needed.
     *
     * @param instructions message printed before user input
     * @param errorMessage message printed in case of invalid input
     * @return (Date) Date parsed from user
     */
    public static Date parseDateFromUser(String instructions, String errorMessage) {
        while (true) {
            ColorHelper.printBlue(instructions);
            String line = scanner.nextLine();
            if (line != null && !line.isEmpty()) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                    formatter.setLenient(false); //Will only accept correct dates
                    return formatter.parse(line);
                } catch (ParseException x) {
                    ColorHelper.printRed(errorMessage);
                }
            }
        }
    }

    /**
     * gets called if String-input is needed
     *
     * @param instructions message printed before user input
     * @param errorMessage message printed in case of invalid input
     * @return (String) String parsed from user
     */
    public static String parseStringFromUser(String instructions, String errorMessage) {
        while (true) {
            ColorHelper.printBlue(instructions);
            String line = scanner.nextLine();
            if (line != null && !line.isEmpty()) {
                return line;
            }
            ColorHelper.printRed(errorMessage);
        }
    }

    /**
     * gets called if String-input with fixed length (as defined in SQL-Creates) is needed
     *
     * @param instructions message printed before user input
     * @param errorMessage message printed in case of invalid input
     * @param minLength    in declaring minimum length of input
     * @param maxLength    in declaring maximum length of input
     * @return (String) string parsed from user
     */
    public static String parseStringFixedLengthFromUser(String instructions, String errorMessage, int minLength, int maxLength) {
        while (true) {
            ColorHelper.printBlue(instructions);
            String line = scanner.nextLine();
            if (line != null && !line.isEmpty() && line.length() >= minLength && line.length() <= maxLength) {
                return line;
            }
            ColorHelper.printRed(errorMessage);
        }
    }
}
