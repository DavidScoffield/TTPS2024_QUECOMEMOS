package com.ttps.quecomemos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttps.quecomemos.model.Client;
import com.ttps.quecomemos.model.User;
import com.ttps.quecomemos.repository.ClientRepository;
import com.ttps.quecomemos.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ClientRepository clientRepository;

  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User findUserByDNI(String dni) {
    return userRepository.findByDni(dni);
  }

  public List<User> findUsersByRole(String role) {
    return userRepository.findByRole(role);
  }

  public Client findClientById(Long id) {
    return clientRepository.findById(id).orElse(null);
  }

  public Client findClientByEmail(String email) {
    return (Client) clientRepository.findByEmail(email);
  }

  public Client findClientByDNI(String dni) {
    return (Client) clientRepository.findByDni(dni);
  }

  public Client findClientByEmailAndPassword(String email, String password) {
    Client client = (Client) clientRepository.findByEmail(email);
    if (client != null && client.getPassword().equals(password)) {
      return client;
    }
    return null;
  }

  public User registerUser(User user) {
    return userRepository.save(user);
  }

  public Client registerClient(Client client) {
    return clientRepository.save(client);
  }

}
