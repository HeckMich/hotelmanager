package at.fhburgenland;


import at.fhburgenland.helpers.ColorHelper;
import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.ui.CrudMenu;
import at.fhburgenland.ui.MainMenu;
import at.fhburgenland.ui.QueryMenu;

import java.util.Scanner;

public class Main {
    //TODO: Patrizia Fragen:
    // -> Ungenutzte Getter / Setter / Constructors / IDs ... ?
    // -> für FK entity vs. id vs. beides in Klasse?
    // -> Java Doc - Wie ausführlich?
    // -> Inkorrekte ausgabe neu erstellter enteties ...
    public static void main(String[] args) {
        EMFSingleton.getEntityManager(); //Initializing EntityManager so Hibernate connection is opened at program start
        MainMenu.showMainMenu();
    }
}
