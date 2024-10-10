package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "suggestions")
public class Suggestion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "suggestion_id")
  private Long id;

  private String message;

  private String suggestionType;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  public Suggestion() {
  }

  public Suggestion(String message, String suggestionType, Client client) {
    this.message = message;
    this.suggestionType = suggestionType;
    this.client = client;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getSuggestionType() {
    return suggestionType;
  }

  public void setSuggestionType(String suggestionType) {
    this.suggestionType = suggestionType;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

}
