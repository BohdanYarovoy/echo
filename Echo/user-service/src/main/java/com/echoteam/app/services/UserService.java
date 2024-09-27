package com.echoteam.app.services;

import com.echoteam.app.entities.dto.UserDTO;

import java.util.List;


public interface UserService {

    List<UserDTO> getAll();

    UserDTO getById(Long id);

    UserDTO createUser(UserDTO user);

    UserDTO updateUser(UserDTO user);

    void deleteById(Long id);
}
