package dao.interf;

import java.util.List;

import model.Menu;

public interface MenuDAO extends GenericDAO<Menu> {

  public Menu getByName(String name);

  public List<Menu> getVegetarians(Boolean isVegetarian);

}
