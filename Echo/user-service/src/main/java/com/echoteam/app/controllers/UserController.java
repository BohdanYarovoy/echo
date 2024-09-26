package com.echoteam.app.controllers;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.UserDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.exceptions.UniqueRecordAlreadyExistsException;
import com.echoteam.app.services.UserService;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    public static final String USER_URI = "/api/v1/users";

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).
                body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") Long id) {
        UserDTO user = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO user, UriComponentsBuilder uriBuilder)
                                                            throws ParameterIsNotValidException,
                                                                    UniqueRecordAlreadyExistsException {
        UserDTO createdUser = userService.createUser(user);
        URI location = uriBuilder
                .replacePath(USER_URI + "/{id}")
                .buildAndExpand(Map.of("id", createdUser.id()))
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(location)
                .body(createdUser);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user, UriComponentsBuilder uriBuilder)
                                                            throws ParameterIsNotValidException,
                                                                UniqueRecordAlreadyExistsException {
        UserDTO updatedUser = userService.updateUser(user);
        URI location = uriBuilder
                .replacePath(USER_URI + "/{id}")
                .buildAndExpand(Map.of("id", updatedUser.id()))
                .toUri();
        return ResponseEntity.status(HttpStatus.OK)
                .location(location)
                .body(updatedUser);
    }


    @ExceptionHandler(value = ParameterIsNotValidException.class)
    public ResponseEntity<String> handleParameterIsNotValidException(ParameterIsNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = UniqueRecordAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUniqueRecordAlreadyExistsException(UniqueRecordAlreadyExistsException ex) {
        ErrorResponse errorResponse = ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
