package com.ttps.quecomemos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ttps.quecomemos.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

  Menu findByName(String name);

  @Query("SELECT m FROM Menu m WHERE "
      + "(:isVegetarian = true AND NOT EXISTS (SELECT f FROM m.foods f WHERE f.isVegetarian = false)) OR "
      + "(:isVegetarian = false AND EXISTS (SELECT f FROM m.foods f WHERE f.isVegetarian = false))")
  List<Menu> findVegetarians(Boolean isVegetarian);
}
