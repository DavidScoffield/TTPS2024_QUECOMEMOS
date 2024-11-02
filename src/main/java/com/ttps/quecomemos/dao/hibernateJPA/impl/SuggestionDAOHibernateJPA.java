package com.ttps.quecomemos.dao.hibernateJPA.impl;

import java.util.List;

import com.ttps.quecomemos.dao.hibernateJPA.interf.SuggestionDAO;
import com.ttps.quecomemos.model.Suggestion;
import com.ttps.quecomemos.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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
