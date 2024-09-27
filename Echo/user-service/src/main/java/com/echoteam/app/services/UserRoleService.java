package com.echoteam.app.services;

import com.echoteam.app.entities.dto.UserRoleDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;

import java.util.Collection;
import java.util.List;

public interface UserRoleService {

    List<UserRoleDTO> getAll();

    UserRoleDTO findRoleById(Short id);

    UserRoleDTO createUserRole(UserRoleDTO role) throws ParameterIsNotValidException;

    UserRoleDTO updateUserRole(UserRoleDTO role);

    void deleteById(Short id);

    List<UserRoleDTO> getUserRolesByIdIn(Collection<Short> id);
}
