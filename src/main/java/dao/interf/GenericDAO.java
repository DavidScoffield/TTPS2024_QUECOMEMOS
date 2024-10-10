package dao.interf;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T> {
  public boolean exist(Long id);

  public T save(T entity);

  public T update(T entity);

  public void delete(T entity);

  public T delete(Long id);

  public T get(Serializable id);

  public List<T> getAll(String column);

  public List<T> getAll();

}
