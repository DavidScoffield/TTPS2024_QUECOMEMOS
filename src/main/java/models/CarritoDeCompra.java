package models;

import java.util.List;

public class CarritoDeCompra {
  private Long id;
  private List<Menu> menues;
  private Cliente cliente;

  public Long getId() {
      return id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public List<Menu> getMenues() {
      return menues;
  }

  public void setMenues(List<Menu> menues) {
      this.menues = menues;
  }

  public Cliente getCliente() {
      return cliente;
  }

  public void setCliente(Cliente cliente) {
      this.cliente = cliente;
  }
  
   
}
