package com.ttps.quecomemos.dao.interf;

import java.util.List;

import com.ttps.quecomemos.model.Menu;

public interface MenuDAO extends GenericDAO<Menu> {

  public Menu getByName(String name);

  public List<Menu> getVegetarians(Boolean isVegetarian);

}
