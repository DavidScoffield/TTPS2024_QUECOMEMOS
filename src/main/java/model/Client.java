package model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends User {

  private String photo;

  @OneToOne(mappedBy = "client")
  private ShoppingCart cart;

  @OneToMany(mappedBy = "client")
  private List<Order> orders;

  @OneToMany(mappedBy = "client")
  private List<Suggestion> suggestions;

  public Client(String dni, String name, String email, String password,
      String role) {
    super(dni, name, email, password, role);
  }

  public Client(String dni, String name, String email, String password,
      String role, String photo, ShoppingCart cart) {
    super(dni, name, email, password, role);
    this.photo = photo;
    this.cart = cart;
  }

  public Client(String photo, ShoppingCart cart) {
    this.photo = photo;
    this.cart = cart;
  }
}
