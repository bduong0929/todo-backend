package com.bduong.todo.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.springframework.stereotype.Service;

/**
 * Provides methods for generating a salt and hashing a password using a
 * secure algorithm.
 */
@Service
public class SecurityService {

  private static final int SALT_LENGTH = 16;
  private static final String HASH_ALGORITHM = "SHA-512";

  /**
   * Generates a random salt value to be used in password hashing.
   *
   * @return the generated salt value
   */
  public byte[] generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[SALT_LENGTH];
    random.nextBytes(salt);
    return salt;
  }

  /**
   * Hashes a password using the SHA-512 algorithm and the provided salt.
   *
   * @param password the password to hash
   * @param salt     the salt value to use in the hash
   * @return the hashed password
   * @throws NoSuchAlgorithmException if the specified algorithm is not
   *                                  available
   */
  public byte[] hashingMethod(String password, byte[] salt) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
    md.update(salt);
    return md.digest(password.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Compares two hashed passwords to determine if they are equal.
   *
   * @param expected the expected password
   * @param actual   the actual password
   * @return true if the passwords are equal, false otherwise
   */
  public boolean isSamePassword(byte[] expected, byte[] actual) {
    return MessageDigest.isEqual(expected, actual);
  }
}