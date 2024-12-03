package com.ttps.quecomemos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  @NotNull
  private Long id;

  @Column(unique = true)
  @NotNull
  private String dni;

  @NotNull
  private String password;

  @NotNull
  private String name;

  @NotNull
  private String email;

  @NotNull
  private String role;

  public User(String dni, String name, String email, String password, String role) {
    this.dni = dni;
    this.password = password;
    this.name = name;
    this.email = email;
    this.role = role;
  }

  @Override
  public String toString() {
    return "User{name='" + name + "', dni='" + dni + "'}";
  }

}