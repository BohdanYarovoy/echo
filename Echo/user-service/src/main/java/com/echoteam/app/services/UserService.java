package com.echoteam.app.services;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.nativeDTO.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    Page<User> getAll(Pageable pageable);
    User getById(Long id);
    User getByNickname(String nickname);
    User createUser(UserDTO user);
    User updateUser(UserDTO user);
    void deleteById(Long id);

}
