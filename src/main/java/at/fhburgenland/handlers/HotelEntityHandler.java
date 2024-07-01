package at.fhburgenland.handlers;

import at.fhburgenland.helpers.EMFSingleton;
import at.fhburgenland.entities.*;
import at.fhburgenland.helpers.ColorHelper;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Scanner;

/**
 * A class containing generic CRUD methods which can be applied to all objects extending HotelEntity.
 * Also implements methods to select entities from a list.
 */
public class HotelEntityHandler {
    /**
     * Takes an object extending HotelEntity and persists it on the Database.
     * Will write an error message to the console and return null, if technical restrictions like FK are violated.
     * Does NOT check for logical constraints!
     *
     * @param entity an generic object extending HotelEntity like Room or Guest
     * @return Returns the HotelEntity which was just persisted or null in case of an error.
     */
    public static HotelEntity create(HotelEntity entity) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            em.persist(entity);
            et.commit();
            ColorHelper.printGreen("Created " + entity.getClass().getSimpleName() + ": " + entity.getClass().getSimpleName() + ": " + entity);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            ColorHelper.printRed("ERROR in HotelEntityHandler create. Did you enter a duplicate key? ");
            return null;
        } finally {
            em.close();
        }
        return entity;
    }

    /**
     * Takes an object extending HotelEntity and removes it from the Database.
     * Writes an error to the console if entity does not exist on DB or a constraint inhibits deletion.
     *
     * @param entity an generic object extending HotelEntity like Room or Guest
     * @return true if deletion was successful, false if not
     */
    public static boolean delete(HotelEntity entity) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            if (!em.contains(entity)) {
                //Merge entity to EM (otherwise delete fails)
                entity = em.merge(entity);
            }
            em.remove(entity);
            et.commit();
            ColorHelper.printGreen("Deleted " + entity.getClass().getSimpleName() + ": " + entity);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            ColorHelper.printRed("ERROR in HotelEntityHandler delete: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    /**
     * Takes an object extending HotelEntity and updates it on the Database, committing any changes.
     * Will write an error message to the console and return null, if technical restrictions like FK are violated.
     * Does NOT check for logical constraints!
     *
     * @param entity an generic object extending HotelEntity like Room or Guest
     * @return the HotelEntity which was updated
     */
    public static HotelEntity update(HotelEntity entity) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            entity = em.merge(entity); //Merge entity to implement changes
            et.commit();
            ColorHelper.printGreen("Updated " + entity.getClass().getSimpleName() + ": " + entity);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            ColorHelper.printRed("ERROR in HotelEntityHandler update: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
        return entity;
    }

    /**
     * Reads a HotelEntity which matches the provided class and ID from the DB.
     * Writes an error to the console if entity does not exist on DB.
     *
     * @param entityClass The class (extending HotelEntity) matching the DB Table to read from
     * @param id          the ID of the object to read
     * @return Returns the matching DB entity or null if none was found
     */
    public static HotelEntity read(Class<HotelEntity> entityClass, int id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        HotelEntity entity;
        try {
            et = em.getTransaction();
            et.begin();
            entity = em.find(entityClass, id);
            if (entity == null)
                throw new IllegalArgumentException("The HotelEntity with id : " + id + " was not found in DB.");
            return entity;
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in HotelEntityHandler read: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Reads a list of all objects of a given class from DB
     *
     * @param entityClass The class extending HotelEntity to be returned as a list
     * @return A List of HotelEntity objects (empty if none are found)
     */
    public static <T extends HotelEntity> List<T> readAll(Class<T> entityClass) {
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM " + entityClass.getAnnotation(Entity.class).name() + " x";
        List<T> list = null;
        try {
            TypedQuery<T> tq = em.createQuery(query, entityClass);
            list = tq.getResultList();
        } catch (Exception ex) {
            System.err.println("ERROR in HotelEntityHandler readAll: " + ex.getMessage());
        } finally {
            em.close();
        }
        return list;
    }

    private static void printAsIndexedList(List<? extends HotelEntity> list) {
        if (list.isEmpty()) ColorHelper.printRed("List is empty.");
        for (int i = 0; i < list.size(); i++) {
            ColorHelper.printYellow(i + " - " + list.get(i).toString());
        }
    }

    /**
     * Prints a list of HotelEntity objects as a neutral list for display.
     *
     * @param list list to print
     */
    public static void printAsNeutralList(List<? extends HotelEntity> list) {
        if (list.isEmpty()) ColorHelper.printRed("List is empty.");
        for (HotelEntity entity : list) {
            System.out.println((entity.toString()));
        }
    }

    /**
     * Prints a list of all HotelEntities of the provided class and lets user select one.
     *
     * @param entityClass Class extending HotelEntity which to select from
     * @return the selected HotelEntity object
     */
    public static <T extends HotelEntity> T selectEntityFromFullList(Class<T> entityClass) {
        List<T> list = readAll(entityClass);
        return selectEntityFromList(list);
    }

    /**
     * Prints the provided list of HotelEntities lets user select one
     *
     * @param list list of objects extending HotelEntity from which to select from
     * @return the selected HotelEntity object
     */
    public static <T extends HotelEntity> T selectEntityFromList(List<T> list) {
        if (list == null || list.isEmpty()) {
            ColorHelper.printRed("No entities in list.");
            return null;
        }
        Scanner scanner = new Scanner(System.in);
        int index;
        T selectedEntity;

        while (true) {
            ColorHelper.printBlue("Enter the index of the " + list.get(0).getClass().getSimpleName() + " to select:");
            printAsIndexedList(list);
            try {
                index = Integer.parseInt(scanner.nextLine());
                selectedEntity = list.get(index);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid number.");
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Invalid index. Please enter an index between 0 and " + (list.size() - 1));
            }
        }

        return selectedEntity;
    }
}
