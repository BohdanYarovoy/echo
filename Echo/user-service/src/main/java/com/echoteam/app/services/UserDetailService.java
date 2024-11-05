package com.echoteam.app.services;

import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.dto.nativeDTO.UserDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserDetailService {

    Page<UserDetail> getAll(Pageable pageable);
    UserDetail getById(Long id);
    UserDetail create(UserDetailDTO userDetailDTO);
    UserDetail update(UserDetailDTO userDetailDTO);
    void delete(Long id);

}
