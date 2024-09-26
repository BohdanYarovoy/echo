package com.echoteam.app.services;

import com.echoteam.app.entities.UserRole;
import com.echoteam.app.entities.dto.UserRoleDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRoleService {

    public List<UserRoleDTO> getAll();

    public UserRoleDTO findRoleById(Short id);

    public UserRoleDTO createUserRole(UserRoleDTO role) throws ParameterIsNotValidException;

    public UserRoleDTO updateUserRole(UserRoleDTO role);

    public void deleteById(Short id);

    public List<UserRoleDTO> getUserRolesByIdIn(Collection<Short> id);
}
