package dao.impl;

import java.util.List;

import dao.interf.SuggestionDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.Suggestion;
import util.HibernateUtil;

public class SuggestionDAOHibernateJPA
    extends GenericDAOHibernateJPA<Suggestion> implements SuggestionDAO {

  public SuggestionDAOHibernateJPA() {
    super(Suggestion.class);
  }

  @Override
  public List<Suggestion> getByType(String type) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Suggestion> query = em.createQuery(
          "SELECT s FROM Suggestion s WHERE s.suggestionType = :suggestionType",
          Suggestion.class);
      query.setParameter("suggestionType", type);
      return query.getResultList();
    } catch (Exception e) {
      return null;
    }
  }

  // @Override
  // public List<Suggestion> getByClient(Long clientId) {

  // try (EntityManager em = HibernateUtil.getEntityManager()) {
  // TypedQuery<Suggestion> query = em.createQuery(
  // "SELECT s FROM Suggestion s WHERE s.client.id = :client_id",
  // Suggestion.class);
  // query.setParameter("client_id", clientId);
  // return query.getResultList();
  // } catch (Exception e) {
  // return null;
  // }
  // }

  // @Override
  // public List<Suggestion> getByClientAndType(Long clientId, String type) {
  // try (EntityManager em = HibernateUtil.getEntityManager()) {
  // TypedQuery<Suggestion> query = em.createQuery(
  // "SELECT s FROM Suggestion s WHERE s.client.id = :client_id AND s.suggestiontype =
  // :suggestiontype",
  // Suggestion.class);
  // query.setParameter("client_id", clientId);
  // query.setParameter("suggestiontype", type);
  // return query.getResultList();
  // } catch (Exception e) {
  // return null;
  // }
  // }

  @Override
  public List<Suggestion> getLikeMessage(String message) {
    try (EntityManager em = HibernateUtil.getEntityManager()) {
      TypedQuery<Suggestion> query = em.createQuery(
          "SELECT s FROM Suggestion s WHERE s.message LIKE :message",
          Suggestion.class);
      query.setParameter("message", "%" + message + "%");
      return query.getResultList();
    } catch (Exception e) {
      return null;
    }
  }

}
