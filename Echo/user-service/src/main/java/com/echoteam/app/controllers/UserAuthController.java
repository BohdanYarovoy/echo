package com.echoteam.app.controllers;

import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.dto.entityDTO.CreatedAuth;
import com.echoteam.app.entities.dto.entityDTO.UserAuthDTO;
import com.echoteam.app.services.UserAuthService;
import com.echoteam.app.services.validation.ValidationService;
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
    private final ValidationService validationService;

    @GetMapping
    public ResponseEntity<List<UserAuthDTO>> getAll() {
        List<UserAuth> auths = authService.getAll();
        List<UserAuthDTO> authDTOs = INSTANCE.toDTOsFromAuths(auths);
        return ResponseEntity.ok(authDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAuthDTO> getById(@PathVariable("id")Long id) {
        UserAuth auth = authService.getById(id);
        UserAuthDTO authDTO = INSTANCE.toDTOFromUserAuth(auth);
        return ResponseEntity.ok(authDTO);
    }

    @PostMapping
    public ResponseEntity<UserAuthDTO> createAuth(@RequestBody CreatedAuth createdAuth,
                                                  UriComponentsBuilder uriBuilder) {
        validationService.isValid(createdAuth);
        UserAuthDTO createdAuthDTO = INSTANCE.toDTOFromCreatedAuth(createdAuth);
        UserAuth savedAuth = authService.create(createdAuthDTO);
        UserAuthDTO authDTO = INSTANCE.toDTOFromUserAuth(savedAuth);

        URI uri = uriBuilder.replacePath(authUri + "/{id}")
                .buildAndExpand("id", authDTO.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(authDTO);
    }

    @PutMapping
    public ResponseEntity<UserAuthDTO> updateAuth(@RequestBody UserAuthDTO authDTO,
                                                  UriComponentsBuilder uriBuilder) {
        UserAuth updatedAuth = authService.update(authDTO);
        UserAuthDTO updatedAuthDTO = INSTANCE.toDTOFromUserAuth(updatedAuth);
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
