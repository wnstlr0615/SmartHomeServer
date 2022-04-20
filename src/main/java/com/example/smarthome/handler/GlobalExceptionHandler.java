package com.example.smarthome.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> runtimeExceptionHandler(Exception e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
