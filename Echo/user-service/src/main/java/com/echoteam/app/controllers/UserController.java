package com.echoteam.app.controllers;

import com.echoteam.app.entities.dto.changedDTO.ChangedUser;
import com.echoteam.app.entities.dto.createdDTO.CreatedUser;
import com.echoteam.app.entities.dto.nativeDTO.UserDTO;
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
    public static String userUri;
    private final UserService userService;

    @Value("${application.endpoint.user}")
    public void setUserUri(String uri) {
        UserController.userUri = uri;
    }

    // todo: this method need to be with pagination, because when client will request, he can get all entities.
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

        URI location = uriBuilder
                .replacePath(userUri + "/{id}")
                .buildAndExpand(Map.of("id", createdUser.getId()))
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(location)
                .build();
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody ChangedUser changedUser,
                                        UriComponentsBuilder uriBuilder) {
        var user = userService.updateUser(INSTANCE.toDTOFromChangedUser(changedUser));
        URI location = uriBuilder
                .replacePath(userUri + "/{id}")
                .buildAndExpand(Map.of("id", user.getId()))
                .toUri();
        return ResponseEntity.status(HttpStatus.OK)
                .location(location)
                .build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
