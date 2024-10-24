package dao.interf;

import java.util.Date;
import java.util.List;

import model.Order;

public interface OrderDAO extends GenericDAO<Order> {
	public List<Order> getByEmissionDate(Date date);
	
	public List<Order> getByClientId(Long clientId);
}
