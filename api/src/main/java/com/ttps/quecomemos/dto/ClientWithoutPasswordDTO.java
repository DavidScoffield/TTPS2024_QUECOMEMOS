package com.ttps.quecomemos.dto;

import java.util.List;

import com.ttps.quecomemos.model.Order;
import com.ttps.quecomemos.model.ShoppingCart;
import com.ttps.quecomemos.model.Suggestion;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientWithoutPasswordDTO extends UserWithoutPasswordDTO {

  private String photo;

  private ShoppingCart cart;

  private List<Order> orders;

  private List<Suggestion> suggestions;

  public ClientWithoutPasswordDTO(Long id, String dni, String name, String email,
      String role, String photo, ShoppingCart cart) {
    super(id, dni, name, email, role);
    this.photo = photo;
    this.cart = cart;
  }
}
