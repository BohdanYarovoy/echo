package com.echoteam.app.services;

import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.dto.entityDTO.UserAuthDTO;

import java.util.List;

public interface UserAuthService {
    List<UserAuth> getAll();
    UserAuth getById(Long id);
    UserAuth create(UserAuthDTO createdAuth);
    UserAuth update(UserAuthDTO updatedAuth);
    void delete(Long id);

}
