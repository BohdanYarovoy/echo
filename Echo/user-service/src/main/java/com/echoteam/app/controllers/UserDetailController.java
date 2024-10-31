package com.echoteam.app.controllers;

import com.echoteam.app.entities.dto.changedDTO.ChangedUserDetail;
import com.echoteam.app.entities.dto.createdDTO.CreatedDetail;
import com.echoteam.app.services.UserDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.echoteam.app.entities.mappers.UserDetailMapper.INSTANCE;

@RestController
@RequestMapping("${application.endpoint.detail}")
@RequiredArgsConstructor
public class UserDetailController {
    public static String detailUri;
    private final UserDetailService userDetailService;

    @Value("${application.endpoint.detail}")
    public void setDetailUri(String uri) {
        UserDetailController.detailUri = uri;
    }

    // todo: this method need to be with pagination, because when client will request, he can get all entities.
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

        URI location = uriBuilder.replacePath(detailUri + "/{id}")
                .buildAndExpand(detail.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(location)
                .build();
    }

    @PutMapping
    public ResponseEntity<?> updateDetail(@Valid @RequestBody ChangedUserDetail changedUserDetail,
                                          UriComponentsBuilder uriBuilder) {
        var changedDetailDTO = INSTANCE.toDTOFromChangedDetail(changedUserDetail);
        var detail = userDetailService.update(changedDetailDTO);

        URI location = uriBuilder.replacePath(detailUri + "/{id}")
                .buildAndExpand(detail.getId()).toUri();
        return ResponseEntity.status(HttpStatus.OK)
                .location(location)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        userDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
