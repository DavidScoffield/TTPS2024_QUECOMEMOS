package models;

import java.util.List;

public class Menu {
  private Long id;
  private String nombre;
  private String imagen;
  private Float precio;

    public List<Comida> getComidas() {
        return comidas;
    }

    public void setComidas(List<Comida> comidas) {
        this.comidas = comidas;
    }

    private List<Comida> comidas;


  public Long getId() {
      return id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public String getNombre() {
      return nombre;
  }

  public void setNombre(String nombre) {
      this.nombre = nombre;
  }

  public String getImagen() {
      return imagen;
  }

  public void setImagen(String imagen) {
      this.imagen = imagen;
  }

  public Float getPrecio() {
      return precio;
  }

  public void setPrecio(Float precio) {
      this.precio = precio;
  }

}
