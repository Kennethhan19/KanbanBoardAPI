package com.example.kanban.service.Board;

import com.example.kanban.model.Board.Board;
import com.example.kanban.model.Task.Task;
import com.example.kanban.model.User.User;
import com.example.kanban.repository.BoardRepository;
import com.example.kanban.repository.TaskRepository;
import com.example.kanban.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Board addNewBoard(Board board){
        board.setTasks(new ArrayList<Task>());
        board.setUsers(new ArrayList<User>());
        return boardRepository.save(board);
    }

    @Override
    public List<Board> getAllBoard(){
        return boardRepository.findAll();
    }

    @Override
    public Optional<Board> getBoardById(Long boardId){
        return boardRepository.findById(boardId);
    }

    @Override
    public Optional<Board> getBoardByName(String name){
        return boardRepository.findByName(name);
    }

    @Override
    public Board addNewUser(Long boardId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "board not found"));

        List<Board> tempBoard = user.getBoards();
        tempBoard.add(board);
        user.setBoards(tempBoard);

        List<User> tempUser = board.getUsers();
        tempUser.add(user);
        board.setUsers(tempUser);

        return boardRepository.save(board);
    }

    @Override
    public Board addNewTask(Long boardId, Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "task not found"));
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "board not found"));

        task.setBoard(board);
        List<Task> tempTask = board.getTasks();
        tempTask.add(task);
        board.setTasks(tempTask);

        return boardRepository.save(board);
    }

    @Override
    public Board updateBoard(Long boardId, Board board){
        Board tempBoard = boardRepository.findById(boardId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "board not found"));
        tempBoard.setName(board.getName());
        return boardRepository.save(tempBoard);
    }

    @Override
    public Board deleteUserFromBoard(Long boardId, Long userId){
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "board not found"));
        User user = userRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

        List<User> users = userRepository.findByBoards_Id(boardId);
        if (!users.contains(user)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not member of board");
        }

        users.remove(user);
        board.setUsers(users);
        return boardRepository.save(board);
    }

    @Override
    public void deleteBoard(Long boardId){
        boardRepository.deleteById(boardId);
    }

}
