package com.bduong.todo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bduong.todo.dtos.requests.NewTaskRequest;
import com.bduong.todo.entities.Task;
import com.bduong.todo.entities.User;
import com.bduong.todo.repositories.TaskRepository;
import com.bduong.todo.utils.custom_exceptions.UserNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TaskService {
  private final TaskRepository taskRepository;
  private final UserService userService;

  /**
   * Create a new task
   * 
   * @param req - Contains the title of the task and the user id
   * @return Task object if successful, else null
   */
  public Task createTask(NewTaskRequest req) {
    Optional<User> userOptional = userService.getUserById(req.getUserId());

    if (!userOptional.isPresent()) {
      throw new UserNotFoundException("User not found");
    }

    Task newTask = new Task(req, userOptional.get());
    return taskRepository.save(newTask);
  }
}
