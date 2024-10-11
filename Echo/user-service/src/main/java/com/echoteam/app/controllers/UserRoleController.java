package com.echoteam.app.controllers;

import com.echoteam.app.entities.dto.entityDTO.changed.ChangedRole;
import com.echoteam.app.entities.dto.entityDTO.created.CreatedRole;
import com.echoteam.app.services.UserRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.echoteam.app.entities.dto.mappers.UserRoleMapper.INSTANCE;

@RequiredArgsConstructor
@RestController
@RequestMapping("${application.endpoint.role}")
public class UserRoleController {
    @Value("${application.endpoint.role}")
    public String roleUri;
    private final UserRoleService userRoleService;

    @GetMapping
    public ResponseEntity<?> getAllUserRoles() {
        var userRoles = userRoleService.getAll();
        var userRolesDTOs = INSTANCE.toDTOs(userRoles);
        return ResponseEntity.ok(userRolesDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserRoleByID(@PathVariable("id") Short id) {
        var role = userRoleService.findRoleById(id);
        var roleDTO = INSTANCE.toDTO(role);
        return ResponseEntity.ok(roleDTO);
    }

    @GetMapping("/selectively")
    public ResponseEntity<?> getUserRoleByIds(@RequestParam(name = "ids") List<Short> ids) {
        var role = userRoleService.getUserRolesByIdIn(ids);
        var roles = INSTANCE.toDTOs(role);
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<?> createUserRole(@Valid @RequestBody CreatedRole createdRole,
                                            UriComponentsBuilder uriBuilder) {
        var userRoleDTO = INSTANCE.toDTOFromCreatedRole(createdRole);
        var role = userRoleService.createUserRole(userRoleDTO);
        var roleDTO = INSTANCE.toDTO(role);

        URI location = uriBuilder
                .replacePath(roleUri + "/{id}")
                .buildAndExpand(Map.of("id", roleDTO.getId()))
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(location)
                .body(roleDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateUserRole(@Valid @RequestBody ChangedRole changedRole,
                                            UriComponentsBuilder uriBuilder) {
        var userRoleDTO = INSTANCE.toDTOFromChangedRole(changedRole);
        var role = userRoleService.updateUserRole(userRoleDTO);
        var roleDTO = INSTANCE.toDTO(role);

        URI location = uriBuilder
                .replacePath(roleUri + "/{id}")
                .buildAndExpand(Map.of("id", roleDTO.getId()))
                .toUri();
        return ResponseEntity.status(HttpStatus.OK)
                .location(location)
                .body(roleDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserRole(@PathVariable("id") Short id) {
        userRoleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
