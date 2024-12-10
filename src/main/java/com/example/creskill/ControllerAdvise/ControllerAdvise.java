package com.example.creskill.ControllerAdvise;

import com.example.creskill.ApiRespose.ApiException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.concurrent.TimeoutException;

@ControllerAdvice
public class ControllerAdvise {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity ApiException (ApiException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(message);
    }
    @ExceptionHandler (value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException (MethodArgumentNotValidException e){
        String message =e.getFieldError().getDefaultMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity IllegalArgumentException(IllegalArgumentException e) {
        String message = "Invalid argument: " + e.getMessage();
        return ResponseEntity.status(400).body(message);
    }
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity NullPointerException(NullPointerException e) {
        String message =  e.getMessage();
        return ResponseEntity.status(400).body(message);}

    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity NumberFormatException (NumberFormatException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);}

    @ExceptionHandler(value = TimeoutException.class)
    public ResponseEntity TimeoutException (TimeoutException e){
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(msg);}

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity DataIntegrityViolationException (DataIntegrityViolationException e){
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(msg);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity NoResourceFoundException (NoResourceFoundException e){
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(msg);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(msg);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(msg);
    }


    @ExceptionHandler(value = InvalidDataAccessResourceUsageException.class )
    public ResponseEntity InvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e){
        String msg=e.getMessage();
        return ResponseEntity.status(200).body(msg);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        String msg=e.getMessage();
        return ResponseEntity.status(400).body(msg);
    }
@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity HttpRequestMethodNotSupportedException (HttpRequestMethodNotSupportedException e){
        String msg= e.getMessage();
        return ResponseEntity.status(400).body(msg);
    }
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity ConstraintViolationException(ConstraintViolationException e) {
        String msg =e.getMessage();
        return ResponseEntity.status(400).body(msg);
    }



}
