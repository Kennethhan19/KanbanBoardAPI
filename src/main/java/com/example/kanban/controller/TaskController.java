package com.example.kanban.controller;

import com.example.kanban.model.Board.Board;
import com.example.kanban.model.Task.Task;
import com.example.kanban.model.Task.taskStatus;
import com.example.kanban.model.User.Role;
import com.example.kanban.model.User.User;
import com.example.kanban.service.Board.BoardService;
import com.example.kanban.service.Task.TaskService;
import com.example.kanban.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @PostMapping("/new/{userId}")
    public ResponseEntity<Task> newTask(@RequestBody Task task, @PathVariable Long userId) {
        User user = userService.getUserById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        task.setUser(user);
        try{
            return ResponseEntity.ok(taskService.addNewTask(task));
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title cannot be empty");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getTasks(){
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId){
            return ResponseEntity.ok(taskService.getAllTasksByUser(userId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.getTaskById(taskId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")));
    }

    @GetMapping()
    public ResponseEntity<Task> getTaskByTitle(@RequestParam String title){
        return ResponseEntity.ok(taskService.getTaskByTitle(title).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<Task>> getAllTasksByBoard(@PathVariable Long boardId){
        Optional<Board> board = boardService.getBoardById(boardId);
        if(board.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "board not found");
        }
        return ResponseEntity.ok(taskService.getAllTasksByBoard(boardId));
    }

    @GetMapping("/board-user/{boardId}/{userId}")
    public ResponseEntity<List<Task>> getAllTasksByBoardAndUser(@PathVariable("boardId") Long boardId, @PathVariable("userId") Long userId){
        Optional<Board> board = boardService.getBoardById(boardId);
        Optional<User> user = userService.getUserById(userId);
        if(board.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "board not found");
        }
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

//        check if user is member of board
        return ResponseEntity.ok(taskService.getAllTasksByBoardAndUser(boardId, userId));
    }

    @GetMapping("/board-status/{boardId}")
    public ResponseEntity<List<Task>> getAllTasksByBoardAndStatus(@PathVariable Long boardId, @RequestParam taskStatus status){
        Optional<Board> board = boardService.getBoardById(boardId);
        if(board.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "board not found");
        }
        return ResponseEntity.ok(taskService.getAllTasksByBoardAndStatus(boardId, status));
    }

    @GetMapping("/board-user-status/{boardId}/{userId}")
    public ResponseEntity<List<Task>> getAllTasksByBoardAndStatusAndUser(@PathVariable Long boardId, @PathVariable Long userId, @RequestParam taskStatus status) {
        Optional<Board> board = boardService.getBoardById(boardId);
        Optional<User> user = userService.getUserById(userId);
        if (board.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "board not found");
        }
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

//        check if user is member of board
        return ResponseEntity.ok(taskService.getAllTasksByBoardAndStatusAndUser(boardId, status, userId));
    }

    @PutMapping("/update/{taskId}/{boardId}")
    public ResponseEntity<Task> updateTaskByCurrentUser(@PathVariable("taskId")Long taskId, @PathVariable("boardId") Long boardId, @RequestBody Task task, @AuthenticationPrincipal User principal){
        List<User> users = userService.getUsersByBoards(boardId);
        User user = userService.getUserById(principal.getId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (!users.contains(user)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not a member of board");
        }
        return ResponseEntity.ok(taskService.updateTaskInfo(taskId, task, principal, boardId));
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable Long taskId, @AuthenticationPrincipal User principal){
        List<Task> tasks = taskService.getAllTasksByUser(principal.getId());
        Optional<Task> task = taskService.getTaskById(taskId);
        if((task.isPresent() && tasks.contains(task.get()))|| principal.getRole()== Role.ADMIN){
            taskService.deleteTask(taskId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Task does not belong to user / Admin rights needed / Task not found");
        }
    }
}
