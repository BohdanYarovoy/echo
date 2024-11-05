package com.echoteam.app.services;

import com.echoteam.app.entities.UserRole;
import com.echoteam.app.entities.dto.nativeDTO.UserRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface UserRoleService {

    Page<UserRole> getAll(Pageable pageable);
    UserRole findRoleById(Short id);
    UserRole createUserRole(UserRoleDTO role) ;
    UserRole updateUserRole(UserRoleDTO role);
    void deleteById(Short id);
    List<UserRole> getUserRolesByIdIn(Collection<Short> id);

}
