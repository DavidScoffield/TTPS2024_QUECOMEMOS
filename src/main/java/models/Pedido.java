package models;

import java.util.Date;
import java.util.List;

public class Pedido {
  private Long id;
  private Date fechaEmision;
  private String medioPago;
  private Float total;
  private String codigoQR; 
  private Date fechaEntrega;
  private Cliente cliente; 
  private List<Menu> menues;

  public Long getId() {
      return id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public Date getFechaEmision() {
      return fechaEmision;
  }

  public void setFechaEmision(Date fechaEmision) {
      this.fechaEmision = fechaEmision;
  }

  public String getMedioPago() {
      return medioPago;
  }

  public void setMedioPago(String medioPago) {
      this.medioPago = medioPago;
  }

  public Float getTotal() {
      return total;
  }

  public void setTotal(Float total) {
      this.total = total;
  }

  public String getCodigoQR() {
      return codigoQR;
  }

  public void setCodigoQR(String codigoQR) {
      this.codigoQR = codigoQR;
  }

  public Date getFechaEntrega() {
      return fechaEntrega;
  }

  public void setFechaEntrega(Date fechaEntrega) {
      this.fechaEntrega = fechaEntrega;
  }

  public Cliente getCliente() {
      return cliente;
  }

  public void setCliente(Cliente cliente) {
      this.cliente = cliente;
  }

  public List<Menu> getMenues() {
      return menues;
  }

  public void setMenues(List<Menu> menues) {
      this.menues = menues;
  }

}
