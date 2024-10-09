package models;

public class Sugerencia {
  private Long id;
  private String mensaje;
  private String tipoSugerencia;
  private Cliente cliente;

  public Long getId() {
      return id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public String getMensaje() {
      return mensaje;
  }

  public void setMensaje(String mensaje) {
      this.mensaje = mensaje;
  }

  public String getTipoSugerencia() {
      return tipoSugerencia;
  }

  public void setTipoSugerencia(String tipoSugerencia) {
      this.tipoSugerencia = tipoSugerencia;
  }

  public Cliente getCliente() {
      return cliente;
  }

  public void setCliente(Cliente cliente) {
      this.cliente = cliente;
  }

}
