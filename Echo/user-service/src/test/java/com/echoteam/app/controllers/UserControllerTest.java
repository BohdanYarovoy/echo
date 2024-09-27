package com.echoteam.app.controllers;

import com.echoteam.app.entities.Sex;
import com.echoteam.app.entities.UserRole;
import com.echoteam.app.entities.dto.UserDTO;
import com.echoteam.app.entities.dto.UserRoleDTO;
import com.echoteam.app.exceptions.InProgress;
import com.echoteam.app.utils.PerformUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    public static final String USER_URI = "/api/v1/users";

    @Autowired
    private MockMvc mockMvc;


    // GET
    @Test
    @Order(1)
    void getUsers() throws Exception {
        mockMvc.perform(get(USER_URI)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    // GET BY ID
    @Test
    @Order(2)
    void getUserById() throws Exception {
        long id = 1L;
        mockMvc.perform(get(USER_URI + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(),
                        jsonPath("$.id").value(id),
                        jsonPath("$.nickname").isNotEmpty(),
                        jsonPath("$.email").isNotEmpty()
                );
    }

    @Test
    @Order(3)
    void getUserByNotExistsId() throws Exception {
        long id = 4L; // id 4 is not exists in db
        mockMvc.perform(get(USER_URI + "/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isNotFound());
    }

    // CREATE
    @Test
    @Order(4)
    void createUser() throws Exception {
        String nickname = "newuser";
        String email = "newuser@example.com";
        MockHttpServletRequestBuilder request = createUserWithId(null, nickname, email);

        mockMvc.perform(request).andExpectAll(
                status().isCreated(),
                jsonPath("$.id").value(4),
                jsonPath("$.created").value(Matchers.notNullValue()),
                jsonPath("$.nickname").value(nickname),
                jsonPath("$.email").value(email),
                jsonPath("$.roles").exists()
        );
    }

    @Test
    @Order(5)
    void createUserWithPreparedId() throws Exception {
        String nickname = "newuser";
        String email = "newuser@example.com";
        MockHttpServletRequestBuilder request = createUserWithId(5L, nickname, email);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    void createUserWithUniqueAlreadyExistingDbNickname() throws Exception {
        String nickname = "maybyes";
        String email = "newuser@example.com";
        MockHttpServletRequestBuilder request = createUserWithId(null, nickname, email);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    void createUserWithUniqueAlreadyExistingDbEmail() throws Exception {
        String nickname = "newuser";
        String email = "bogdan.yarovoy.01@gmail.com";
        MockHttpServletRequestBuilder request = createUserWithId(null, nickname, email);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    void createUserWithoutNotNullFieldNickname() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(9)
    void createUserWithoutNotNullFieldEmail() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(10)
    void createUserWithoutNotNullFieldFirstname() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(11)
    void createUserWithoutNotNullFieldLastname() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(12)
    void createUserWithoutNotNullFieldPassword() {
        // todo: implement logic
        throw new InProgress();
    }

    private MockHttpServletRequestBuilder createUserWithId(Long id, String nickname, String email) throws Exception {
        UserDTO user = new UserDTO(
                id,
                nickname,
                "John",
                "Doe",
                "Johnovich",
                Sex.MALE,
                email,
                "password123",
                LocalDate.of(1990, 10, 12),
                null,
                null,
                List.of(new UserRoleDTO((short) 2, "User"))
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String userAsJson = objectMapper.writeValueAsString(user);

        return post(USER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userAsJson);
    }


    // UPDATE
    @Test
    @Order(13)
    void updateUser() throws Exception {
        String nickname = "mark16",
                firstname = "Bob",
                lastname = "Dilan",
                patronymic = "MH17",
                email = "lisiy.from.brazzers@gmail.com",
                password = "mypassword";
        Sex sex = Sex.FEMALE;
        LocalDate dateOfBirth = LocalDate.of(2011, 9, 11);
        Timestamp created = null;
        Timestamp changed = null;
        List<UserRoleDTO> roles = List.of(
                new UserRoleDTO((short) 1, "ADMIN"),
                new UserRoleDTO((short) 2, "USER")
        );
        UserDTO changesForUser = new UserDTO(null, nickname, firstname, lastname, patronymic, sex, email, password, dateOfBirth, created, changed, roles);

        MockHttpServletRequestBuilder request = changeExistUser(changesForUser);

        mockMvc.perform(request).andExpectAll(
                status().isOk(),
                jsonPath("$.nickname").value(nickname),
                jsonPath("$.firstname").value(firstname),
                jsonPath("$.lastname").value(lastname),
                jsonPath("$.patronymic").value(patronymic),
                jsonPath("$.sex").value(sex.toString()),
                jsonPath("$.email").value(email),
                jsonPath("$.password").value(password),
                jsonPath("$.dateOfBirth").value(dateOfBirth.toString()),
                jsonPath("$.created").exists(),
                jsonPath("$.changed").exists(),
                jsonPath("$.roles.size()").value(2)
        );
    }

    private static MockHttpServletRequestBuilder changeExistUser(UserDTO userChanges) throws JsonProcessingException {
        Long id = 2L;
        String nickname = "jacoonda",
                firstname = "Evgene",
                lastname = "Olhovski",
                patronymic = null,
                email = "evene.olhovski@gmail.com",
                password = "pass1234";
        Sex sex = Sex.MALE;
        LocalDate dateOfBirth = LocalDate.of(2005, 5, 17);
        Timestamp created = null;
        Timestamp changed = null;
        List<UserRoleDTO> roles = List.of(new UserRoleDTO((short) 2, "USER"));
        UserDTO defaultUser = new UserDTO(id, nickname, firstname, lastname, patronymic, sex, email, password, dateOfBirth, created, changed, roles);

        UserDTO changedExistUser = PerformUser.mergeUserDTO(defaultUser, userChanges);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String userAsJson = objectMapper.writeValueAsString(changedExistUser);

        return put(USER_URI)
                .contentType(MediaType
                        .APPLICATION_JSON).content(userAsJson);
    }

    @Test
    @Order(14)
    void updateUserWithoutId() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(15)
    void updateUserWithUniqueAlreadyExistingDbNickname() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(16)
    void updateUserWithUniqueAlreadyExistingDbEmail() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(17)
    void updateUserWithBlankNotNullFieldNickname() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(18)
    void updateUserWithBlankNotNullFieldEmail() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(19)
    void updateUserWithBlankNotNullFieldFirstname() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(20)
    void updateUserWithBlankNotNullFieldLastname() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(21)
    void updateUserWithBlankNotNullFieldPassword() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(22)
    void deleteUser() {
        // todo: implement logic
        throw new InProgress();
    }

    @Test
    @Order(23)
    void deleteUserWithNotExistingId() {
        // todo: implement logic
        throw new InProgress();
    }
}