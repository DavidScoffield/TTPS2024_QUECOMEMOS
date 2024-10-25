package dao.interf;

import model.User;

public interface UserGenericDAO {
  public User getByEmail(String email);

  public User getByDNI(String dni);

  public User getByEmailAndPassword(String email, String password);

}
