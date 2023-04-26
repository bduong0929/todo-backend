package com.bduong.todo.controllers;

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

import com.bduong.todo.dtos.requests.NewTaskRequest;
import com.bduong.todo.services.TaskService;
import com.bduong.todo.utils.custom_exceptions.UserNotFoundException;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
  private final TaskService taskService;

  @PostMapping("/create")
  public ResponseEntity<?> createTask(@RequestBody NewTaskRequest req) {
    taskService.createTask(req);
    return ResponseEntity.ok("Task created successfully.");
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException e) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", new Date(System.currentTimeMillis()));
    body.put("message", e.getMessage());
    return ResponseEntity.badRequest().body(body);
  }
}
