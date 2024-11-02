package com.ttps.quecomemos.dao.interf;

import java.util.List;

import com.ttps.quecomemos.model.Suggestion;

public interface SuggestionDAO extends GenericDAO<Suggestion> {

  public List<Suggestion> getByType(String type);

  public List<Suggestion> getLikeMessage(String message);

}
