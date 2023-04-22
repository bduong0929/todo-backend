package com.bduong.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bduong.todo.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
}
