package com.example.kanban.service.Task;
import com.example.kanban.model.Task.Task;
import com.example.kanban.model.Task.taskStatus;
import com.example.kanban.model.User.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task addNewTask(Task task);

    List<Task> getAllTasks();

    List<Task> getAllTasksByUser(Long userId);

    List<Task> getAllTasksByBoard(Long boardId);

    List<Task> getAllTasksByBoardAndUser(Long boardId, Long userId);

    List<Task> getAllTasksByBoardAndStatus(Long boardId, taskStatus status);

    List<Task> getAllTasksByBoardAndStatusAndUser(Long boardId, taskStatus status, Long userId);

    Optional<Task> getTaskById(Long taskId);

    Optional<Task> getTaskByTitle(String title);

    Task updateTaskInfo(Long taskId, Task task, User user, Long boardId);

    void deleteTask(Long taskId);
}