package com.example.kanban.service;

import com.example.kanban.model.Task;
import com.example.kanban.model.User;
import com.example.kanban.repository.TaskRepository;
import com.example.kanban.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task addNewTask(Task task){
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getAllTasksByUser(User user){
        return taskRepository.findAllByUser(user);
    }

//    @Override
//    public List<Task> getAllTasksByBoard(Long boardID){
//        return taskRepository.getAllByBoard(boardID);
//    }

    @Override
    public Optional<Task> getTaskById(Long id){
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> getTaskByTitle(String title){
        return taskRepository.findByTitle(title);
    }

    @Override
    public Task updateTaskInfo(Long id, Task task){
        Task tempTask = taskRepository.findById(id).get();
        tempTask.setTitle(task.getTitle());
        tempTask.setDescription(task.getDescription());
        tempTask.setUser(task.getUser());
        tempTask.setDue_date(task.getDue_date());
        tempTask.setStatus(task.getStatus());

        return taskRepository.save(tempTask);
    }

    @Override
    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

}
