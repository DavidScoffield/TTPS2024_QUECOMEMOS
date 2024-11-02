package com.ttps.quecomemos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttps.quecomemos.model.Menu;
import com.ttps.quecomemos.repository.MenuRepository;

@Service
public class MenuService {

  @Autowired
  private MenuRepository menuRepository;

  public Menu findMenuByName(String name) {
    return menuRepository.findByName(name);
  }

  public List<Menu> findVegetarianMenus(Boolean isVegetarian) {
    return menuRepository.findVegetarians(isVegetarian);
  }

}
