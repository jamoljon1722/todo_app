package com.todo.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.todo.app.exception.DataAlreadyExistsException;
import com.todo.app.exception.DataNotFoundException;

@ControllerAdvice
public class GlobalHandler {
    
    // not found
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> notFoundHandler(DataNotFoundException dataNotFoundException) {
        return new ResponseEntity<>(dataNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    // alreayd exists
    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<String> alreadyExistsHandler(DataAlreadyExistsException dataAlreadyExistsException) {
        return new ResponseEntity<>(dataAlreadyExistsException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
