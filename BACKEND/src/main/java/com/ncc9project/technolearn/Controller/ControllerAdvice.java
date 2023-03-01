package com.ncc9project.technolearn.Controller;

import com.ncc9project.technolearn.DTO.ErrorDTO;
import com.ncc9project.technolearn.Exceptions.GeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity<ErrorDTO> requestExceptionHandler(GeneralException ex){
        ErrorDTO error = ErrorDTO.builder().message(ex.getMessage()).build();
        return new ResponseEntity<>(error, ex.getStatus());
    }
}
