package com.ttps.quecomemos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ttps.quecomemos.dto.ClientWithoutPasswordDTO;
import com.ttps.quecomemos.dto.LoginUserDTO;
import com.ttps.quecomemos.dto.UpdateClientDTO;
import com.ttps.quecomemos.dto.UserRegisterDTO;
import com.ttps.quecomemos.dto.UserWithoutPasswordDTO;
import com.ttps.quecomemos.enums.UserRole;
import com.ttps.quecomemos.model.Client;
import com.ttps.quecomemos.model.ShoppingCart;
import com.ttps.quecomemos.model.User;
import com.ttps.quecomemos.repository.ClientRepository;
import com.ttps.quecomemos.repository.UserRepository;
import com.ttps.quecomemos.util.PasswordUtil;

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

  public UserWithoutPasswordDTO registerNewUser(UserRegisterDTO userRegisterDTO) {
    // Check if user exists
    User existingUser = this.findUserByDNI(userRegisterDTO.getDni());
    if (existingUser != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
    }

    // Hash password
    String hashedPassword = PasswordUtil.hashPassword(userRegisterDTO.getPassword());
    userRegisterDTO.setPassword(hashedPassword);

    if (userRegisterDTO.getRoleSelected() == UserRole.CLIENT) {
      Client newClient = new Client(userRegisterDTO.getDni(), userRegisterDTO.getName(),
          userRegisterDTO.getEmail(), userRegisterDTO.getPassword(),
          userRegisterDTO.getRoleSelected().toString());

      ShoppingCart newCart = new ShoppingCart(newClient);
      newClient.setCart(newCart);

      clientRepository.save(newClient);

      log.info("Client registered successfully: {}", (Client) newClient);

      ClientWithoutPasswordDTO clientWithoutPasswordDTO = new ClientWithoutPasswordDTO(
          newClient.getId(), newClient.getDni(), newClient.getName(),
          newClient.getEmail(), newClient.getRole(), newClient.getPhoto(),
          newClient.getCart());

      return clientWithoutPasswordDTO;
    } else {
      User newUser = new User(userRegisterDTO.getDni(), userRegisterDTO.getName(),
          userRegisterDTO.getEmail(), userRegisterDTO.getPassword(),
          userRegisterDTO.getRoleSelected().toString());

      userRepository.save(newUser);

      log.info("User registered successfully: {}", newUser);

      UserWithoutPasswordDTO userWithoutPasswordDTO = new UserWithoutPasswordDTO(
          newUser.getId(), newUser.getDni(), newUser.getName(), newUser.getEmail(),
          newUser.getRole());

      return userWithoutPasswordDTO;
    }
  }

  public UserWithoutPasswordDTO authenticateUser(LoginUserDTO loginUserDTO) {
    User existingUser = userRepository.findByDni(loginUserDTO.getDni());

    if (existingUser == null || !PasswordUtil.matchesPassword(loginUserDTO.getPassword(),
        existingUser.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    UserWithoutPasswordDTO user;

    if (existingUser.getRole().equals(UserRole.CLIENT.toString())) {
      Client client = (Client) existingUser;
      user = new ClientWithoutPasswordDTO(client.getId(), client.getDni(),
          client.getName(), client.getEmail(), client.getRole(), client.getPhoto(),
          client.getCart());
    } else {
      user = new UserWithoutPasswordDTO(existingUser.getId(), existingUser.getDni(),
          existingUser.getName(), existingUser.getEmail(), existingUser.getRole());
    }

    return user;

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

    // Update client
    if (updateClientDTO.getName() != null && !updateClientDTO.getName().isEmpty()) {
      existingClient.setName(updateClientDTO.getName());
    }
    if (updateClientDTO.getEmail() != null && !updateClientDTO.getEmail().isEmpty()) {
      existingClient.setEmail(updateClientDTO.getEmail());
    }

    if (updateClientDTO.getNewPassword() != null
        && !updateClientDTO.getNewPassword().isEmpty()) {
      existingClient.setPassword(updateClientDTO.getNewPassword());
    }

    if (updateClientDTO.getPhoto() != null && !updateClientDTO.getPhoto().isEmpty()) {
      existingClient.setPhoto(updateClientDTO.getPhoto());
    }

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
