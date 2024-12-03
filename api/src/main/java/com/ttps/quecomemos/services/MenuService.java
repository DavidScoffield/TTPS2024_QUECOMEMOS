package com.ttps.quecomemos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ttps.quecomemos.dto.MenuRegisterDTO;
import com.ttps.quecomemos.errors.ValidationDataException;
import com.ttps.quecomemos.model.Food;
import com.ttps.quecomemos.model.Menu;
import com.ttps.quecomemos.repository.FoodRepository;
import com.ttps.quecomemos.repository.MenuRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuService {

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private FoodRepository foodRepository;

  public Menu findMenuByName(String name) {
    return menuRepository.findByName(name);
  }

  public List<Menu> findVegetarianMenus(Boolean isVegetarian) {
    return menuRepository.findVegetarians(isVegetarian);
  }

  public List<Menu> getAllMenus() {
    return menuRepository.findAll();
  }

  public Menu registerNewMenu(MenuRegisterDTO menuRegisterDTO) {
    // Check if menu exists
    Menu existingMenu = this.findMenuByName(menuRegisterDTO.getName());
    if (existingMenu != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Menu already exists");
    }

    List<Long> foodIds = menuRegisterDTO.getFoodsIds();
    List<Food> foodEntities = foodRepository.findAllById(foodIds);
    if (foodEntities.size() != foodIds.size()) {
      List<Long> missingIds = foodIds.stream()
          .filter(id -> foodEntities.stream().noneMatch(food -> food.getId().equals(id)))
          .toList();

      throw new ValidationDataException("foodIds",
          "The following food IDs do not exist: " + missingIds);
    }
    menuRegisterDTO.setFoods(foodEntities);
    Menu newMenu = new Menu(menuRegisterDTO.getName(), menuRegisterDTO.getPicture(),
        menuRegisterDTO.getPrice(), menuRegisterDTO.getFoods());

    menuRepository.save(newMenu);

    log.info("Menu registered successfully: {}", newMenu);

    return newMenu;
  }

  public Menu updateMenu(Long menuId, MenuRegisterDTO updateMenuDTO) {
    Optional<Menu> optionalMenu = menuRepository.findById(menuId);

    if (!optionalMenu.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid menu");
    }

    List<Long> foodIds = updateMenuDTO.getFoodsIds();
    List<Food> foodEntities = foodRepository.findAllById(foodIds);
    if (foodEntities.size() != foodIds.size()) {
      List<Long> missingIds = foodIds.stream()
          .filter(id -> foodEntities.stream().noneMatch(food -> food.getId().equals(id)))
          .toList();

      throw new ValidationDataException("foodIds",
          "The following food IDs do not exist: " + missingIds);
    }
    updateMenuDTO.setFoods(foodEntities);
    Menu existingMenu = optionalMenu.get();

    // Update menu
    if (!updateMenuDTO.getName().isEmpty()) {
      existingMenu.setName(updateMenuDTO.getName());
    }
    existingMenu.setPrice(updateMenuDTO.getPrice());

    existingMenu.setFoods(updateMenuDTO.getFoods());

    existingMenu.setPicture(updateMenuDTO.getPicture());

    return menuRepository.save(existingMenu);
  }

}
