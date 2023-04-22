package com.bduong.todo.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

import com.bduong.todo.dtos.responses.Principal;
import com.bduong.todo.entities.Task;
import com.bduong.todo.utils.JwtConfig;
import com.bduong.todo.utils.custom_exceptions.InvalidTokenException;

/**
 * A service class for managing JWT tokens in a Spring Boot application.
 */
@Service
public class TokenService {

  private final JwtConfig jwtConfig;

  public TokenService(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  /**
   * Generates a JWT token for the provided Principal.
   *
   * @param subject the Principal for which the token will be generated
   * @return a JWT token as a string
   * @throws NullPointerException if the subject is null
   */
  public String generateToken(Principal subject) {
    Objects.requireNonNull(subject, "Subject cannot be null");

    Instant now = Instant.now();
    Instant expirationTime = now.plusMillis(jwtConfig.getExpiration());

    JwtBuilder tokenBuilder = Jwts.builder()
        .setId(subject.getId())
        .setIssuer("todo")
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(expirationTime))
        .setSubject(subject.getUsername())
        .signWith(jwtConfig.getSignatureAlgorithm(),
            jwtConfig.getSigningKey());

    return tokenBuilder.compact();
  }

  /**
   * Extracts the Principal from the provided JWT token.
   *
   * @param token the JWT token as a string
   * @return a Principal instance
   * @throws InvalidTokenException if the provided token is invalid
   */
  public Principal getPrincipal(String token) throws InvalidTokenException {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtConfig.getSigningKey())
        .parseClaimsJws(token)
        .getBody();

    return new Principal(claims.getId(), claims.getSubject());
  }
}