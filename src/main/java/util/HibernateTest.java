package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class HibernateTest {
  public static void main(String[] args) {
    try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
      EntityTransaction transaction = entityManager.getTransaction();
      transaction.begin();

      System.out.println("Hibernate inicializado correctamente!");

      transaction.commit();

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error al inicializar Hibernate: " + e.getMessage());
    }
  }
}
