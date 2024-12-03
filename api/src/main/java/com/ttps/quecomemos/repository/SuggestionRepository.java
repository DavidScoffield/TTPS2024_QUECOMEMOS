package com.ttps.quecomemos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.quecomemos.model.Suggestion;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

  List<Suggestion> findBySuggestionType(String type);

  List<Suggestion> findByMessageContaining(String message);
}
