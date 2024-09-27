package com.echoteam.app.controllers;

import com.echoteam.app.exceptions.InProgress;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRoleControllerTest {

    public static final String USER_ROLE_URI = "/api/v1/user-roles";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void getAllUserRoles() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    void getUserRoleByID() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    void getUserRoleByIds() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    void createUserRole() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    void updateUserRole() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    void deleteUserRole() {
        // todo: implement logic
        throw new InProgress();
    }

}