package com.echoteam.app.services;

import com.echoteam.app.entities.UserRole;
import com.echoteam.app.entities.dto.UserRoleDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserRoleServiceTest {

    @Autowired
    private UserRoleService userRoleService;

    @Test
    public void getAllUserRoles() {
        List<UserRoleDTO> userRoles = userRoleService.getAll();
        userRoles.forEach(System.out::println);
    }

    @Test
    public void getSpecifiedRolesByIn() {
        List<UserRoleDTO> userRoles = userRoleService.getUserRolesByIdIn(List.of((short) 1, (short) 3));
        userRoles.forEach(System.out::println);
    }

    @Test
    public void addRoleToDB() {
        UserRoleDTO role = new UserRoleDTO(null, "FOMA");
        try {
            userRoleService.createUserRole(role);
        } catch (ParameterIsNotValidException e) {
            throw new RuntimeException(e);
        }
    }

}