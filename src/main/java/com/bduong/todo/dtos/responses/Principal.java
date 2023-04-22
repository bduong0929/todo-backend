package com.bduong.todo.dtos.responses;

import java.util.List;

import com.bduong.todo.entities.Task;
import com.bduong.todo.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Principal {
  private String id;
  private String username;
  private String token;

  public Principal(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }

  public Principal(String id, String username) {
    this.id = id;
    this.username = username;
  }
}
