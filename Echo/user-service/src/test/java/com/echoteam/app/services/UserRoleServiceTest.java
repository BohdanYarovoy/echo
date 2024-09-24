package com.echoteam.app.services;

import com.echoteam.app.entities.UserRole;
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
        List<UserRole> userRoles = userRoleService.getAll();
        userRoles.forEach(r -> System.out.println(r.getName()));
    }

    @Test
    public void getSpecifiedRolesByIn() {
        List<UserRole> userRoles = userRoleService.getUserRolesByIdIn(List.of((short) 1, (short) 3));
        userRoles.forEach(r -> System.out.println(r.getName()));
    }

}