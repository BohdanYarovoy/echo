package com.echoteam.app.services;

import com.echoteam.app.entities.UserRole;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.exceptions.ParameterIsNullException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRoleService {

    public List<UserRole> getAll();

    public Optional<UserRole> findRoleById(Short id);

    public UserRole createUserRole(UserRole role) throws ParameterIsNotValidException;

    public UserRole updateUserRole(UserRole role);

    public void deleteById(Short id);

    public List<UserRole> getUserRolesByIdIn(Collection<Short> id);
}
