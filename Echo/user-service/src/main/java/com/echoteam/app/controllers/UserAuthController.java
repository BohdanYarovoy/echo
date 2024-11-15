package com.echoteam.app.controllers;

import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.dto.changedDTO.ChangedUserAuth;
import com.echoteam.app.entities.dto.createdDTO.CreatedAuth;
import com.echoteam.app.entities.dto.nativeDTO.UserAuthDTO;
import com.echoteam.app.services.UserAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.echoteam.app.entities.mappers.UserAuthMapper.INSTANCE;


@Controller
@RequestMapping("${application.endpoint.auth}")
@RequiredArgsConstructor
public class UserAuthController {
    public static String authUri;
    private final UserAuthService authService;

    @Value("${application.endpoint.auth}")
    public void setAuthUri(String authUri) {
        UserAuthController.authUri = authUri;
    }

    @GetMapping
    public ResponseEntity<PagedModel<UserAuthDTO>> getAll(@RequestParam(name ="page",defaultValue = "0") int page,
                                                          @RequestParam(name ="size",defaultValue = "10") int size,
                                                          @RequestParam(name ="sortBy",defaultValue = "id") String sortBy,
                                                          @RequestParam(name ="direction",defaultValue = "asc") String direction) {
        var sort = createSort(sortBy, direction);
        var pageRequest = PageRequest.of(page, size, sort);
        Page<UserAuth> pageResponse = authService.getAll(pageRequest);

        PagedModel<UserAuthDTO> pageDTOs = createPageModel(pageResponse);
        return ResponseEntity.ok(pageDTOs);
    }

    private PagedModel<UserAuthDTO> createPageModel(Page<UserAuth> pageResponse) {
        Page<UserAuthDTO> pageDTOs = pageResponse.map(INSTANCE::toDTOFromUserAuth);
        return new PagedModel<>(pageDTOs);
    }

    private Sort createSort(String sortBy, String direction) {
        return direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
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

        URI uri = uriBuilder.replacePath(authUri + "/{id}")
                .buildAndExpand(savedAuth.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .build();
    }

    @PutMapping
    public ResponseEntity<UserAuthDTO> updateAuth(@Valid @RequestBody ChangedUserAuth changedUserAuth,
                                                  UriComponentsBuilder uriBuilder) {
        var changedAuthDTO = INSTANCE.toDTOFromChangedAuth(changedUserAuth);
        var updatedAuth = authService.update(changedAuthDTO);

        URI uri = uriBuilder.replacePath(authUri + "/{id}")
                .buildAndExpand(updatedAuth.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.OK)
                .location(uri)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuth(@PathVariable("id")Long id) {
        authService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
