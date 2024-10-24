package dao.impl;

import java.util.List;

import dao.interf.SuggestionDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Suggestion;
import util.HibernateUtil;

public class SuggestionDAOHibernateJPA extends GenericDAOHibernateJPA<Suggestion>
implements SuggestionDAO{
	
	public SuggestionDAOHibernateJPA() {
	    super(Suggestion.class);
	  }
	
	@Override
	  public List<Suggestion> getByType(String type) {
	    EntityManager em = HibernateUtil.getEntityManager();
	    try {
	      TypedQuery<Suggestion> query = em
	          .createQuery("SELECT s FROM Suggestion s WHERE s.type = :type", Suggestion.class);
	      query.setParameter("type", type);
	      return query.getResultList();
	    } catch (Exception e) {
	      return null;
	    } finally {
	      em.close();
	    }
	  }

}
