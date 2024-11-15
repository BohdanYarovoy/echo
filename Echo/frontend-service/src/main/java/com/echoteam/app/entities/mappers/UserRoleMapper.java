package com.echoteam.app.entities.mappers;

import com.echoteam.app.entities.UserRole;
import com.echoteam.app.entities.dto.changedDTO.ChangedRole;
import com.echoteam.app.entities.dto.createdDTO.CreatedRole;
import com.echoteam.app.entities.dto.nativeDTO.UserRoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    UserRoleMapper INSTANCE = Mappers.getMapper(UserRoleMapper.class);

    UserRoleDTO toDTOFromCreatedRole(CreatedRole createdRole);
    UserRoleDTO toDTOFromChangedRole(ChangedRole changedRole);
    UserRole toUserRole(UserRoleDTO userRoleDTO);

    UserRoleDTO toDTO(UserRole userRole);

    List<UserRole> toUserRoles(List<UserRoleDTO> userRoleDTOs);
    List<UserRoleDTO> toDTOs(List<UserRole> userRoles);
}
