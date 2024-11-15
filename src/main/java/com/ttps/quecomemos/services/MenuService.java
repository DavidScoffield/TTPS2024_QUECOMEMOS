package com.ttps.quecomemos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ttps.quecomemos.dto.MenuRegisterDTO;
import com.ttps.quecomemos.model.Menu;
import com.ttps.quecomemos.repository.MenuRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MenuService {

  @Autowired
  private MenuRepository menuRepository;

  public Menu findMenuByName(String name) {
    return menuRepository.findByName(name);
  }

  public List<Menu> findVegetarianMenus(Boolean isVegetarian) {
    return menuRepository.findVegetarians(isVegetarian);
  }
  
  public List<Menu> getAllMenus (){
	  return menuRepository.findAll();
  }
  
  
  public Menu registerNewMenu(MenuRegisterDTO menuRegisterDTO) {
    // Check if menu exists
    Menu existingMenu = this.findMenuByName(menuRegisterDTO.getName());
    if (existingMenu != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Menu already exists");
    }

    Menu newMenu = new Menu(menuRegisterDTO.getName(), menuRegisterDTO.getPicture(),
    	menuRegisterDTO.getPrice(), menuRegisterDTO.getFoods());

    menuRepository.save(newMenu);

    log.info("Menu registered successfully: {}", newMenu);

    return newMenu;
  }
  
  public Menu updateMenu(String menuName, MenuRegisterDTO updateMenuDTO) {
  	Menu existingMenu= this.findMenuByName(menuName);
  	if (existingMenu == null) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid menu");
      }

    existingMenu.updateDetails(updateMenuDTO);

    return menuRepository.save(existingMenu);
  }

}
