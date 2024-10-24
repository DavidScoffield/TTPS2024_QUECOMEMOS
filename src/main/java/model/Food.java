package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "foods")
public class Food {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "food_id")
  private Long id;

  private String name;

  private Boolean isVegetarian;

  private String type;

  public Food(String name, Boolean isVegetarian, String type) {
    this.name = name;
    this.isVegetarian = isVegetarian;
    this.type = type;
  }
}
