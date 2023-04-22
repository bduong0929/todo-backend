package com.bduong.todo.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration class for JWT generation and validation.
 */
@Component
public class JwtConfig {
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
  private final Key signingKey;
  private final int expiration;

  /**
   * Creates a new instance of JwtConfig with the specified secret key.
   *
   * @param salt The secret key used to sign and validate JWTs.
   */
  public JwtConfig(@Value("${jwt.salt}") String salt) {
    // Convert the secret key from Base64 to bytes
    byte[] saltyBytes = DatatypeConverter.parseBase64Binary(salt);

    // Create a new secret key using the specified algorithm
    this.signingKey = new SecretKeySpec(saltyBytes, signatureAlgorithm.getJcaName());

    // Set the default expiration time for JWTs to 1 hour
    this.expiration = 60 * 60 * 1000;
  }

  /**
   * Gets the default expiration time for JWTs in milliseconds.
   *
   * @return The default expiration time for JWTs in milliseconds.
   */
  public int getExpiration() {
    return expiration;
  }

  /**
   * Gets the default signature algorithm used for JWTs.
   *
   * @return The default signature algorithm used for JWTs.
   */
  public SignatureAlgorithm getSignatureAlgorithm() {
    return signatureAlgorithm;
  }

  /**
   * Gets the secret key used to sign and validate JWTs.
   *
   * @return The secret key used to sign and validate JWTs.
   */
  public Key getSigningKey() {
    return signingKey;
  }
}