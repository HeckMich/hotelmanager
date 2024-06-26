package at.fhburgenland;


import at.fhburgenland.helpers.ColorHelper;
import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.ui.CrudMenu;
import at.fhburgenland.ui.MainMenu;
import at.fhburgenland.ui.QueryMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EMFSingleton.getEntityManager(); //Initializing EntityManager so Hibernate connection is opened at program start
        MainMenu.showMainMenu();
    }
}
