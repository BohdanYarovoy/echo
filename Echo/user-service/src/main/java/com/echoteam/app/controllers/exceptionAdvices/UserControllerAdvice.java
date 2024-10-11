package com.echoteam.app.controllers.exceptionAdvices;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.exceptions.ValidationException;
import jakarta.persistence.Column;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.JoinColumn;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Field;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(value = ParameterIsNotValidException.class)
    public ResponseEntity<ErrorResponse> handleParameterIsNotValidException(ParameterIsNotValidException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage()).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = recognizeDataIntegrityViolationException(ex);
        ErrorResponse errorResponse = ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, errorMessage);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getViolations().toString()).build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    private Class<?> recognizeClassByException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();
        if (message.contains("users")) {
            return User.class;
        } else if (message.contains("user_details")) {
            System.out.println("recognizeClassByException");
            return UserDetail.class;
        } else if (message.contains("user_auths")) {
            return User.class;
        }
        return null;
    }

    /**
     * recognizing what field was filling with unique value by Annotation in {@link User} class
     */
    private String recognizeDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();
        String requestMessage = "Entity with such %s already exist.";
        Class<?> targetClass = recognizeClassByException(ex);

        if (targetClass == null) {
            return "Something was wrong. Try again later please.";
        }

        Field[] declaredFields = targetClass.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation.unique() &&
                    (message.contains("(" + columnAnnotation.name() + ")") || message.contains(columnAnnotation.name().toUpperCase()))) {
                    return requestMessage.formatted(field.getName());
                }
            } else if (field.isAnnotationPresent(JoinColumn.class)) {
                JoinColumn joinColumnAnnotation = field.getAnnotation(JoinColumn.class);
                if (joinColumnAnnotation.unique() &&
                    (message.contains("(" + joinColumnAnnotation.name() + ")") || message.contains(joinColumnAnnotation.name().toUpperCase()))) {
                    return requestMessage.formatted(field.getName());
                }
            }
        }

        return "Something was wrong. Try again later please.";
    }


}
