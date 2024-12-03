package com.ttps.quecomemos.services;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ttps.quecomemos.model.Client;
import com.ttps.quecomemos.model.User;
import com.ttps.quecomemos.repository.ClientRepository;
import com.ttps.quecomemos.repository.UserRepository;

@SpringBootTest
// @ActiveProfiles("test") // Use the test profile
public class UserServiceTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private UserService userService;

  private User testUser;
  private Client testClient;

  @BeforeEach
  public void setUp() {
    // Initialize test data
    testUser = new User();
    testUser.setEmail("user@example.com");
    testUser.setDni("123456");
    testUser.setRole("ROLE_USER");
    userRepository.save(testUser); // Save to the test database

    testClient = new Client();
    testClient.setEmail("client@example.com");
    testClient.setDni("654321");
    testClient.setPassword("password123");
    clientRepository.save(testClient); // Save to the test database
  }

  @AfterEach
  public void tearDown() {
    // Clear the test data after each test
    clientRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  public void testFindUserByEmail() {
    User result = userService.findUserByEmail("user@example.com");

    assertNotNull(result);
    assertEquals("user@example.com", result.getEmail());
  }

  @Test
  public void testFindUserByDNI() {
    User result = userService.findUserByDNI("123456");

    assertNotNull(result);
    assertEquals("123456", result.getDni());
  }

  @Test
  public void testFindClientByEmail() {
    Client result = userService.findClientByEmail("client@example.com");

    assertNotNull(result);
    assertEquals("client@example.com", result.getEmail());
  }

  @Test
  public void testFindClientByDNI() {
    Client result = userService.findClientByDNI("654321");

    assertNotNull(result);
    assertEquals("654321", result.getDni());
  }

  @Test
  public void testFindClientByEmailAndPassword_Success() {
    Client result = userService
        .findClientByEmailAndPassword("client@example.com", "password123");

    assertNotNull(result);
    assertEquals("client@example.com", result.getEmail());
  }

  @Test
  public void testFindClientByEmailAndPassword_Failure() {
    Client result = userService
        .findClientByEmailAndPassword("client@example.com", "wrongpassword");

    assertNull(result);
  }
}
