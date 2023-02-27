package com.example.kanban.controller;

import com.example.kanban.model.Board.Board;
import com.example.kanban.model.User.Role;
import com.example.kanban.model.User.User;
import com.example.kanban.service.Board.BoardService;
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
@RequestMapping("boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<Board> addNewBoard(@RequestBody Board board){
        return ResponseEntity.ok(boardService.addNewBoard(board));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Board>> getAllBoard(){
        return ResponseEntity.ok(boardService.getAllBoard());
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long boardId){
        Optional<Board> board = boardService.getBoardById(boardId);
        if (board.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }
        return ResponseEntity.ok(board.get());
    }

    @GetMapping("")
    public ResponseEntity<Board> getBoardByName(@RequestParam String name){
        Optional<Board> board = boardService.getBoardByName(name);
        if (board.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }
        return ResponseEntity.ok(board.get());
    }

    @PutMapping ("/add-user/{boardId}/{userId}")
    public ResponseEntity<Board> addUserToBoard(@PathVariable("boardId") Long boardId, @PathVariable("userId") Long userId){
        return ResponseEntity.ok(boardService.addNewUser(boardId, userId));
    }

    @PutMapping ("/add-task/{boardId}/{taskId}")
    public ResponseEntity<Board> addTaskToBoard(@PathVariable("boardId") Long boardId, @PathVariable("taskId") Long taskId){
        return ResponseEntity.ok(boardService.addNewTask(boardId, taskId));
    }

    @PutMapping("/update/{boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long boardId, @RequestParam Board board, @AuthenticationPrincipal User principal){

        Optional<Board> tempBoard = boardService.getBoardById(boardId);
        if (tempBoard.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }

        List<User> users = userService.getUsersByBoards(boardId);
        if (!users.contains(userService.getUserById(principal.getId())) || principal.getRole() != Role.ADMIN){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        return ResponseEntity.ok(boardService.updateBoard(boardId, board));
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<HttpStatus> deleteBoard(@PathVariable Long boardId){
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteUser/{boardId}/{userId}")
    public ResponseEntity<Board> deleteUserFromBoard(@PathVariable("userId") Long userId, @PathVariable("boardId") Long boardId){
        return ResponseEntity.ok(boardService.deleteUserFromBoard(boardId, userId));
    }
}


