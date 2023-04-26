package com.bduong.todo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bduong.todo.dtos.requests.NewTaskRequest;
import com.bduong.todo.services.TaskService;

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
}
