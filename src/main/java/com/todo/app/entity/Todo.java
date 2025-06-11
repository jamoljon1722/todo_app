package com.todo.app.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "todo_name")
    private String todoName;
    private String description;

    @Column(name = "is_finished")
    private boolean isFinished = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User userTodo;

    @Column(name = "created_at")
    private LocalDateTime localDateTime;
    
    @PrePersist
    protected void onCreate() {
        this.localDateTime = LocalDateTime.now();
    }
}
