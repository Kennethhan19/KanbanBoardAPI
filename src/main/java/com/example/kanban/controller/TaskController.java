package com.example.kanban.controller;

import com.example.kanban.model.Task;
import com.example.kanban.model.User;
import com.example.kanban.service.TaskService;
import com.example.kanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @RequestMapping("/newtasks/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Task newTask(@RequestBody Task task, @PathVariable("id") Long id) {
        User user = userService.getUserById(id).orElseThrow(()-> new RuntimeException("User not found"));
        task.setUser(user);
        return taskService.addNewTask(task);
    }

    @RequestMapping("/tasks")
    public List<Task> getTasks(){
        return taskService.getAllTasks();
    }

    @RequestMapping("/users/{userID}/tasks")
    public List<Task> getTasksByUser(@PathVariable("userID") User user){
        return taskService.getAllTasksByUser(user);
    }

    @RequestMapping("/tasks/{taskID}")
    public Task getTaskById(@PathVariable Long taskID){
        return taskService.getTaskById(taskID).orElseThrow(()-> new RuntimeException("Task ID not found"));
    }

    @RequestMapping("/tasks/title")
    public Task getTaskByTitle(@RequestParam String title){
        return taskService.getTaskByTitle(title).orElseThrow(()-> new RuntimeException("Task Title not found"));
    }

    @PutMapping("/tasks/{taskID}")
    public Task updateTask(@PathVariable Long taskID, @RequestBody Task task){
        return taskService.updateTaskInfo(taskID, task);
    }

    @DeleteMapping("/tasks/{taskID}")
    public void deleteTask(@PathVariable Long taskID){
        taskService.deleteTask(taskID);
    }
}
