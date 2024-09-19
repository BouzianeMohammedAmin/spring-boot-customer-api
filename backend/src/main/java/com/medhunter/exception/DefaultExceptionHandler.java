package com.medhunter.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(NoteFoundResourceExecption.class)
    public ResponseEntity<ApiError> handelException(
            NoteFoundResourceExecption noteFoundResourceExecption ,
            HttpServletRequest request ,
            HttpServletResponse response
    ){
        ApiError apiError = new ApiError(
                request.getRequestURI() ,
                noteFoundResourceExecption.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        ) ;
        return  new ResponseEntity<ApiError>(apiError , HttpStatus.NOT_FOUND) ;
    }
}
