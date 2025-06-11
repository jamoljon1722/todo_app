package com.todo.app.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.app.dto.TodoRequest;
import com.todo.app.dto.TodoUpdateRequest;
import com.todo.app.entity.Todo;
import com.todo.app.service.TodoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/todo")
public class TodoController {
    
    private final TodoService todoService;
    
    @PostMapping("/new")
    public ResponseEntity<Todo> createTodo(@RequestBody TodoRequest todoRequest, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Todo todo = todoService.createTodo(todoRequest, principal);
        return ResponseEntity.ok(todo);
    }
    
    @GetMapping("/all")
    public List<Todo> getTodos() {
        return todoService.getAllTodos();
    }
    
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Todo> updateTodo(@RequestBody TodoUpdateRequest todoUpdateRequest,
    @PathVariable Long id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Todo todo = todoService.updateTodo(todoUpdateRequest, id, principal);
        return ResponseEntity.ok(todo);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> delTodo(@PathVariable Long id, Principal principal) {
        boolean bol = todoService.deleteTodo(id, principal);

        if (bol) {
            return ResponseEntity.ok().body("success");
        }
        return ResponseEntity.badRequest().body("not success");
    }
}
