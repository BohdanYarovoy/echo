package com.echoteam.app.controllers;

import com.echoteam.app.entities.Sex;
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
/*

    @Test
    @Order(1)
    void getUsers() throws Exception {
        mockMvc.perform(get(USER_URI).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    @Order(2)
    void getUserById() throws Exception {
        long id = 1L;
        mockMvc.perform(get(USER_URI + "/" + id).accept(MediaType.APPLICATION_JSON))
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
        mockMvc.perform(get(USER_URI + "/" + id).accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.body.detail").value("User with id " + id + " not found.")
                );
    }

    @Test
    @Order(4)
    void createUser() throws Exception {
        String nickname = "newuser";
        String firstname = "Joe";
        String lastname = "Beaber";
        String email = "newuser@example.com";
        String password = "pass1234";
        MockHttpServletRequestBuilder request = createUserWithId(null,nickname,firstname,lastname,email,password);

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
        String firstname = "Joe";
        String lastname = "Beaber";
        String email = "newuser@example.com";
        String password = "pass1234";
        MockHttpServletRequestBuilder request = createUserWithId(5L,nickname,firstname,lastname,email,password);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("There is no need user with id.")
        );
    }

    @Test
    @Order(6)
    void createUserWithUniqueAlreadyExistingDbNickname() throws Exception {
        String nickname = "maybyes";
        String firstname = "Joe";
        String lastname = "Beaber";
        String email = "newuser@example.com";
        String password = "pass1234";
        MockHttpServletRequestBuilder request = createUserWithId(null,nickname,firstname,lastname,email,password);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Entity with such nickname already exist.")
        );
    }

    @Test
    @Order(7)
    void createUserWithUniqueAlreadyExistingDbEmail() throws Exception {
        String nickname = "holobobo";
        String firstname = "Joe";
        String lastname = "Beaber";
        String email = "bogdan.yarovoy.01@gmail.com";
        String password = "pass1234";
        MockHttpServletRequestBuilder request = createUserWithId(null,nickname,firstname,lastname,email,password);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Entity with such email already exist.")
        );
    }

    @Test
    @Order(8)
    void createUserWithoutNotNullFieldNickname() throws Exception {
        String nickname = "";       // nickname is empty
        String firstname = "Joe";
        String lastname = "Beaber";
        String email = "bogdan.yarovoy.01@gmail.com";
        String password = "pass1234";
        MockHttpServletRequestBuilder request = createUserWithId(null,nickname,firstname,lastname,email,password);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Field nickname cannot be empty.")
        );
    }

    @Test
    @Order(9)
    void createUserWithoutNotNullFieldEmail() throws Exception {
        String nickname = "newuserWithoutEmain";
        String firstname = "Joe";
        String lastname = "Beaber";
        String email = "";                      // email is empty
        String password = "pass1234";
        MockHttpServletRequestBuilder request = createUserWithId(null,nickname,firstname,lastname,email,password);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Field email cannot be empty.")
        );
    }

    @Test
    @Order(10)
    void createUserWithoutNotNullFieldFirstname() throws Exception {
        String nickname = "newuserWithoutFirstname";
        String firstname = "";                  // firstname is empty
        String lastname = "Beaber";
        String email = "sofar@gmail.com";
        String password = "pass1234";
        MockHttpServletRequestBuilder request = createUserWithId(null,nickname,firstname,lastname,email,password);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Field firstname cannot be empty.")
        );
    }

    @Test
    @Order(11)
    void createUserWithoutNotNullFieldLastname() throws Exception {
        String nickname = "newuserWithoutLastname";
        String firstname = "firstname";
        String lastname = "";                   // lastname is empty
        String email = "sofar@gmail.com";
        String password = "pass1234";
        MockHttpServletRequestBuilder request = createUserWithId(null,nickname,firstname,lastname,email,password);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Field lastname cannot be empty.")
        );
    }

    @Test
    @Order(12)
    void createUserWithoutNotNullFieldPassword() throws Exception {
        String nickname = "newuserWithoutPassword";
        String firstname = "firstname";
        String lastname = "lastname";
        String email = "sofar@gmail.com";
        String password = "";           // password is empty
        MockHttpServletRequestBuilder request = createUserWithId(null,nickname,firstname,lastname,email,password);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Field password cannot be empty.")
        );
    }

    private MockHttpServletRequestBuilder createUserWithId(Long id, String nickname,
                                                           String firstname, String lastname,
                                                           String email, String password) throws Exception {
        UserDTO user = new UserDTO(
                id,
                nickname,
                firstname,
                lastname,
                "Johnovich",
                Sex.MALE,
                email,
                password,
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

    @Test
    @Order(13)
    void updateUser() throws Exception {
        Long id = 2L;
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
        UserDTO changesForUser = new UserDTO(id, nickname, firstname, lastname, patronymic, sex, email, password, dateOfBirth, created, changed, roles);
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

    @Test
    @Order(14)
    void updateUserWithoutId() throws Exception {
        Long id = null;                         // id is null
        String nickname = null,
                firstname = null,
                lastname = null,
                patronymic = null,
                email = null,
                password = null;
        Sex sex = null;
        LocalDate dateOfBirth = null;
        Timestamp created = null;
        Timestamp changed = null;
        List<UserRoleDTO> roles = null;
        UserDTO changesForUser = new UserDTO(id, nickname, firstname, lastname, patronymic, sex, email, password, dateOfBirth, created, changed, roles);
        MockHttpServletRequestBuilder request = changeExistUser(changesForUser);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Id is required.")
        );
    }

    @Test
    @Order(15)
    void updateUserWithUniqueAlreadyExistingDbNickname() throws Exception {
        Long id = 2L;
        String nickname = "maybyes",                     // such nickname already exists
                firstname = null,
                lastname = null,
                patronymic = null,
                email = null,
                password = null;
        Sex sex = null;
        LocalDate dateOfBirth = null;
        Timestamp created = null;
        Timestamp changed = null;
        List<UserRoleDTO> roles = null;
        UserDTO changesForUser = new UserDTO(id, nickname, firstname, lastname, patronymic, sex, email, password, dateOfBirth, created, changed, roles);
        MockHttpServletRequestBuilder request = changeExistUser(changesForUser);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Entity with such nickname already exist.")
        );
    }

    @Test
    @Order(16)
    void updateUserWithUniqueAlreadyExistingDbEmail() throws Exception {
        Long id = 2L;
        String nickname = null,
                firstname = null,
                lastname = null,
                patronymic = null,
                email = "bogdan.yarovoy.01@gmail.com",          // such email already exists
                password = null;
        Sex sex = null;
        LocalDate dateOfBirth = null;
        Timestamp created = null;
        Timestamp changed = null;
        List<UserRoleDTO> roles = null;
        UserDTO changesForUser = new UserDTO(id, nickname, firstname, lastname, patronymic, sex, email, password, dateOfBirth, created, changed, roles);
        MockHttpServletRequestBuilder request = changeExistUser(changesForUser);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Entity with such email already exist.")
        );
    }

    @Test
    @Order(17)
    void updateUserWithBlankNotNullFieldNickname() throws Exception {
        Long id = 2L;
        String nickname = "",                     // nickname is blank
                firstname = null,
                lastname = null,
                patronymic = null,
                email = null,
                password = null;
        Sex sex = null;
        LocalDate dateOfBirth = null;
        Timestamp created = null;
        Timestamp changed = null;
        List<UserRoleDTO> roles = null;
        UserDTO changesForUser = new UserDTO(id, nickname, firstname, lastname, patronymic, sex, email, password, dateOfBirth, created, changed, roles);
        MockHttpServletRequestBuilder request = changeExistUser(changesForUser);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Field nickname cannot be empty.")
        );
    }

    @Test
    @Order(18)
    void updateUserWithBlankNotNullFieldEmail() throws Exception {
        Long id = 2L;
        String nickname = null,
                firstname = null,
                lastname = null,
                patronymic = null,
                email = "",               // email is blank
                password = null;
        Sex sex = null;
        LocalDate dateOfBirth = null;
        Timestamp created = null;
        Timestamp changed = null;
        List<UserRoleDTO> roles = null;
        UserDTO changesForUser = new UserDTO(id, nickname, firstname, lastname, patronymic, sex, email, password, dateOfBirth, created, changed, roles);
        MockHttpServletRequestBuilder request = changeExistUser(changesForUser);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Field email cannot be empty.")
        );
    }

    @Test
    @Order(19)
    void updateUserWithBlankNotNullFieldFirstname() throws Exception {
        Long id = 2L;
        String nickname = null,
                firstname = "",           // firstname is blank
                lastname = null,
                patronymic = null,
                email = null,
                password = null;
        Sex sex = null;
        LocalDate dateOfBirth = null;
        Timestamp created = null;
        Timestamp changed = null;
        List<UserRoleDTO> roles = null;
        UserDTO changesForUser = new UserDTO(id, nickname, firstname, lastname, patronymic, sex, email, password, dateOfBirth, created, changed, roles);
        MockHttpServletRequestBuilder request = changeExistUser(changesForUser);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Field firstname cannot be empty.")
        );
    }

    @Test
    @Order(20)
    void updateUserWithBlankNotNullFieldLastname() throws Exception {
        Long id = 2L;
        String nickname = null,
                firstname = null,
                lastname = "",            // lastname is blank
                patronymic = null,
                email = null,
                password = null;
        Sex sex = null;
        LocalDate dateOfBirth = null;
        Timestamp created = null;
        Timestamp changed = null;
        List<UserRoleDTO> roles = null;
        UserDTO changesForUser = new UserDTO(id, nickname, firstname, lastname, patronymic, sex, email, password, dateOfBirth, created, changed, roles);
        MockHttpServletRequestBuilder request = changeExistUser(changesForUser);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Field lastname cannot be empty.")
        );
    }

    @Test
    @Order(21)
    void updateUserWithBlankNotNullFieldPassword() throws Exception {
        Long id = 2L;
        String nickname = null,
                firstname = null,
                lastname = null,
                patronymic = null,
                email = null,
                password = "";
        Sex sex = null;
        LocalDate dateOfBirth = null;
        Timestamp created = null;
        Timestamp changed = null;
        List<UserRoleDTO> roles = null;
        UserDTO changesForUser = new UserDTO(id, nickname, firstname, lastname, patronymic, sex, email, password, dateOfBirth, created, changed, roles);
        MockHttpServletRequestBuilder request = changeExistUser(changesForUser);

        mockMvc.perform(request).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.body.detail").value("Field password cannot be empty.")
        );
    }

    private static MockHttpServletRequestBuilder changeExistUser(UserDTO userChanges) throws JsonProcessingException {
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
        UserDTO defaultUser = new UserDTO(null, nickname, firstname, lastname, patronymic, sex, email, password, dateOfBirth, created, changed, roles);

        UserDTO changedExistUser = PerformUser.mergeUserDTO(defaultUser, userChanges);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String userAsJson = objectMapper.writeValueAsString(changedExistUser);

        return put(USER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userAsJson);
    }

    @Test
    @Order(22)
    void deleteUser() throws Exception {
        Long id = 1L;
        MockHttpServletRequestBuilder request = delete(USER_URI + "/" + id)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    @Order(23)
    void deleteUserWithNotExistingId() throws Exception {
        Long id = 20L;
        MockHttpServletRequestBuilder request = delete(USER_URI + "/" + id)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request).andExpectAll(
                status().isNotFound(),
                jsonPath("$.body.detail").value("User with id " + id + " not found.")
        );
    }

    */
}