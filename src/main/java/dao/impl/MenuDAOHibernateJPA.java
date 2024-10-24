package dao.impl;

import java.util.List;

import dao.interf.MenuDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Menu;
import util.HibernateUtil;

public class MenuDAOHibernateJPA extends GenericDAOHibernateJPA<Menu>
    implements MenuDAO {
  public MenuDAOHibernateJPA() {
    super(Menu.class);
  }

  @Override
  public Menu getByName(String name) {
    EntityManager em = HibernateUtil.getEntityManager();
    try {
      TypedQuery<Menu> query = em
          .createQuery("SELECT m FROM Menu m WHERE m.name = :name", Menu.class);
      query.setParameter("name", name);
      List<Menu> result = query.getResultList();
      return result.isEmpty() ? null : result.get(0);
    } catch (Exception e) {
      return null;
    } finally {
      em.close();
    }
  }

}