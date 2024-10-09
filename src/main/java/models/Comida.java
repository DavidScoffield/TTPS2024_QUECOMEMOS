package models;

public class Comida {
  private Long id;
  private String nombre;
  private Boolean esVegatariano;
  private String tipo; 

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

    public Boolean getEsVegatariano() {
        return esVegatariano;
    }

    public void setEsVegatariano(Boolean esVegatariano) {
        this.esVegatariano = esVegatariano;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
