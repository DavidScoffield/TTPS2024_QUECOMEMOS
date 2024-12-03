package com.ttps.quecomemos.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttps.quecomemos.model.Order;
import com.ttps.quecomemos.repository.OrderRepository;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  public List<Order> getOrdersByEmissionDate(Date date) {
    return orderRepository.findByEmissionDate(date);
  }

  public List<Order> getOrdersByClientId(Long clientId) {
    return orderRepository.findByClientId(clientId);
  }

  public List<Order> getOrdersByDeliveryDate(Date date) {
    return orderRepository.findByDeliveryDate(date);
  }

  public List<Order> getOrdersWithHigherTotal(Float total) {
    return orderRepository.findByTotalGreaterThan(total);
  }

  public List<Order> getOrdersWithLowerTotal(Float total) {
    return orderRepository.findByTotalLessThan(total);
  }

  public List<Order> getOrdersByPaymentMethod(String paymentMethod) {
    return orderRepository.findByPaymentMethod(paymentMethod);
  }

  public List<Order> getOrdersBetweenEmissionDates(Date startDate,
      Date endDate) {
    return orderRepository.findByEmissionDateBetween(startDate, endDate);
  }

  public List<Order> getOrdersBetweenDeliveryDates(Date startDate,
      Date endDate) {
    return orderRepository.findByDeliveryDateBetween(startDate, endDate);
  }

  public Order saveOrder(Order order) {
    return orderRepository.save(order);
  }

  public void deleteOrder(Long orderId) {
    orderRepository.deleteById(orderId);
  }

  public Order getOrderById(Long orderId) {
    return orderRepository.findById(orderId).orElse(null);
  }
}
