package com.echoteam.app.controllers;

import com.echoteam.app.entities.User;
import com.echoteam.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).
                body(users);
    }

    // todo: fix all warnings that signs in console while it is running

}
