package com.echoteam.app.controllers;

import com.echoteam.app.entities.dto.UserDTO;
import com.echoteam.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    public static final String USER_URI = "/api/v1/users";
    private final UserService userService;

    // todo: поробити перевірки на null в усіх методах, бо у випадку відправлення null, сервер ніяк не реагує

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).
                body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        UserDTO user = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO user,
                                        UriComponentsBuilder uriBuilder) {
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
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user,
                                        UriComponentsBuilder uriBuilder) {
        UserDTO updatedUser = userService.updateUser(user);
        URI location = uriBuilder
                .replacePath(USER_URI + "/{id}")
                .buildAndExpand(Map.of("id", updatedUser.id()))
                .toUri();
        return ResponseEntity.status(HttpStatus.OK)
                .location(location)
                .body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
