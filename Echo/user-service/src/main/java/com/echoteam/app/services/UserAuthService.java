package com.echoteam.app.services;

import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.dto.nativeDTO.UserAuthDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserAuthService {
    Page<UserAuth> getAll(Pageable pageable);
    UserAuth getById(Long id);
    UserAuth create(UserAuthDTO createdAuth);
    UserAuth update(UserAuthDTO updatedAuth);
    void delete(Long id);

}
