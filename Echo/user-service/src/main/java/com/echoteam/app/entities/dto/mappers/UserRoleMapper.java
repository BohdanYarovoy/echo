package com.echoteam.app.entities.dto.mappers;

import com.echoteam.app.entities.UserRole;
import com.echoteam.app.entities.dto.entityDTO.UserRoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    UserRole toUserRole(UserRoleDTO userRoleDTO);
    UserRoleDTO toDTO(UserRole userRole);

    List<UserRole> toUserRoles(List<UserRoleDTO> userRoleDTOs);
    List<UserRoleDTO> toDTOs(List<UserRole> userRoles);
}
