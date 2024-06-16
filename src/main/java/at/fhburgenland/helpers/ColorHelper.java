package at.fhburgenland.helpers;

/**
 * Stores ANSI color codes to make console output more readable
 * Provides methods to print to console in chosen color
 */
public class ColorHelper {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001b[34m";
    private static final String ANSI_RESET = "\u001B[0m";

    /**Prints line to console in RED
     * @param line Line to print in color
     */
    public static void printRed(String line) {
        System.out.println(ANSI_RED + line + ANSI_RESET);
    }
    /**Prints line to console in GREEN
     * @param line Line to print in color
     */
    public static void printGreen(String line) {
        System.out.println(ANSI_GREEN + line + ANSI_RESET);
    }
    /**Prints line to console in BLUE
     * @param line Line to print in color
     */
    public static void printBlue(String line) {
        System.out.println(ANSI_BLUE + line + ANSI_RESET);
    }
    /**Prints line to console in YELLOW
     * @param line Line to print in color
     */
    public static void printYellow(String line) {
        System.out.println(ANSI_YELLOW + line + ANSI_RESET);
    }
}
