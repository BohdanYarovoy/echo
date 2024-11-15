package com.echoteam.app.controllers;

import com.echoteam.app.entities.UserRole;
import com.echoteam.app.entities.dto.changedDTO.ChangedRole;
import com.echoteam.app.entities.dto.createdDTO.CreatedRole;
import com.echoteam.app.entities.dto.nativeDTO.UserRoleDTO;
import com.echoteam.app.services.UserRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.echoteam.app.entities.mappers.UserRoleMapper.INSTANCE;

@RequiredArgsConstructor
@RestController
@RequestMapping("${application.endpoint.role}")
public class UserRoleController {
    public static String roleUri;
    private final UserRoleService userRoleService;

    @Value("${application.endpoint.role}")
    public void setRoleUri(String roleUri) {
        UserRoleController.roleUri = roleUri;
    }

    @GetMapping
    public ResponseEntity<?> getAllUserRoles(@RequestParam(name ="page",defaultValue = "0") int page,
                                             @RequestParam(name ="size",defaultValue = "10") int size,
                                             @RequestParam(name ="sortBy",defaultValue = "id") String sortBy,
                                             @RequestParam(name ="direction",defaultValue = "asc") String direction) {
        var sort = creteSort(sortBy, direction);
        var pageRequest = PageRequest.of(page, size, sort);
        Page<UserRole> pageResponse = userRoleService.getAll(pageRequest);

        PagedModel<UserRoleDTO> pageRolesDTOs = cretePagedMode(pageResponse);
        return ResponseEntity.ok(pageRolesDTOs);
    }

    private PagedModel<UserRoleDTO> cretePagedMode(Page<UserRole> pageResponse) {
        Page<UserRoleDTO> pageRoleDTOs = pageResponse.map(INSTANCE::toDTO);
        return new PagedModel<>(pageRoleDTOs);
    }

    private Sort creteSort(String sortBy, String direction) {
        return direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
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

        URI location = uriBuilder
                .replacePath(roleUri + "/{id}")
                .buildAndExpand(Map.of("id", role.getId()))
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(location)
                .build();
    }
    @PutMapping
    public ResponseEntity<?> updateUserRole(@Valid @RequestBody ChangedRole changedRole,
                                            UriComponentsBuilder uriBuilder) {
        var userRoleDTO = INSTANCE.toDTOFromChangedRole(changedRole);
        var role = userRoleService.updateUserRole(userRoleDTO);

        URI location = uriBuilder
                .replacePath(roleUri + "/{id}")
                .buildAndExpand(Map.of("id", role.getId()))
                .toUri();
        return ResponseEntity.status(HttpStatus.OK)
                .location(location)
                .build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserRole(@PathVariable("id") Short id) {
        userRoleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
