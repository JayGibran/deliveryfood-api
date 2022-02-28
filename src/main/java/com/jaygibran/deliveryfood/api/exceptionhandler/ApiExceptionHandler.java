package com.jaygibran.deliveryfood.api.exceptionhandler;

import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<?> handleEntityNotFound(EntityNotFoundException e) {
        ApiError error = ApiError.builder()
                .dateTime(LocalDateTime.now())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    private ResponseEntity<?> handleBusinessException(BusinessException e) {
        ApiError error = ApiError.builder()
                .dateTime(LocalDateTime.now())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EntityInUseException.class)
    private ResponseEntity<?> handleEntityInUseException(EntityInUseException e) {
        ApiError error = ApiError.builder()
                .dateTime(LocalDateTime.now())
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
