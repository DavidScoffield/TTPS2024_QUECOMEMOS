package com.ttps.quecomemos.dao.impl;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ttps.quecomemos.model.Client;
import com.ttps.quecomemos.model.Suggestion;
import com.ttps.quecomemos.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

class SuggestionDAOHibernateJPATest {

  private EntityManager em;
  private SuggestionDAOHibernateJPA suggestionDAO;

  @BeforeEach
  void setUp() {
    em = HibernateUtil.getEntityManager();
    suggestionDAO = new SuggestionDAOHibernateJPA();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Client client1 = new Client();
    client1.setName("Client1");

    Client client2 = new Client();
    client2.setName("Client2");

    em.persist(client1);
    em.persist(client2);

    Suggestion suggestion1 = new Suggestion("I love the food here", "Food",
        client1);

    Suggestion suggestion2 = new Suggestion("Service could be better",
        "Service", client2);

    Suggestion suggestion3 = new Suggestion("More vegetarian options, please",
        "Food", client1);

    em.persist(suggestion1);
    em.persist(suggestion2);
    em.persist(suggestion3);

    tx.commit();
  }

  @AfterEach
  void tearDown() {
    // Limpiar los datos después de cada prueba
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.createQuery("DELETE FROM Suggestion").executeUpdate();
    em.createQuery("DELETE FROM Client").executeUpdate();
    tx.commit();
    em.close();
  }

  @Test
  void testGetByType() {
    List<Suggestion> suggestions = suggestionDAO.getByType("Food");
    assertNotNull(suggestions);
    assertEquals(2, suggestions.size());
    assertEquals("Food", suggestions.get(0).getSuggestionType());
    assertEquals("Food", suggestions.get(1).getSuggestionType());
  }

  @Test
  void testGetLikeMessage() {
    List<Suggestion> suggestions = suggestionDAO.getLikeMessage("vegetarian");
    assertNotNull(suggestions);
    assertEquals(1, suggestions.size());
    assertTrue(suggestions.get(0).getMessage().contains("vegetarian"));
  }
}
