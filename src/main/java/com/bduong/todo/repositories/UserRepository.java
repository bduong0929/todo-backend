package com.bduong.todo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bduong.todo.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  // Get user by username
  Optional<User> findByUsername(String username);

  // Get user by id
  Optional<User> findById(String id);
}
