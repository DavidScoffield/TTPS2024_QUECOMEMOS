package com.ttps.quecomemos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.quecomemos.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

  User findByDni(String dni);

  User findByEmailAndPassword(String email, String password);

  List<User> findByRole(String role);
}
