package com.echoteam.app.controllers;

import com.echoteam.app.entities.dto.entityDTO.UserAuthDTO;
import com.echoteam.app.entities.dto.entityDTO.changed.ChangedUserAuth;
import com.echoteam.app.entities.dto.entityDTO.created.CreatedAuth;
import com.echoteam.app.services.UserAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.echoteam.app.entities.dto.mappers.UserAuthMapper.INSTANCE;

@Controller
@RequestMapping("${application.endpoint.auth}")
@RequiredArgsConstructor
public class UserAuthController {
    @Value("${application.endpoint.auth}")
    public String authUri;
    private final UserAuthService authService;

    @GetMapping
    public ResponseEntity<List<UserAuthDTO>> getAll() {
        var auths = authService.getAll();
        var authDTOs = INSTANCE.toDTOsFromAuths(auths);
        return ResponseEntity.ok(authDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAuthDTO> getById(@PathVariable("id")Long id) {
        var auth = authService.getById(id);
        var authDTO = INSTANCE.toDTOFromUserAuth(auth);
        return ResponseEntity.ok(authDTO);
    }

    @PostMapping
    public ResponseEntity<UserAuthDTO> createAuth(@Valid @RequestBody CreatedAuth createdAuth,
                                                  UriComponentsBuilder uriBuilder) {
        var createdAuthDTO = INSTANCE.toDTOFromCreatedAuth(createdAuth);
        var savedAuth = authService.create(createdAuthDTO);
        var authDTO = INSTANCE.toDTOFromUserAuth(savedAuth);

        URI uri = uriBuilder.replacePath(authUri + "/{id}")
                .buildAndExpand("id", authDTO.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(authDTO);
    }

    @PutMapping
    public ResponseEntity<UserAuthDTO> updateAuth(@Valid @RequestBody ChangedUserAuth changedUserAuth,
                                                  UriComponentsBuilder uriBuilder) {
        var changedAuthDTO = INSTANCE.toDTOFromChangedAuth(changedUserAuth);
        var updatedAuth = authService.update(changedAuthDTO);
        var updatedAuthDTO = INSTANCE.toDTOFromUserAuth(updatedAuth);

        URI uri = uriBuilder.replacePath(authUri + "/{id}")
                .buildAndExpand("id", updatedAuthDTO.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK)
                .location(uri)
                .body(updatedAuthDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuth(@PathVariable("id")Long id) {
        authService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
