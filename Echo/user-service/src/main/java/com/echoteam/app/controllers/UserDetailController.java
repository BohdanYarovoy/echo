package com.echoteam.app.controllers;

import com.echoteam.app.entities.dto.entityDTO.changed.ChangedUserDetail;
import com.echoteam.app.entities.dto.entityDTO.created.CreatedDetail;
import com.echoteam.app.services.UserDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.echoteam.app.entities.dto.mappers.UserDetailMapper.INSTANCE;

@RestController
@RequestMapping("${application.endpoint.detail}")
@RequiredArgsConstructor
public class UserDetailController {
    @Value("${application.endpoint.detail}")
    public String detailUri;
    private final UserDetailService userDetailService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        var details = userDetailService.getAll();
        var detailsDTOs = INSTANCE.toDTOsFromUserDetails(details);
        return ResponseEntity.ok(detailsDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        var detail = userDetailService.getById(id);
        var detailDTO = INSTANCE.toDTOFromUserDetail(detail);
        return ResponseEntity.ok(detailDTO);
    }

    @PostMapping
    public ResponseEntity<?> createDetail(@Valid @RequestBody CreatedDetail createdDetail,
                                          UriComponentsBuilder uriBuilder) {
        var createdDetailDTO = INSTANCE.toDTOFromCreatedDetail(createdDetail);
        var detail = userDetailService.create(createdDetailDTO);
        var detailDTO = INSTANCE.toDTOFromUserDetail(detail);

        URI location = uriBuilder.replacePath(detailUri + "/{id}")
                .buildAndExpand(detailDTO.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(location)
                .body(detailDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateDetail(@Valid @RequestBody ChangedUserDetail changedUserDetail,
                                          UriComponentsBuilder uriBuilder) {
        var changedDetailDTO = INSTANCE.toDTOFromChangedDetail(changedUserDetail);
        var detail = userDetailService.update(changedDetailDTO);
        var detailDTO = INSTANCE.toDTOFromUserDetail(detail);

        URI location = uriBuilder.replacePath(detailUri + "/{id}")
                .buildAndExpand(detailDTO.getId()).toUri();
        return ResponseEntity.status(HttpStatus.OK)
                .location(location)
                .body(detailDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        userDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
