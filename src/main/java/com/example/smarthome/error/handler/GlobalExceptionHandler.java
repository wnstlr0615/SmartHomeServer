package com.example.smarthome.error.handler;

import com.example.smarthome.error.ErrorResponse;
import com.example.smarthome.error.code.GlobalErrorCode;
import com.example.smarthome.error.exception.ArduinoServerException;
import com.example.smarthome.error.exception.SpeakerServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SpeakerServerException.class)
    public ResponseEntity<?> speakerServerExceptionHandler(SpeakerServerException e, HttpServletRequest request){
        log.error("speaker server error  - requestUrl : {}", request.getRequestURL());
        log.error(e.getMessage());
        return ResponseEntity.status(
                        e.getErrorCode().getStatusCode()
                ).body(
                        ErrorResponse.fromErrorCode(e.getErrorCode())
            );
    }

    @ExceptionHandler(ArduinoServerException.class)
    public ResponseEntity<?> speakerServerExceptionHandler(ArduinoServerException e, HttpServletRequest request){
        log.error("arduino server error  - requestUrl : {}", request.getRequestURL());
        log.error(e.getMessage());
        return ResponseEntity.status(
                e.getErrorCode().getStatusCode()
        ).body(
                ErrorResponse.fromErrorCode(e.getErrorCode())
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> runtimeExceptionHandler(Exception e, HttpServletRequest request){
        log.error("request fail  - requestUrl : {}", request.getRequestURL());
        log.error(e.getMessage());
        return ResponseEntity.internalServerError()
                .body(
                ErrorResponse.fromErrorCode(GlobalErrorCode.INTERNAL_SERVER_ERROR)
            );
    }
}
