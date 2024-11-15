package com.echoteam.app.controllers;

import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.dto.changedDTO.ChangedUserDetail;
import com.echoteam.app.entities.dto.createdDTO.CreatedDetail;
import com.echoteam.app.entities.dto.nativeDTO.UserDetailDTO;
import com.echoteam.app.services.UserDetailService;
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

    @GetMapping
    public ResponseEntity<PagedModel<UserDetailDTO>> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "10") int size,
                                                            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                                            @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        Sort sort = createSort(sortBy, direction);
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<UserDetail> pageResponse = userDetailService.getAll(pageRequest);
        PagedModel<UserDetailDTO> pagedModel = createPagedModel(pageResponse);
        return ResponseEntity.ok(pagedModel);
    }

    private PagedModel<UserDetailDTO> createPagedModel(Page<UserDetail> pageResponse) {
        Page<UserDetailDTO> pageDTOs = pageResponse.map(INSTANCE::toDTOFromUserDetail);
        return new PagedModel<>(pageDTOs);
    }

    private Sort createSort(String sortBy, String direction) {
        return direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
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
