package com.echoteam.app.services;

import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.dto.entityDTO.UserDetailDTO;

import java.util.List;

public interface UserDetailService {

    List<UserDetail> getAll();
    UserDetail getById(Long id);
    UserDetail create(UserDetailDTO userDetailDTO);
    UserDetail update(UserDetailDTO userDetailDTO);
    void delete(Long id);

}
