package com.ttps.quecomemos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.quecomemos.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByEmissionDate(Date date);

  List<Order> findByClientId(Long clientId);

  List<Order> findByDeliveryDate(Date date);

  List<Order> findByTotalGreaterThan(Float total);

  List<Order> findByTotalLessThan(Float total);

  List<Order> findByPaymentMethod(String paymentMethod);

  List<Order> findByEmissionDateBetween(Date startDate, Date endDate);

  List<Order> findByDeliveryDateBetween(Date startDate, Date endDate);
}
