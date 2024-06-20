package at.fhburgenland.entities;

import at.fhburgenland.helpers.ColorHelper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public abstract class HotelEntity {
    protected static Scanner scanner = new Scanner(System.in);
    public abstract HotelEntity createFromUserInput();

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

    public static Date parseDateFromUser(String instructions, String errorMessage) {
        while (true) {
            ColorHelper.printBlue(instructions);
            String line = scanner.nextLine();
            if (line != null && !line.isEmpty()) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
                    Date date = formatter.parse(line);
                    return date;
                } catch (ParseException x) {
                    ColorHelper.printRed(errorMessage);
                }
            }
        }
    }


    public static String parseStringFromUser(String instructions, String errorMessage) {
        while (true) {
            ColorHelper.printBlue(instructions);
            String line = scanner.nextLine();
            if (line != null && !line.isEmpty()) {
                return line;
            }
        }
    }
}
