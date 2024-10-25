package dao.interf;

import java.util.List;

import model.User;

public interface UserDAO extends GenericDAO<User>, UserGenericDAO {
  public List<User> getByRole(String role);

}
