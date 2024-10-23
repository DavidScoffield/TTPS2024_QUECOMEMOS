package model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;

  private Date emissionDate;

  private String paymentMethod;

  private Float total;

  private String qrCode;

  private Date deliveryDate;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToMany
  @JoinTable(name = "order_menu", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
  private List<Menu> menus;

  public Order(Date emissionDate, String paymentMethod, Float total,
      String qrCode, Date deliveryDate, Client client, List<Menu> menus) {
    this.emissionDate = emissionDate;
    this.paymentMethod = paymentMethod;
    this.total = total;
    this.qrCode = qrCode;
    this.deliveryDate = deliveryDate;
    this.client = client;
  }

}
