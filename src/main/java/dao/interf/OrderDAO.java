package dao.interf;

import java.util.Date;
import java.util.List;

import model.Order;

public interface OrderDAO extends GenericDAO<Order> {
  public List<Order> getByEmissionDate(Date date);

  public List<Order> getByDeliveryDate(Date date);

  public List<Order> getByClientId(Long clientId);

  public List<Order> getHigherTotal(Float total);

  public List<Order> getLowerTotal(Float total);

  public List<Order> getByPaymentMethod(String paymentMethod);

  public List<Order> getBetweenEmissionDates(Date startDate, Date endDate);

  public List<Order> getBetweenDeliveryDates(Date startDate, Date endDate);

}
