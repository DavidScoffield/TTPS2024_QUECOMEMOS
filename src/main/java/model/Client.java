package model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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

  public Client() {
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

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public ShoppingCart getCart() {
    return cart;
  }

  public void setCart(ShoppingCart cart) {
    this.cart = cart;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public List<Suggestion> getSuggestions() {
    return suggestions;
  }

  public void setSuggestions(List<Suggestion> suggestions) {
    this.suggestions = suggestions;
  }
}
