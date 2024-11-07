package com.ttps.quecomemos.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ttps.quecomemos.dto.UpdateClientDTO;

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

  public void updateDetails(UpdateClientDTO updateClientDTO) {
    if (updateClientDTO.getName() != null && !updateClientDTO.getName().isEmpty()) {
      setName(updateClientDTO.getName());
    }
    if (updateClientDTO.getEmail() != null && !updateClientDTO.getEmail().isEmpty()) {
      setEmail(updateClientDTO.getEmail());
    }

    if (updateClientDTO.getNewPassword() != null
        && !updateClientDTO.getNewPassword().isEmpty()) {
      setPassword(updateClientDTO.getNewPassword());
    }

    if (updateClientDTO.getPhoto() != null && !updateClientDTO.getPhoto().isEmpty()) {
      setPhoto(updateClientDTO.getPhoto());
    }

  }

}
