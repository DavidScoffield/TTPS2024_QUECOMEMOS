package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {

  private static final EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

  private static EntityManagerFactory buildEntityManagerFactory() {
    try {
      // Cargar la configuración desde persistence.xml
      return Persistence.createEntityManagerFactory("miPostgresDatabase");

    } catch (Throwable ex) {
      // Manejar cualquier excepción que ocurra durante la inicialización
      System.err.println("Error al crear el EntityManagerFactory: " + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static EntityManager getEntityManager() {
    return entityManagerFactory.createEntityManager();
  }

  public static void shutdown() {
    // Cerrar el EntityManagerFactory cuando no se necesite más
    if (entityManagerFactory != null) {
      entityManagerFactory.close();
    }
  }
}
