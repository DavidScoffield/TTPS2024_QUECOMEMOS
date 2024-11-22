package com.ttps.quecomemos.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends User {

  private String photo;

  @OneToOne(mappedBy = "client", cascade = CascadeType.PERSIST)
  @JsonManagedReference
  private ShoppingCart cart;

  @OneToMany(mappedBy = "client")
  private List<Order> orders;

  @OneToMany(mappedBy = "client")
  private List<Suggestion> suggestions;

  public Client(String dni, String name, String email, String password, String role) {
    super(dni, name, email, password, role);
  }

  public Client(String dni, String name, String email, String password, String role,
      String photo, ShoppingCart cart) {
    super(dni, name, email, password, role);
    this.photo = photo;
    this.cart = cart;
  }

  @Override
  public String toString() {
    return "Client{name='" + getName() + "', dni='" + getDni() + "'}";
  }

}
