package com.todo.app.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_todo")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "full_name")
    private String fullName;
    
    private String username;
    private String password;
    
    @OneToMany(mappedBy = "userTodo")
    @JsonIgnore
    private List<Todo> todos;
    
    @Column(name = "created_at")
    private LocalDateTime localDateTime;
    
    @PrePersist
    protected void onCreate() {
        this.localDateTime = LocalDateTime.now();
    }
}
