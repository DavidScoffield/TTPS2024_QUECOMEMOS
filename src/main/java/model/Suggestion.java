package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
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

  public Suggestion(String message, String suggestionType, Client client) {
    this.message = message;
    this.suggestionType = suggestionType;
    this.client = client;
  }

}
