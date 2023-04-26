package com.bduong.todo.controllers;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bduong.todo.dtos.requests.NewLoginRequest;
import com.bduong.todo.dtos.requests.NewRegisterRequest;
import com.bduong.todo.dtos.responses.Principal;
import com.bduong.todo.services.TokenService;
import com.bduong.todo.services.UserService;
import com.bduong.todo.utils.custom_exceptions.InvalidAuthException;
import com.bduong.todo.utils.custom_exceptions.InvalidTokenException;
import com.bduong.todo.utils.custom_exceptions.UserNotFoundException;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  private final UserService userService;
  private final TokenService tokenService;

  /**
   * Registers a new user
   * 
   * @param req - NewRegisterRequest object
   * @return ResponseEntity with a message saying that the user has been created
   *         successfully
   * @throws NoSuchAlgorithmException - if the algorithm used to hash the password
   *                                  is not found
   */
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody NewRegisterRequest req) throws NoSuchAlgorithmException {
    String username = req.getUsername();
    String p1 = req.getPassword1();
    String p2 = req.getPassword2();

    if (!userService.isValidUsername(username)) {
      throw new InvalidAuthException(
          "Username must be at least 8-20 characters long.");
    }

    if (!userService.isUniqueUsername(username)) {
      throw new InvalidAuthException("Username is already taken.");
    }

    if (!userService.isValidPassword(p1)) {
      throw new InvalidAuthException(
          "Password must be at least 1 letter, 1 number, and 1 special character.");
    }

    if (!userService.isSamePassword(p1, p2)) {
      throw new InvalidAuthException("Passwords do not match.");
    }

    userService.register(username, p1);
    return ResponseEntity.ok("User created successfully"); // return status ok, and a message saying that the user has
                                                           // been created successfully
  }

  /**
   * Logs in a user
   * 
   * @param req - NewLoginRequest object
   * @return ResponseEntity with a Principal object
   * @throws NoSuchAlgorithmException - if the algorithm used to hash the password
   */
  @PostMapping("/login")
  public ResponseEntity<Principal> login(@RequestBody NewLoginRequest req) throws NoSuchAlgorithmException {
    String username = req.getUsername();
    String password = req.getPassword();

    Principal principal = userService.login(username, password);
    String token = tokenService.generateToken(principal);
    principal.setToken(token);
    return ResponseEntity.ok(principal);
  }

  /**
   * Handles NoSuchAlgorithmException
   * 
   * @param e - NoSuchAlgorithmException object
   * @return ResponseEntity with a message saying that the algorithm used to hash
   *         the password is not found
   */
  @ExceptionHandler(NoSuchAlgorithmException.class)
  public ResponseEntity<Map<String, Object>> handleNoSuchAlgorithmException(NoSuchAlgorithmException e) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", new Date(System.currentTimeMillis()));
    body.put("message", e.getMessage());
    return ResponseEntity.badRequest().body(body);
  }

  /**
   * Handles InvalidAuthException
   * 
   * @param e - InvalidAuthException object
   * @return ResponseEntity with a message saying that the username or password is
   *         invalid
   */
  @ExceptionHandler(InvalidAuthException.class)
  public ResponseEntity<Map<String, Object>> handleInvalidAuthException(InvalidAuthException e) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", new Date(System.currentTimeMillis()));
    body.put("message", e.getMessage());
    return ResponseEntity.badRequest().body(body);
  }

  /**
   * Handles InvalidTokenException
   * 
   * @param e - InvalidTokenException object
   * @return ResponseEntity with a message saying that the token is invalid
   */
  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<Map<String, Object>> handleInvalidTokenException(InvalidTokenException e) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", new Date(System.currentTimeMillis()));
    body.put("message", e.getMessage());
    return ResponseEntity.badRequest().body(body);
  }

  /**
   * Handles UserNotFoundException
   * 
   * @param e - UserNotFoundException object
   * @return ResponseEntity with a message saying that the user is not found
   */
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException e) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", new Date(System.currentTimeMillis()));
    body.put("message", e.getMessage());
    return ResponseEntity.badRequest().body(body);
  }
}
