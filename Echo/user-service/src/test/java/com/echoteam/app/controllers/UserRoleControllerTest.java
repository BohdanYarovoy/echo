package com.echoteam.app.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRoleControllerTest {

    public static final String USER_ROLE_URI = "/api/v1/user-roles";

    /*
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void getAllUserRoles() throws Exception {
        MockHttpServletRequestBuilder request = get(USER_ROLE_URI).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpectAll(
                status().isOk(),
                jsonPath("$.size()").value(3)
        );
    }

    @Test
    void getUserRoleById() throws Exception {
        Long id = 2L;
        MockHttpServletRequestBuilder request = get(USER_ROLE_URI + "/" + id)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpectAll(
                status().isOk(),
                jsonPath("$.id").value(id),
                jsonPath("$.name").value("USER")
        );
    }

    @Test
    void getUserRoleByNotExistingId() throws Exception {
        Long id = 200L;
        MockHttpServletRequestBuilder request = get(USER_ROLE_URI + "/" + id)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpectAll(
                status().isNotFound(),
                jsonPath("$.body.detail").value("Role with id " + id + " not found.")
        );
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
*/
}