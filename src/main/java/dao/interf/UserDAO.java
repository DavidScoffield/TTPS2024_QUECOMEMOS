package dao.interf;

import java.util.List;

import model.User;

public interface UserDAO extends GenericDAO<User> {
  public User getByEmail(String email);

  public List<User> getByRole(String role);

  public User getByDNI(String dni);

}
