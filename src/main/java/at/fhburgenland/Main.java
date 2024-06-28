package at.fhburgenland;
x
import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.ui.MainMenu;


public class Main {
    //TODO: Patrizia Fragen:
    // -> CRUD inklusive spezifischer Logik (z.B. Reservierung, check welche Räume, etc. ?)
    // -> Ungenutzte Getter / Setter / Constructors / IDs ... ?
    // -> für FK: entity vs. id vs. beides in Klasse?
    // -> Java Doc - Wie ausführlich?
    // -> Inkorrekte ausgabe neu erstellter enteties ...
    // -> DB Querys als lange SQL statements oder alle Daten holen & in Java verarbeiten?
    public static void main(String[] args) {
        EMFSingleton.getEntityManager(); //Initializing EntityManager so Hibernate connection is opened at program start
        MainMenu.showMainMenu();
    }
}
