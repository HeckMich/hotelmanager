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

public class HotelEntityHandler {
    public static HotelEntity create(HotelEntity entity) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;

        try {
            et = em.getTransaction();
            et.begin();
            em.persist(entity);
            et.commit();
            ColorHelper.printGreen("Created " + entity.getClass().getSimpleName() + ": " + entity);
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
            ColorHelper.printGreen("Deleted " + entity);
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

    public static HotelEntity update(HotelEntity entity) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            entity = em.merge(entity); //Merge entity to implement changes (can create new entity if PK is not found!)
            et.commit();
            ColorHelper.printGreen("Updated " + entity);
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

    public static <T extends HotelEntity> T read(Class<T> entityClass, int id) {
        EntityManager em = EMFSingleton.getEntityManager();
        EntityTransaction et = null;
        T entity = null;
        try {
            et = em.getTransaction();
            et.begin();
            entity = em.find(entityClass, id);
            if (entity == null) throw new IllegalArgumentException("The HotelEntity with id : " + id + " was not found in DB." );
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

    public static <T extends HotelEntity> List<T> readAll(Class<T> entityClass){
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM " + entityClass.getAnnotation(Entity.class).name() + " x";
        List<T> list = null;
        try {
            TypedQuery<T> tq = em.createQuery(query, entityClass);
            list = tq.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }

    public static void printAllAsIndexedList(Class<? extends HotelEntity> entityClass) {
        List<? extends HotelEntity> list = readAll(entityClass);
        printAsIndexedList(list);
    }
    public static void printAsIndexedList(List<? extends HotelEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            ColorHelper.printYellow(i + " - " + list.get(i).toString());
        }
    }

    public static void printAsNeutralList(List<? extends HotelEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((list.get(i).toString()));
        }
    }

    public static <T extends HotelEntity> T selectEntityFromFullList(Class<T> entityClass) {
        List<T> list = readAll(entityClass);
        return selectEntityFromList(list);
    }

    public static <T extends HotelEntity> T selectEntityFromList(List<T> list) {
        if (list == null || list.isEmpty()) {
            ColorHelper.printRed("No entities in list.");
            return null;
        }

        Scanner scanner = new Scanner(System.in);
        int index;
        T selectedEntity = null;

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
