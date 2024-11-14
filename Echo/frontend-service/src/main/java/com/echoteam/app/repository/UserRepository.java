package com.echoteam.app.repository;

import com.echoteam.app.entities.dto.nativeDTO.UserDTO;

public interface UserRepository {

    UserDTO getUserByNickname(String nickname);

}
