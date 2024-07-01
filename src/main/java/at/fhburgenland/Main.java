package at.fhburgenland;

import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.ui.MainMenu;

public class Main {
    /**
     * Starting point for the rest of the application.
     * *** OVERVIEW PROGRAM STRUCTURE ***
     * Database Tables are represented as classes extending entities.HotelEntity.
     * -> All HotelEntities implement methods to CREATE and UPDATE themselves from user input.
     * -> All CRUD operations are managed from the ui.CrudMenu class.
     * -> All DB interactions for CRUD happen in handlers.HotelEntityHandler and are generalized to work with all HotelEntities
     * -> Some HotelEntities have custom logic imposing additional restraints for CRUD operations. Others only check for technical restraints.
     * Queries are managed in ui.QueryMenu
     * -> Queries are implemented as successive SQL statements combined with user-input
     * ******
     */
    public static void main(String[] args) {
        EMFSingleton.getEntityManager(); //Initializing EntityManager so Hibernate connection is opened at program start
        MainMenu.showMainMenu();
    }
}
