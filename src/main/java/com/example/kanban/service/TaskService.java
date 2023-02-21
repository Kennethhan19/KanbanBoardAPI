package com.example.kanban.service;
import com.example.kanban.model.Task;
import com.example.kanban.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task addNewTask(Task task);

    List<Task> getAllTasks();

    List<Task> getAllTasksByUser(User user);

//    List<Task> getAllTasksByBoard(Board board);

    Optional<Task> getTaskById(Long id);

    Optional<Task> getTaskByTitle(String title);

    Task updateTaskInfo(Long id, Task task);

    void deleteTask(Long id);

}