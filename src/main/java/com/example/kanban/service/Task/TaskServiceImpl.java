package com.example.kanban.service.Task;

import com.example.kanban.model.Board.Board;
import com.example.kanban.model.Task.Task;
import com.example.kanban.model.Task.taskStatus;
import com.example.kanban.model.User.User;
import com.example.kanban.repository.BoardRepository;
import com.example.kanban.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Task addNewTask(Task task){
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getAllTasksByUser(Long userId){
        return taskRepository.findAllByUserId(userId);
    }

    @Override
    public List<Task> getAllTasksByBoard(Long boardId){
        return taskRepository.findAllByBoardId(boardId);
    }

    @Override
    public List<Task> getAllTasksByBoardAndUser(Long boardId, Long userId){
        return taskRepository.findAllByBoardIdAndUserId(boardId, userId);
    }

    @Override
    public List<Task> getAllTasksByBoardAndStatus(Long boardId, taskStatus status){
        return taskRepository.findAllByBoardIdAndStatus(boardId, status);
    }

    @Override
    public List<Task> getAllTasksByBoardAndStatusAndUser(Long boardId, taskStatus status, Long userId){
        return taskRepository.findAllByBoardIdAndStatusAndUserId(boardId, status, userId);
    }

    @Override
    public Optional<Task> getTaskById(Long taskId){
        return taskRepository.findById(taskId);
    }

    @Override
    public Optional<Task> getTaskByTitle(String title){
        return taskRepository.findByTitle(title);
    }

    @Override
    public Task updateTaskInfo(Long taskId, Task task, User user, Long boardId){
        Task tempTask = taskRepository.findById(taskId).get();
        Board tempBoard = boardRepository.findById(boardId).orElseThrow(()-> new RuntimeException("board not found"));

        tempTask.setTitle(task.getTitle());
        tempTask.setDescription(task.getDescription());
        tempTask.setUser(user);
        tempTask.setDue_date(task.getDue_date());
        tempTask.setStatus(task.getStatus());
        tempTask.setBoard(tempBoard);

        return taskRepository.save(tempTask);
    }

    @Override
    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

}
