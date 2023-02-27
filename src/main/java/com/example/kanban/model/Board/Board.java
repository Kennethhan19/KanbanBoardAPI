package com.example.kanban.model.Board;

import com.example.kanban.model.Task.Task;
import com.example.kanban.model.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="boards")
public class Board {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "boards", cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;
}
