package com.ttps.quecomemos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.quecomemos.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository
    extends JpaRepository<ShoppingCart, Long> {
}