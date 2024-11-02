package com.ttps.quecomemos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttps.quecomemos.model.Suggestion;
import com.ttps.quecomemos.repository.SuggestionRepository;

@Service
public class SuggestionService {

  @Autowired
  private SuggestionRepository suggestionRepository;

  public List<Suggestion> getAllSuggestions() {
    return suggestionRepository.findAll();
  }

  public Optional<Suggestion> getSuggestionById(Long id) {
    return suggestionRepository.findById(id);
  }

  public List<Suggestion> getSuggestionsByType(String type) {
    return suggestionRepository.findBySuggestionType(type);
  }

  public List<Suggestion> getSuggestionsLikeMessage(String message) {
    return suggestionRepository.findByMessageContaining(message);
  }

  public Suggestion saveSuggestion(Suggestion suggestion) {
    return suggestionRepository.save(suggestion);
  }

  public void deleteSuggestion(Long id) {
    suggestionRepository.deleteById(id);
  }
}
