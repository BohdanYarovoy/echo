package com.echoteam.app.unit.entities.mappers;

import com.echoteam.app.entities.UserRole;
import com.echoteam.app.entities.dto.changedDTO.ChangedRole;
import com.echoteam.app.entities.dto.createdDTO.CreatedRole;
import com.echoteam.app.entities.dto.nativeDTO.UserRoleDTO;
import com.echoteam.app.entities.mappers.UserRoleMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRoleMapperTest {
    private final UserRoleMapper mapper = UserRoleMapper.INSTANCE;

    @Test
    void mapToDTO_fromCreatedRoleTest() {
        CreatedRole createdRole = CreatedRole.getValidInstance();
        UserRoleDTO dto = mapper.toDTOFromCreatedRole(createdRole);

        assertThat(dto.getName()).isEqualTo(createdRole.getName());

        assertThat(dto.getId()).isNull();
    }

    @Test
    void mapToDTO_fromChangedRole() {
        ChangedRole changedRole = ChangedRole.getValidInstance();
        UserRoleDTO dto = mapper.toDTOFromChangedRole(changedRole);

        assertThat(dto.getId()).isEqualTo(changedRole.getId());
        assertThat(dto.getName()).isEqualTo(changedRole.getName());
    }

    @Test
    void mapToRole_fromDTO() {
        UserRoleDTO dto = UserRoleDTO.getValidInstance();
        UserRole role = mapper.toUserRole(dto);

        assertThat(role.getId()).isEqualTo(dto.getId());
        assertThat(role.getName()).isEqualTo(dto.getName());
        assertThat(role.getUsers()).isNull();
    }

    @Test
    void mapToDTO_fromRole() {
        UserRole role = UserRole.getValidInstance();
        UserRoleDTO dto = mapper.toDTO(role);

        assertThat(dto.getId()).isEqualTo(role.getId());
        assertThat(dto.getName()).isEqualTo(role.getName());
    }

}
