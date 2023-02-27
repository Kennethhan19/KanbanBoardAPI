package com.example.kanban.service.Board;

import com.example.kanban.model.Board.Board;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    Board addNewBoard(Board board);

    List<Board> getAllBoard();

    Optional<Board> getBoardById(Long boardId);

    Optional<Board> getBoardByName(String name);

    Board addNewUser(Long boardId, Long userId);

    Board addNewTask(Long boardId, Long taskId);

    Board updateBoard(Long boardId, Board board);

    Board deleteUserFromBoard(Long boardId, Long userId);

    void deleteBoard(Long boardId);
}
