package com.example.kanban.repository;

import com.example.kanban.model.User;
import com.example.kanban.model.taskStatus;
import org.springframework.stereotype.Repository;
import com.example.kanban.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitle(String title);
    List<Task> findAllByUser(User user);
}
