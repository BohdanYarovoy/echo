package com.echoteam.app.integration.controllers;

import com.echoteam.app.controllers.UserController;
import com.echoteam.app.entities.dto.changedDTO.ChangedUser;
import com.echoteam.app.entities.dto.createdDTO.CreatedUser;
import com.echoteam.app.entities.dto.nativeDTO.UserDTO;
import com.echoteam.app.entities.mappers.UserMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @Autowired
    TestRestTemplate template;

    @Test
    void getUsers_shouldReturn200_whenIsRequested() {
        ResponseEntity<UserDTO[]> response = template.getForEntity(UserController.userUri, UserDTO[].class);
        assertThat(response.getStatusCode())
                .as("Expected status code 200 OK when requesting all " +
                    "users, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .as("Expected to receive 3 users from the database, but received different amount.")
                .hasSize(3);
    }

    @Test
    void userById_shouldReturn200_whenItIsOk() {
        int requestId = 1;
        ResponseEntity<UserDTO> response = template.getForEntity(
                UserController.userUri + "/" + requestId,
                UserDTO.class
        );
        assertThat(response.getStatusCode())
                .as("Expected status code 200 OK when requesting user " +
                    "with id 1, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        UserDTO user = response.getBody();
        assertThat(user)
                .as("Expected user after request at endpoint 'userById', but user is null.")
                .isNotNull();
        assertThat(user.getId())
                .as("Expected user with id 1, but got user with different id.")
                .isEqualTo(requestId);
    }

    @Test
    void userById_shouldReturn404_whenUserWithSuchIdIsNotExists() {
        int requestId = 5;
        ResponseEntity<String> response = template.getForEntity(
                UserController.userUri + "/" + requestId,
                String.class
        );
        assertThat(response.getStatusCode())
                .as("Expected status code 404 NOT_FOUND when requesting a user with " +
                    "non-existing id, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(response.getBody())
                .as("Expected a response body with error details, but it was null.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        String title = context.read("$.body.title");
        String detail = context.read("$.body.detail");
        assertThat(title)
                .as("Expected error title to be 'Not Found', but got '%s'.", title)
                .isEqualTo("Not Found");
        assertThat(detail)
                .as("Expected error detail to be 'User with id 5 not found.', but got '%s'.", detail)
                .isEqualTo("User with id 5 not found.");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createUser_shouldReturn201_whenIsValid() {
        CreatedUser createdUser = CreatedUser.getValidInstance();
        ResponseEntity<String> response = template.postForEntity(
                UserController.userUri,
                createdUser,
                String.class
        );

        assertThat(response.getStatusCode())
                .as("Expected status code 201 CREATED after successfully adding " +
                    "a new user, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        // Test location header
        URI location = response.getHeaders().getLocation();
        assertThat(location)
                .as("Expected location header to contain the URI of the created user, but it was null.")
                .isNotNull();

        ResponseEntity<UserDTO> getResponse = template.getForEntity(location, UserDTO.class);
        assertThat(getResponse.getStatusCode())
                .as("Expected status code 200 OK when fetching the newly created user, but got %s.", getResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody())
                .as("Expected user details when fetching the newly created user, but response body was null.")
                .isNotNull();

        // Test user fields
        UserDTO responseUser = getResponse.getBody();
        assertThat(responseUser)
                .as("Expected UserDTO in response body after user creation, but it was null.")
                .isNotNull();
        assertThat(responseUser.getNickname())
                .as("Expected nickname to be '%s', but got '%s'.", createdUser.getNickname(), responseUser.getNickname())
                .isEqualTo(createdUser.getNickname());
        assertThat(responseUser.getRoles())
                .as("Expected user roles size to match created user roles, but got different sizes.")
                .hasSize(createdUser.getRoles().size());
        assertThat(responseUser.getCreated())
                .as("Expected creation timestamp to be set, but it was null.")
                .isNotNull();
        assertThat(responseUser.getIsDeleted())
                .as("Expected 'isDeleted' field to be false, but it was true.")
                .isFalse();

        // Test fields that should be null
        assertThat(responseUser.getAvatar())
                .as("Expected avatar to be null for newly created user, but it was not.")
                .isNull();
        assertThat(responseUser.getChanged())
                .as("Expected 'changed' field to be null for newly created user, but it was not.")
                .isNull();
        assertThat(responseUser.getUserDetail())
                .as("Expected 'userDetail' field to be null for newly created user, but it was not.")
                .isNull();
        assertThat(responseUser.getUserAuth())
                .as("Expected 'userAuth' field to be null for newly created user, but it was not.")
                .isNull();
    }

    @Test
    void createUser_shouldReturn400_whenCreatedUserIsInvalid() {
        CreatedUser createdUser = CreatedUser.getValidInstance();
        createdUser.setNickname(null);  // invalidate the request

        ResponseEntity<String> response = template.postForEntity(
                UserController.userUri,
                createdUser,
                String.class
        );

        assertThat(response.getStatusCode())
                .as("Expected status code 400 BAD_REQUEST when creating user " +
                    "with invalid data, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        DocumentContext context = JsonPath.parse(response.getBody());
        String title = context.read("$.body.title");
        String violation = context.read("$.body.nickname");
        assertThat(title)
                .as("Expected error title to be 'Validation Error', but got '%s'.", title)
                .isEqualTo("Validation Error");
        assertThat(violation)
                .as("Expected error message for 'nickname' field to be 'Nickname" +
                    " can't be empty', but got '%s'.", violation)
                .isEqualTo("Nickname can't be empty");
    }

    @Test
    void createUser_shouldReturn400_whenCreatedUserHasFieldsThatAlreadyExistsInDb() {
        CreatedUser createdUser = CreatedUser.getValidInstance();
        createdUser.setNickname("nickname1");   // invalidate the request

        ResponseEntity<String> response = template.postForEntity(
                UserController.userUri,
                createdUser,
                String.class
        );

        assertThat(response.getStatusCode())
                .withFailMessage("Expected status code 400 BAD_REQUEST when creating " +
                                 "user has fields with already existing data, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        DocumentContext context = JsonPath.parse(response.getBody());
        String violationTitle = context.read("$.body.title");
        String violationDetail = context.read("$.body.detail");
        assertThat(violationTitle)
                .as("Expected violation title as 'Bad Request', but got %s.", violationTitle)
                .isEqualTo("Bad Request");
        assertThat(violationDetail)
                .as("Expected violation detail as 'Entity with such nickname already exist.', but got %s.", violationDetail)
                .isEqualTo("Entity with such nickname already exist.");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateUser_shouldReturn200_whenIsValid() {
        // given
        int userId = 1;
        ResponseEntity<UserDTO> beforeChangingResponse = template.getForEntity(
                UserController.userUri + "/" + userId,
                UserDTO.class
        );
        assertThat(beforeChangingResponse.getStatusCode())
                .withFailMessage("Expected status code 200 OK when we do get request by id:1, but got %s.", beforeChangingResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(beforeChangingResponse.getBody())
                .as("Expected body after call get method by id:1.")
                .isNotNull();
        ChangedUser user = UserMapper.INSTANCE.toChangedFromDTO(beforeChangingResponse.getBody());

        user.setNickname("nickname53");
        // when
        ResponseEntity<String> changingResponse = template.exchange(
                UserController.userUri,
                HttpMethod.PUT,
                new HttpEntity<>(user),
                String.class
        );
        assertThat(changingResponse.getStatusCode())
                .withFailMessage("Expected status code 200 OK after changing user, but got %s.", changingResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        URI location = changingResponse.getHeaders().getLocation();
        assertThat(location)
                .as("Expected location provided by put method, and field location mustn't be null.")
                .isNotNull();

        // then
        ResponseEntity<UserDTO> afterChangingResponse = template.getForEntity(
                location,
                UserDTO.class
        );
        assertThat(afterChangingResponse.getStatusCode())
                .withFailMessage("Expected status code 200 OK after using location that was provided " +
                                 "by put method, but got %s.", afterChangingResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        // assertion
        UserDTO before = beforeChangingResponse.getBody();
        UserDTO after = afterChangingResponse.getBody();

        assertThat(before.getNickname())
                .as("Expect that nickname will be different after changing, but it isn't.")
                .isNotEqualTo(after.getNickname());
        assertThat(after.getNickname())
                .as("Expect that nicknames changed user and user that was prepared for changing is equal.")
                .isEqualTo(user.getNickname());
        assertThat(user.getRoles())
                .as("Expect that roles will be that same, because it changes only nickname.")
                .hasSameElementsAs(before.getRoles());
        assertThat(after.getAvatar())
                .as("Expect that avatars will be that same, because it changes only nickname.")
                .isEqualTo(before.getAvatar());
        assertThat(after.getCreated())
                .as("Expect that created data will be that same, because it changes only nickname.")
                .isEqualTo(before.getCreated());
        assertThat(after.getIsDeleted())
                .as("Expect that isDeleted flags will be that same, because it changes only nickname.")
                .isEqualTo(before.getIsDeleted());

        assertThat(after.getChanged())
                .as("Expect that after changing timestamp will be updated.")
                .isNotEqualTo(before.getChanged());
    }

    @Test
    void updateUser_shouldReturn404_whenUserWithSuchIdIsNotExist() {
        ChangedUser user = ChangedUser.getValidInstance();
        user.setId(20L);    // user with such id not exist

        ResponseEntity<String> response = template.exchange(
                UserController.userUri,
                HttpMethod.PUT,
                new HttpEntity<>(user),
                String.class
        );

        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status code 404 NOT_FOUND when db doesn't " +
                                 "exists user with id %s, but got %s.", user.getId(), response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(response.getBody())
                .as("Expect response body with details about constraints.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        Object title = context.read("$.body.title");
        Object detail = context.read("$.body.detail");
        assertThat(title)
                .as("Expect constraint title like 'Not Found'")
                .isEqualTo("Not Found");
        assertThat(detail)
                .as("Expect constraint detail like 'User with id 20 not found.'")
                .isEqualTo("User with id " + user.getId() + " not found.");
    }

    @Test
    void updateUser_shouldReturn400_whenIdIsNull() {
        ChangedUser user = ChangedUser.getValidInstance();
        user.setId(null);   // invalidate the request

        ResponseEntity<String> response = template.exchange(
                UserController.userUri,
                HttpMethod.PUT,
                new HttpEntity<>(user),
                String.class
        );

        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status code 400 BAD_REQUEST when we try change " +
                                 "user with id 'null', but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .as("Expect that response body not null.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        String title = context.read("$.body.title");
        String detail = context.read("$.body.detail");
        String idViolationMessage = context.read("$.body.id");

        assertThat(title)
                .as("Expect that response violation title is 'Validation Error'.")
                .isEqualTo("Validation Error");
        assertThat(detail)
                .as("Expect that response violation detail is 'One or more fields have validation errors.'.")
                .isEqualTo("One or more fields have validation errors.");
        assertThat(idViolationMessage)
                .as("Expect that response violation message return properties 'id=User id is required'.")
                .isEqualTo("User id is required");
    }

    @Test
    void updateUser_shouldReturn400_whenChangedUserIsInvalid() {
        ChangedUser user = ChangedUser.getValidInstance();
        user.setNickname("air");   // invalidate nickname

        ResponseEntity<String> response = template.exchange(
                UserController.userUri,
                HttpMethod.PUT,
                new HttpEntity<>(user),
                String.class
        );

        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status code 400 BAD_REQUEST when we try " +
                                 "to change fields with invalid data, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .as("Expect that response body not null.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        String title = context.read("$.body.title");
        String detail = context.read("$.body.detail");
        String nicknameViolationMessage = context.read("$.body.nickname");

        assertThat(title)
                .as("Expect that response violation title is 'Validation Error'.")
                .isEqualTo("Validation Error");
        assertThat(detail)
                .as("Expect that response violation detail is 'One or more fields have validation errors.'.")
                .isEqualTo("One or more fields have validation errors.");
        assertThat(nicknameViolationMessage)
                .as("Expect that response violation message return properties " +
                    "'nickname=Nickname cannot be greater than 50 and less than 4 characters'.")
                .isEqualTo("Nickname cannot be greater than 50 and less than 4 characters");
    }

    @Test
    void updateUser_shouldReturn400_whenChangedUserHasFieldsThatAlreadyExistingInDb() {
        ChangedUser user = ChangedUser.getValidInstance();
        user.setNickname("nickname2");   // such nickname already exists

        ResponseEntity<String> response = template.exchange(
                UserController.userUri,
                HttpMethod.PUT,
                new HttpEntity<>(user),
                String.class
        );

        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status code 400 BAD_REQUEST when we try " +
                                 "to change nickname with already existing nickname, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody())
                .as("Expect that response body not null.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        String title = context.read("$.body.title");
        String detail = context.read("$.body.detail");
        assertThat(title)
                .as("Expect that response violation title is 'Bad Request'.")
                .isEqualTo("Bad Request");
        assertThat(detail)
                .as("Expect that response violation detail is 'Entity with such nickname already exist.'.")
                .isEqualTo("Entity with such nickname already exist.");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteUser_shouldReturn204_whenIsOk() {
        int userID = 1;
        // make sure that entity is exists
        ResponseEntity<String> responseBefore = template.getForEntity(UserController.userUri + "/" + userID, String.class);
        assertThat(responseBefore.getStatusCode())
                .withFailMessage("Expect response status code 200 OK when we want " +
                                 "make sure that entity with id:%d is exists, but got %s.", userID, responseBefore.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        // delete existing entity
        ResponseEntity<Void> deleteResponse = template.exchange(
                UserController.userUri + "/" + userID,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                Void.class
        );
        assertThat(deleteResponse.getStatusCode())
                .withFailMessage("Expect response status code 204 NO_CONTENT when we delete " +
                                 "existing user, but got %s.", deleteResponse.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

        // make sure that entity was deleted
        ResponseEntity<String> responseAfter = template.getForEntity(UserController.userUri + "/" + userID, String.class);
        assertThat(responseAfter.getStatusCode())
                .withFailMessage("Expect response status code 404 NOT_FOUND when we want " +
                                 "make sure that entity with id:%d is not exists after delete, but got %s.", userID, responseAfter.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteUser_shouldReturn404_whenTargetToDeleteHasNoExistingId() {
        int userID = 20;    // no one entity hasn't such id
        ResponseEntity<String> response = template.exchange(
                UserController.userUri + "/" + userID,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                String.class
        );
        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status code 404 NOT_FOUND when we try " +
                                 "delete not existing entity by id, but we got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody())
                .as("Expect response body with violation details is not null.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        String title = context.read("$.body.title");
        String detail = context.read("$.body.detail");
        assertThat(title)
                .as("Expect that response violation title is 'Not Found'.")
                .isEqualTo("Not Found");
        assertThat(detail)
                .as("Expect that response violation detail contains message 'User with id %d not found.'.", userID)
                .isEqualTo("User with id %d not found.", userID);
    }


}
