package com.echoteam.app.repository;

import com.echoteam.app.entities.dto.nativeDTO.UserAuthDTO;

public interface UserAuthRepository {
    UserAuthDTO getAuthById(Long id);
}
