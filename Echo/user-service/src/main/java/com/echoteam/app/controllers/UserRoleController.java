package com.echoteam.app.controllers;

import com.echoteam.app.entities.dto.UserRoleDTO;
import com.echoteam.app.services.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user-roles")
public class UserRoleController {

    public static final String USER_ROLE_URI = "/api/v1/user-roles";
    private final UserRoleService userRoleService;

    // todo: поробити перевірки на null в усіх методах, бо у випадку відправлення null в not null поля,
    // todo: сервер видає свою помилку а не bad-request як потрібно

    @GetMapping
    public ResponseEntity<?> getAllUserRoles() {
        List<UserRoleDTO> userRoles = userRoleService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(userRoles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserRoleByID(@PathVariable("id") Short id) {
        UserRoleDTO roleById = userRoleService.findRoleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(roleById);
    }

    @GetMapping("/selectively")
    public ResponseEntity<?> getUserRoleByIds(@RequestParam(name = "ids") List<Short> ids) {
        List<UserRoleDTO> userRolesByIdIn = userRoleService.getUserRolesByIdIn(ids);
        return ResponseEntity.status(HttpStatus.OK).body(userRolesByIdIn);
    }

    @PostMapping
    public ResponseEntity<?> createUserRole(@RequestBody UserRoleDTO userRoleDTO,
                                            UriComponentsBuilder uriBuilder) {
        UserRoleDTO createdUserRole = userRoleService.createUserRole(userRoleDTO);
        URI location = uriBuilder
                .replacePath(USER_ROLE_URI + "/{id}")
                .buildAndExpand(Map.of("id", createdUserRole.id()))
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(location)
                .body(createdUserRole);
    }

    @PutMapping
    public ResponseEntity<?> updateUserRole(@RequestBody UserRoleDTO userRoleDTO,
                                            UriComponentsBuilder uriBuilder) {
        UserRoleDTO updatedUserRole = userRoleService.updateUserRole(userRoleDTO);
        URI location = uriBuilder
                .replacePath(USER_ROLE_URI + "/{id}")
                .buildAndExpand(Map.of("id", updatedUserRole.id()))
                .toUri();
        return ResponseEntity.status(HttpStatus.OK)
                .location(location)
                .body(updatedUserRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserRole(@PathVariable("id") Short id) {
        userRoleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
