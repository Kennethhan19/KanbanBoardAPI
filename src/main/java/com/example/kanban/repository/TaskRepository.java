package com.example.kanban.repository;

import com.example.kanban.model.Task.taskStatus;
import org.springframework.stereotype.Repository;
import com.example.kanban.model.Task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitle(String title);
    List<Task> findAllByUserId(Long userId);
    List<Task> findAllByBoardId(Long boardId);
    List<Task> findAllByBoardIdAndUserId(Long boardId, Long userId);
    List<Task> findAllByBoardIdAndStatus(Long boardId, taskStatus status);
    List<Task> findAllByBoardIdAndStatusAndUserId(Long boardId, taskStatus status, Long userId);
}
