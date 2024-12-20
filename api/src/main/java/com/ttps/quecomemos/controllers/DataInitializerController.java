package com.ttps.quecomemos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttps.quecomemos.dto.FoodRegisterDTO;
import com.ttps.quecomemos.dto.UserRegisterDTO;
import com.ttps.quecomemos.enums.FoodType;
import com.ttps.quecomemos.enums.UserRole;
import com.ttps.quecomemos.services.FoodService;
import com.ttps.quecomemos.services.UserService;

@RestController
public class DataInitializerController {

  @Autowired
  private UserService userService;

  @Autowired
  private FoodService foodService;

  // @Autowired
  // private MenuService menuService;
  // @Autowired
  // private OrderService orderService;
  // @Autowired
  // private SuggestionService suggestionService;
  @GetMapping("/initialize-data")
  public String initializeData() {

    // Initialize users
    userService.registerNewUser(new UserRegisterDTO("12341234", "password",
        "david scoffield", "david@gmail.com", UserRole.CLIENT));
    userService.registerNewUser(new UserRegisterDTO("12341235", "password", "john doe",
        "john@gmail.com", UserRole.CLIENT));
    userService.registerNewUser(new UserRegisterDTO("1111111", "password", "admin",
        "admin@admin.com", UserRole.ADMIN));
    userService.registerNewUser(new UserRegisterDTO("2222222", "responsable",
        "responsable", "responsable@gmail.com", UserRole.SHIFT_MANAGER));

    foodService.registerNewFood(
        new FoodRegisterDTO("Hamburguesa", FoodType.PLATO_PRINCIPAL, false));
    foodService
        .registerNewFood(new FoodRegisterDTO("Papas Fritas", FoodType.ENTRADA, true));
    foodService.registerNewFood(new FoodRegisterDTO("Helado", FoodType.POSTRE, true));
    foodService.registerNewFood(new FoodRegisterDTO("Gaseosa", FoodType.BEBIDA, true));
    foodService.registerNewFood(
        new FoodRegisterDTO("Ravioles De Verdura", FoodType.PLATO_PRINCIPAL, true));

    return "Datos iniciales cargados en la base de datos.";
  }
}
