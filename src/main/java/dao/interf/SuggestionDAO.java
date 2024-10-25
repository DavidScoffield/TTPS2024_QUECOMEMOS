package dao.interf;

import java.util.List;

import model.Suggestion;

public interface SuggestionDAO extends GenericDAO<Suggestion> {

  public List<Suggestion> getByType(String type);

  public List<Suggestion> getLikeMessage(String message);

}
