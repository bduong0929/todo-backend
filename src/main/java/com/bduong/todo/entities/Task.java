package com.bduong.todo.entities;

import java.util.UUID;

import com.bduong.todo.dtos.requests.NewTaskRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "tasks")
public class Task {
  @Id
  private String id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "completed", nullable = false)
  private boolean completed;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public Task(NewTaskRequest req, User user) {
    this.id = UUID.randomUUID().toString();
    this.title = req.getTitle();
    this.completed = false;
    this.user = user;
  }
}
