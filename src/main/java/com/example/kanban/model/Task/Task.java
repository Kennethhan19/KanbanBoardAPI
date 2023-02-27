package com.example.kanban.model.Task;

import com.example.kanban.model.Board.Board;
import com.example.kanban.model.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tasks")
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    private String description;

    @CreationTimestamp
    private Date created_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate due_date;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "board_id")
    private Board board;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private taskStatus status;
}

