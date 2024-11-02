package com.ttps.quecomemos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.quecomemos.model.Client;
import com.ttps.quecomemos.model.User;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
  User findByEmail(String email);

  User findByDni(String dni);

  User findByEmailAndPassword(String email, String password);

}
