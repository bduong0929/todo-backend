package com.bduong.todo.services;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bduong.todo.dtos.responses.Principal;
import com.bduong.todo.entities.User;
import com.bduong.todo.repositories.UserRepository;
import com.bduong.todo.utils.custom_exceptions.UserNotFoundException;

@Service
public class UserService {
  private final UserRepository userRepo;
  private final SecurityService securityService;

  public UserService(UserRepository userRepo, SecurityService securityService) {
    this.userRepo = userRepo;
    this.securityService = securityService;
  }

  /**
   * Register a user
   * 
   * @param username username to register
   * @param password password to register
   * @return User object if register is successful, else null
   * @throws NoSuchAlgorithmException if the algorithm used to hash the password
   *                                  is not found
   */
  public User register(String username, String password) throws NoSuchAlgorithmException {
    byte[] salt = securityService.generateSalt();
    byte[] hashedPassword = securityService.hashingMethod(password, salt);
    User createdUser = new User(username, hashedPassword, salt);
    userRepo.save(createdUser);
    return createdUser;
  }

  /**
   * Login a user
   * 
   * @param username username to login
   * @param password password to login
   * @return Principal object if login is successful, else null
   * @throws NoSuchAlgorithmException if the algorithm used to hash the password
   */
  public Principal login(String username, String password) throws NoSuchAlgorithmException {
    Optional<User> userOptional = userRepo.findByUsername(username);

    if (userOptional.isEmpty())
      throw new UserNotFoundException("Invalid username or password.");

    User foundUser = userOptional.get();
    byte[] exptectedHashedPassword = securityService.hashingMethod(password, foundUser.getSalt());
    byte[] actualHashedPassword = foundUser.getPassword();

    if (securityService.isSamePassword(exptectedHashedPassword, actualHashedPassword)) {
      return new Principal(foundUser);
    }
    throw new UserNotFoundException("Invalid username or password.");
  }

  /**
   * Check if username is valid
   * 
   * @param username username to check
   * @return true if username is valid, else false
   */
  public boolean isValidUsername(String username) {
    return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
  }

  /**
   * Check if username is already taken
   * 
   * @param username username to check
   * @return true if username is already taken, else false
   */
  public boolean isUniqueUsername(String username) {
    Optional<User> user = userRepo.findByUsername(username);
    return !user.isPresent();
  }

  /**
   * Check if username is already taken
   * 
   * @param password password to check
   * @return true if username is already taken, else false
   */
  public boolean isValidPassword(String password) {
    return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
  }

  /**
   * Check if password and confirm password are the same
   * 
   * @param p1 password
   * @param p2 confirm password
   * @return true if password and confirm password are the same, else false
   */
  public boolean isSamePassword(String p1, String p2) {
    return p1.equals(p2);
  }
}
