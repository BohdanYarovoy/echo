package com.echoteam.app.controllers;

import com.echoteam.app.entities.dto.nativeDTO.UserDTO;
import com.echoteam.app.entities.dto.changedDTO.ChangedUser;
import com.echoteam.app.entities.dto.createdDTO.CreatedUser;
import com.echoteam.app.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

import static com.echoteam.app.entities.mappers.UserMapper.INSTANCE;

@RestController
@RequestMapping("${application.endpoint.user}")
@RequiredArgsConstructor
public class UserController {
    @Value("${application.endpoint.user}")
    public String userUri;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        var usersDTOs = INSTANCE.toDTOs(userService.getAll());
        usersDTOs.forEach(UserDTO::doRoutine);
        return ResponseEntity.ok(usersDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        var dto = INSTANCE.toDTOFromUser(userService.getById(id));
        dto.doRoutine();
        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreatedUser user,
                                        UriComponentsBuilder uriBuilder) {
        var createdUser = userService.createUser(INSTANCE.toDTOFromCreatedUser(user));
        var createdUserDTO = INSTANCE.toDTOFromUser(createdUser);

        URI location = uriBuilder
                .replacePath(userUri + "/{id}")
                .buildAndExpand(Map.of("id", createdUserDTO.getId()))
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(location)
                .body(createdUserDTO);
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody ChangedUser changedUser,
                                        UriComponentsBuilder uriBuilder) {
        var user = userService.updateUser(INSTANCE.toDTOFromChangedUser(changedUser));
        var updatedUserDTO = INSTANCE.toDTOFromUser(user);
        updatedUserDTO.doRoutine();

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
