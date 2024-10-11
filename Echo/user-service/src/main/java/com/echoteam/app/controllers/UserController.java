package com.echoteam.app.controllers;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.entityDTO.CreatedUser;
import com.echoteam.app.entities.dto.entityDTO.UserDTO;
import com.echoteam.app.services.UserService;
import com.echoteam.app.services.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.echoteam.app.entities.dto.mappers.UserMapper.INSTANCE;

@RestController
@RequestMapping("${application.endpoint.user}")
@RequiredArgsConstructor
public class UserController {
    @Value("${application.endpoint.user}")
    public String userUri;
    private final UserService userService;
    private final ValidationService validationService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.getAll();
        List<UserDTO> usersDTOs = INSTANCE.toDTOs(users);
        UserDTO.removeAuth(usersDTOs);
        return ResponseEntity.ok(usersDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        UserDTO dto = INSTANCE.toDTOFromUser(user);
        dto.hidePassword();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreatedUser user,
                                        UriComponentsBuilder uriBuilder) {
        validationService.isValid(user);
        UserDTO userDTO = INSTANCE.toDTOFromCreatedUser(user);
        User createdUser = userService.createUser(userDTO);
        UserDTO createdUserDTO = INSTANCE.toDTOFromUser(createdUser);
        createdUserDTO.hidePassword();

        URI location = uriBuilder
                .replacePath(userUri + "/{id}")
                .buildAndExpand(Map.of("id", createdUserDTO.getId()))
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(location)
                .body(createdUserDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user,
                                        UriComponentsBuilder uriBuilder) {
        User updatedUser = userService.updateUser(user);
        UserDTO updatedUserDTO = INSTANCE.toDTOFromUser(updatedUser);
        updatedUserDTO.hidePassword();

        URI location = uriBuilder
                .replacePath(userUri + "/{id}")
                .buildAndExpand(Map.of("id", updatedUserDTO.getId()))
                .toUri();
        return ResponseEntity.status(HttpStatus.OK)
                .location(location)
                .body(updatedUserDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
