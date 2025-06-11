package com.todo.app.service;

import java.security.Principal;
import java.util.List;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.todo.app.dto.TodoRequest;
import com.todo.app.dto.TodoUpdateRequest;
import com.todo.app.entity.Todo;
import com.todo.app.entity.User;
import com.todo.app.exception.DataNotFoundException;
import com.todo.app.repository.TodoRepository;
import com.todo.app.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    
    public Todo createTodo(TodoRequest todoRequest, Principal principal) {
        User findUserWithSec = userRepository.findByUsername(principal.getName())
        .orElseThrow(() -> new DataNotFoundException("Foydalanuvchi topilmadi"));
        
        Todo todo = new Todo();
        todo.setTodoName(todoRequest.getTodoName());
        todo.setDescription(todoRequest.getDescription());
        todo.setUserTodo(findUserWithSec);
        return todoRepository.save(todo);
    }
    
    
    public Todo updateTodo(TodoUpdateRequest todoUpdateRequest, Long id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
            .orElseThrow(() -> new DataNotFoundException("Foydalanuvchi topilmadi"));

     
        Todo todo = todoRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Todo topilmadi"));


        if (!todo.getUserTodo().getId().equals(user.getId())) {
            throw new SecurityException("You can only update your own todos");
        }
 
        if (todoUpdateRequest.getTodoName() != null) {
            todo.setTodoName(todoUpdateRequest.getTodoName());
        }
        if (todoUpdateRequest.getDescription() != null) {
            todo.setDescription(todoUpdateRequest.getDescription());
        }
        return todoRepository.save(todo);
    }

    public boolean deleteTodo(Long id, Principal principal) {
        Optional<Todo> todoOpt = todoRepository.findById(id);
        if (todoOpt.isEmpty()) {
            throw new DataNotFoundException("todo topilmadi");
        }

        Todo todo = todoOpt.get();
        todoRepository.delete(todo);
        return true;
    }
    
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
    
    
} 
