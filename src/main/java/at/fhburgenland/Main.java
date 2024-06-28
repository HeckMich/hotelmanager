package at.fhburgenland;

import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.ui.MainMenu;


public class Main {
    //TODO: ER/RM in resource folder
    // Tostring prettyfy

    //TODO: Patrizia Fragen:
    // -> CRUD inklusive spezifischer Logik (z.B. Reservierung, check welche Räume, etc. ?) nein
    // -> Ungenutzte Getter / Setter / Constructors / IDs ... ? -> ungenutzt löschen
    // -> für FK: entity vs. id vs. beides in Klasse? -> Beides ist okay
    // -> Java Doc - Wie ausführlich? -> Nicht nötig, komplex methoden erklren ...
    // -> DB Querys als lange SQL statements oder alle Daten holen & in Java verarbeiten? -> SQL
    public static void main(String[] args) {
        EMFSingleton.getEntityManager(); //Initializing EntityManager so Hibernate connection is opened at program start
        MainMenu.showMainMenu();
    }
}
