package at.fhburgenland;

import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.ui.MainMenu;

public class Main {
    public static void main(String[] args) {
        EMFSingleton.getEntityManager(); //Initializing EntityManager so Hibernate connection is opened at program start
        MainMenu.showMainMenu();
    }
}
