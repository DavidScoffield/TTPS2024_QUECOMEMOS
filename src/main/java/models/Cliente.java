package models;

import java.util.List;

public class Cliente extends Usuario {
  private String foto;
  private CarritoDeCompra carrito; 
  private List<Pedido> pedidos; 
  private List<Sugerencia> sugerencias;

  public String getFoto() {
      return foto;
  }

  public void setFoto(String foto) {
      this.foto = foto;
  }

  public CarritoDeCompra getCarrito() {
      return carrito;
  }

  public void setCarrito(CarritoDeCompra carrito) {
      this.carrito = carrito;
  }

  public List<Pedido> getPedidos() {
      return pedidos;
  }

  public void setPedidos(List<Pedido> pedidos) {
      this.pedidos = pedidos;
  }

  public List<Sugerencia> getSugerencias() {
      return sugerencias;
  }

  public void setSugerencias(List<Sugerencia> sugerencias) {
      this.sugerencias = sugerencias;
  }
}
