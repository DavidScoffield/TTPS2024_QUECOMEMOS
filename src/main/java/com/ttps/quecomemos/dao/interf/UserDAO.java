package com.ttps.quecomemos.dao.interf;

import java.util.List;

import com.ttps.quecomemos.model.User;

public interface UserDAO extends GenericDAO<User>, UserGenericDAO {
  public List<User> getByRole(String role);

}
