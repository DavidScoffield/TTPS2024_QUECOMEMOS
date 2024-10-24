package model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_id")
  private Long id;

  @ManyToMany
  @JoinTable(name = "cart_menu", joinColumns = @JoinColumn(name = "cart_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
  private List<Menu> menus;

  @OneToOne
  private Client client;

  public ShoppingCart(Client client) {
    this.client = client;
  }

}
