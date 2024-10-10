package dao.interf;

import java.util.List;

import model.Food;

public interface FoodDAO extends GenericDAO<Food> {
  public Food getByName(String name);

  public List<Food> getByType(String type);

  public List<Food> getVegetarians(Boolean isVegetarian);

  public List<Food> getNonVegetarians(Boolean isVegetarian);
}
