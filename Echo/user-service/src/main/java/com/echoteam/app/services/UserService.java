package com.echoteam.app.services;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.entityDTO.UserDTO;

import java.util.List;


public interface UserService {

    List<User> getAll();
    User getById(Long id);
    User createUser(UserDTO user);
    User updateUser(UserDTO user);
    void deleteById(Long id);

}
