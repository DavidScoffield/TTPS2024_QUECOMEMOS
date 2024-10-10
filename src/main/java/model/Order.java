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

  public Order() {
  }

  public Order(Date emissionDate, String paymentMethod, Float total,
      String qrCode, Date deliveryDate, Client client, List<Menu> menus) {
    this.emissionDate = emissionDate;
    this.paymentMethod = paymentMethod;
    this.total = total;
    this.qrCode = qrCode;
    this.deliveryDate = deliveryDate;
    this.client = client;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getEmissionDate() {
    return emissionDate;
  }

  public void setEmissionDate(Date emissionDate) {
    this.emissionDate = emissionDate;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public Float getTotal() {
    return total;
  }

  public void setTotal(Float total) {
    this.total = total;
  }

  public String getQrCode() {
    return qrCode;
  }

  public void setQrCode(String qrCodeValue) {
    this.qrCode = qrCodeValue;
  }

  public Date getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(Date deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public List<Menu> getMenus() {
    return menus;
  }

  public void setMenus(List<Menu> menus) {
    this.menus = menus;
  }

}
