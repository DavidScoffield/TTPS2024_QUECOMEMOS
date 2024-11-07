package com.ttps.quecomemos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ttps.quecomemos.dto.LoginUserDTO;
import com.ttps.quecomemos.dto.UpdateClientDTO;
import com.ttps.quecomemos.dto.UserRegisterDTO;
import com.ttps.quecomemos.enums.UserRole;
import com.ttps.quecomemos.model.Client;
import com.ttps.quecomemos.model.ShoppingCart;
import com.ttps.quecomemos.model.User;
import com.ttps.quecomemos.repository.ClientRepository;
import com.ttps.quecomemos.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

  public User registerNewUser(UserRegisterDTO userRegisterDTO) {
    // Check if user exists
    User existingUser = this.findUserByDNI(userRegisterDTO.getDni());
    if (existingUser != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
    }

    if (userRegisterDTO.getRoleSelected() == UserRole.CLIENT) {
      Client newClient = new Client(userRegisterDTO.getDni(), userRegisterDTO.getName(),
          userRegisterDTO.getEmail(), userRegisterDTO.getPassword(),
          userRegisterDTO.getRoleSelected().toString());

      ShoppingCart newCart = new ShoppingCart(newClient);
      newClient.setCart(newCart);

      clientRepository.save(newClient);

      log.info("Client registered successfully: {}", (Client) newClient);

      return newClient;
    } else {
      User newUser = new User(userRegisterDTO.getDni(), userRegisterDTO.getName(),
          userRegisterDTO.getEmail(), userRegisterDTO.getPassword(),
          userRegisterDTO.getRoleSelected().toString());

      log.info("User registered successfully: {}", newUser);

      return userRepository.save(newUser);
    }
  }

  public User authenticateUser(LoginUserDTO loginUserDTO) {
    User existingUser = userRepository.findByDni(loginUserDTO.getDni());
    if (existingUser == null
        || !existingUser.getPassword().equals(loginUserDTO.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
    return existingUser;
  }

  public Client updateClient(String dniOfClient, UpdateClientDTO updateClientDTO) {
    Client existingClient = this.findClientByDNI(dniOfClient);

    if (existingClient == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    // If want to change the password, check if the actual password is correct
    if (updateClientDTO.getActualPassword() != null
        && !updateClientDTO.getActualPassword().isEmpty()) {
      if (!existingClient.getPassword().equals(updateClientDTO.getActualPassword())) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
      }
    }

    existingClient.updateDetails(updateClientDTO);

    return clientRepository.save(existingClient);
  }

  public Client updateClient(Client actualCliemt, UpdateClientDTO updateUserDTO) {

    if (updateUserDTO.getName() != null && !updateUserDTO.getName().isEmpty()) {
      actualCliemt.setName(updateUserDTO.getName());
    }
    if (updateUserDTO.getEmail() != null && !updateUserDTO.getEmail().isEmpty()) {
      actualCliemt.setEmail(updateUserDTO.getEmail());
    }

    if (updateUserDTO.getNewPassword() != null
        && !updateUserDTO.getNewPassword().isEmpty()) {
      actualCliemt.setPassword(updateUserDTO.getNewPassword());
    }

    if (updateUserDTO.getPhoto() != null && !updateUserDTO.getPhoto().isEmpty()) {
      actualCliemt.setPhoto(updateUserDTO.getPhoto());
    }

    return clientRepository.save(actualCliemt);

  }

}
