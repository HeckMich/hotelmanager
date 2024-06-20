package at.fhburgenland.handlers;

import at.fhburgenland.EMFSingleton;
import at.fhburgenland.entities.*;
import at.fhburgenland.helpers.ColorHelper;
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
            System.out.println("Created " + entity.getClass().getName() + ": " + entity);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in HotelEntityHandler create: " + ex.getMessage());
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
            System.out.println("Deleted " + entity);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.err.println("ERROR in HotelEntityHandler delete: " + ex.getMessage());
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
            System.out.println("Updated " + entity);
        } catch (Exception ex) {
            if (et != null) {
                et.rollback();
            }
            System.out.println("ERROR in HotelEntityHandler update: " + ex.getMessage());
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
            System.out.println("ERROR in HotelEntityHandler read: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public static <T extends HotelEntity> List<T> readAll(Class<T> entityClass){
        EntityManager em = EMFSingleton.getEntityManager();
        String query = "SELECT x FROM " + entityClass.getSimpleName().toLowerCase() + " x";
        List<T> list = null;
        try {
            var x = Plz.class;
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
        for (int i = 0; i < list.size(); i++) {
            ColorHelper.printYellow(i + " - " + list.get(i).toString());
        }
    }

    public static <T extends HotelEntity> T selectEntityFromList(Class<T> entityClass) {
        List<T> list = readAll(entityClass);

        if (list.isEmpty()) {
            ColorHelper.printRed("No entities found.");
            return null;
        }

        Scanner scanner = new Scanner(System.in);
        int index = -1;
        T selectedEntity = null;

        while (true) {
            ColorHelper.printBlue("Enter the index of the " + entityClass.getSimpleName() + " to select:");
            printAllAsIndexedList(entityClass);
            try {
                index = Integer.parseInt(scanner.nextLine());
                selectedEntity = list.get(index);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid index. Please enter an index between 0 and " + (list.size() - 1));
            }
        }

        return selectedEntity;
    }
}
