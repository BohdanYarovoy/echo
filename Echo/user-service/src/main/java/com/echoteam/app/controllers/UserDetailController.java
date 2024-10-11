package com.echoteam.app.controllers;

import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.dto.entityDTO.CreatedDetail;
import com.echoteam.app.entities.dto.entityDTO.UserDetailDTO;
import com.echoteam.app.services.UserDetailService;
import com.echoteam.app.services.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.echoteam.app.entities.dto.mappers.UserDetailMapper.INSTANCE;

@RestController
@RequestMapping("${application.endpoint.detail}")
@RequiredArgsConstructor
public class UserDetailController {
    @Value("${application.endpoint.detail}")
    public String detailUri;
    private final UserDetailService userDetailService;
    private final ValidationService validationService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<UserDetail> details = userDetailService.getAll();
        List<UserDetailDTO> detailsDTOs = INSTANCE.toDTOsFromUserDetails(details);
        return ResponseEntity.ok(detailsDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        UserDetail detail = userDetailService.getById(id);
        UserDetailDTO detailDTO = INSTANCE.toDTOFromUserDetail(detail);
        return ResponseEntity.ok(detailDTO);
    }

    @PostMapping
    public ResponseEntity<?> createDetail(@RequestBody CreatedDetail userDetail,
                                          UriComponentsBuilder uriBuilder) {
        validationService.isValid(userDetail);
        UserDetailDTO userDetailDTO = INSTANCE.toDTOFromCreatedDetail(userDetail);
        UserDetail detail = userDetailService.create(userDetailDTO);
        UserDetailDTO detailDTO = INSTANCE.toDTOFromUserDetail(detail);

        URI location = uriBuilder.replacePath(detailUri + "/{id}")
                .buildAndExpand(detailDTO.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(location)
                .body(detailDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateDetail(@RequestBody UserDetailDTO userDetailDTO,
                                          UriComponentsBuilder uriBuilder) {
        UserDetail detail = userDetailService.update(userDetailDTO);
        UserDetailDTO detailDTO = INSTANCE.toDTOFromUserDetail(detail);

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
