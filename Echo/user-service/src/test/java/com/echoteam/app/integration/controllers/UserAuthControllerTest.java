package com.echoteam.app.integration.controllers;

import com.echoteam.app.controllers.UserAuthController;
import com.echoteam.app.entities.dto.changedDTO.ChangedUserAuth;
import com.echoteam.app.entities.dto.createdDTO.CreatedAuth;
import com.echoteam.app.entities.dto.nativeDTO.UserAuthDTO;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
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
public class UserAuthControllerTest {
    @Autowired
    TestRestTemplate template;

    @Test
    void getAll_shouldReturn200_whenRequestedWithoutParameters() {
        // when
        ResponseEntity<String> response = template.getForEntity(
                UserAuthController.authUri,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status 200 OK when requested, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .withFailMessage("Expected response body to be non-null, but it is null.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());

        int pageNumber = context.read("$.page.number");
        assertThat(pageNumber)
                .withFailMessage("Expected page number to be 0, but got %d.", pageNumber)
                .isEqualTo(0);

        int pageSize = context.read("$.page.size");
        assertThat(pageSize)
                .withFailMessage("Expected page size to be 10, but got %d.", pageSize)
                .isEqualTo(10);

        int contentSize = context.read("$.content.length()");
        assertThat(contentSize)
                .withFailMessage("Expected content size to be 3, but got %d.", contentSize)
                .isEqualTo(3);

        JSONArray ids = context.read("$.content[*].id");
        assertThat(ids)
                .withFailMessage("Expected IDs to be [1, 2, 3], but got %s.", ids)
                .containsExactly(1, 2, 3);

        JSONArray emails = context.read("$.content[*].email");
        assertThat(emails)
                .withFailMessage("Expected emails to be [\"example1@gmail.com\", \"example2@gmail.com\"," +
                                 " \"example3@gmail.com\"], but got %s.", emails)
                .containsExactly("example1@gmail.com", "example2@gmail.com", "example3@gmail.com");
    }

    @Test
    void getAll_shouldReturn200_whenRequestedWithParameters() {
        // given
        int page = 0;
        int size = 2;
        String sortBy = "email";
        String direction = "desc";

        // when
        ResponseEntity<String> response = template.getForEntity(
                UserAuthController.authUri + "?page=" + page + "&size=" + size +
                "&sortBy=" + sortBy + "&direction=" + direction,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status 200 OK when requested, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .withFailMessage("Expected response body to be non-null, but it is null.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());

        int pageNumber = context.read("$.page.number");
        assertThat(pageNumber)
                .withFailMessage("Expected page number to be %d, but got %d.", page, pageNumber)
                .isEqualTo(page);

        int pageSize = context.read("$.page.size");
        assertThat(pageSize)
                .withFailMessage("Expected page size to be %d, but got %d.", size, pageSize)
                .isEqualTo(size);

        int contentLength = context.read("$.content.length()");
        assertThat(contentLength)
                .withFailMessage("Expected content length to be %d, but got %d.", size, contentLength)
                .isEqualTo(size);

        JSONArray ids = context.read("$.content[*].id");
        assertThat(ids)
                .withFailMessage("Expected IDs to be [3, 2], but got %s.", ids)
                .containsExactly(3, 2);

        JSONArray emails = context.read("$.content[*].email");
        assertThat(emails)
                .withFailMessage("Expected emails to be [\"example3@gmail.com\"," +
                                 " \"example2@gmail.com\"], but got %s.", emails)
                .containsExactly("example3@gmail.com", "example2@gmail.com");
    }

    @Test
    void getAll_shouldReturn200_whenRequestedPageIsNotExist() {
        // given
        int page = 10;          // there is no page 10
        int size = 10;
        String sortBy = "";
        String direction = "";

        // when
        ResponseEntity<String> response = template.getForEntity(
                UserAuthController.authUri + "?page=" + page + "&size=" + size +
                "&sortBy=" + sortBy + "&direction=" + direction,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status 200 OK when requested non-existent page, " +
                                 "but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .withFailMessage("Expected response body to be non-null for a non-existent " +
                                 "page request, but it is null.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());

        int pageNumber = context.read("$.page.number");
        assertThat(pageNumber)
                .withFailMessage("Expected page number to be %d, but got %d.", page, pageNumber)
                .isEqualTo(page);

        int pageSize = context.read("$.page.size");
        assertThat(pageSize)
                .withFailMessage("Expected page size to be %d, but got %d.", size, pageSize)
                .isEqualTo(size);

        int contentLength = context.read("$.content.length()");
        assertThat(contentLength)
                .withFailMessage("Expected content length to be 0 for a non-existent page, " +
                                 "but got %d.", contentLength)
                .isEqualTo(0);
    }

    @Test
    void getById_shouldReturn200_whenItIsOk() {
        // given
        UserAuthDTO userAuthDTO = UserAuthDTO.getValidInstance();

        // when
        ResponseEntity<UserAuthDTO> response = template.getForEntity(
                UserAuthController.authUri + "/" + userAuthDTO.getId(),
                UserAuthDTO.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status 200 OK when it is ok, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .withFailMessage("Expect response body is not null, but it is.")
                .isNotNull();
        assertThat(response.getBody().getId())
                .withFailMessage("Expect response entity with id:%s when request is by id:%s," +
                                 " but id is %s.", userAuthDTO.getId(), userAuthDTO.getId(), response.getBody().getId())
                .isEqualTo(userAuthDTO.getId());
    }

    @Test
    void getById_shouldReturn404_whenThereNoEntityWithSuchId() {
        // given
        UserAuthDTO userAuthDTO = UserAuthDTO.getValidInstance();
        userAuthDTO.setId(20L);

        // when
        ResponseEntity<String> response = template.getForEntity(
                UserAuthController.authUri + "/" + userAuthDTO.getId(),
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status 404 NOT_FOUND when there is no element with such id," +
                                 " but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody())
                .withFailMessage("Expect response body is not null, but it is.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        String errorTitle = context.read("$.body.title");
        String expectedTitle = "Not Found";
        assertThat(errorTitle)
                .withFailMessage("Expect response error title is %s, but got %s.", expectedTitle, errorTitle)
                .isEqualTo(expectedTitle);

        String errorDetail = context.read("$.body.detail");
        String expectedDetail = String.format("Auth with id %s not found.", userAuthDTO.getId());
        assertThat(errorDetail)
                .withFailMessage("Expect response error detail is %s, but got %s.", expectedDetail, errorDetail)
                .isEqualTo(expectedDetail);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createAuth_shouldReturn200_whenItIsValid() {
        // given
        CreatedAuth createdAuth = CreatedAuth.getValidInstance();
        createdAuth.setUserId(4L);

        // when
        ResponseEntity<Void> createdResponse = template.postForEntity(
                UserAuthController.authUri,
                createdAuth,
                Void.class
        );

        // then
        assertThat(createdResponse.getStatusCode())
                .withFailMessage("Expect createdResponse status 201 CREATED when created auth " +
                                 "is valid, but got %s.", createdResponse.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        assertThat(createdResponse.getHeaders().getLocation())
                .withFailMessage("Expect that response provide location for created entity, but it is null.")
                .isNotNull();

        // try to get entity by location
        ResponseEntity<UserAuthDTO> getResponse = template.getForEntity(
                createdResponse.getHeaders().getLocation(),
                UserAuthDTO.class
        );
        assertThat(getResponse.getStatusCode())
                .withFailMessage("Expected getResponse status 200 OK when auth was posted, " +
                                 "but got %s.", getResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody())
                .withFailMessage("Expect getResponse body is not null, but it is.")
                .isNotNull();
        UserAuthDTO getAuth = getResponse.getBody();

        // check entity for fields matching
        assertThat(getAuth.getId()).isNotNull();
        assertThat(getAuth.getUserId()).isEqualTo(createdAuth.getUserId());
        assertThat(getAuth.getEmail()).isEqualTo(createdAuth.getEmail());
        assertThat(getAuth.getPassword()).isEqualTo(createdAuth.getPassword());
        assertThat(getAuth.getCreated()).isNotNull();
        assertThat(getAuth.getIsDeleted()).isFalse();

        assertThat(getAuth.getChanged()).isNull();
    }

    @Test
    void createdAuth_shouldReturn400_whenUserWithSuchIdAlreadyHasAuth() {
        // given
        CreatedAuth createdAuth = CreatedAuth.getValidInstance();
        createdAuth.setUserId(1L);      // user with such id already has auth

        // when
        ResponseEntity<String> response = template.postForEntity(
                UserAuthController.authUri,
                createdAuth,
                String.class);

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status 400 BAD_REQUEST when try ro create auth " +
                                 "to user with already existing auth, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        DocumentContext context = JsonPath.parse(response.getBody());
        String errorTitle = context.read("$.body.title");
        String expectTitle = "Bad Request";
        assertThat(errorTitle)
                .withFailMessage("Expect response errorTitle like %s, but got %s.", expectTitle, errorTitle)
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "Entity with such user already exist.";
        assertThat(errorDetail)
                .withFailMessage("Expect response errorDetail like %s, but got %s.", expectDetail, errorDetail)
                .isEqualTo(expectDetail);
    }

    @Test
    void createAuth_shouldReturn400_whenAuthIsInvalid() {
        // given
        CreatedAuth createdAuth = CreatedAuth.getValidInstance();
        createdAuth.setUserId(null);        // invalidate createdUser

        // when
        ResponseEntity<String> response = template.postForEntity(
                UserAuthController.authUri,
                createdAuth,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status 400 BAD_REQUEST when user is invalid, " +
                                 "but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        DocumentContext context = JsonPath.parse(response.getBody());
        String errorTitle = context.read("$.body.title");
        String expectTitle = "Validation Error";
        assertThat(errorTitle)
                .withFailMessage("Expect errorTitle like %s, but it is %s.", expectTitle, errorTitle)
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "One or more fields have validation errors.";
        assertThat(errorDetail)
                .withFailMessage("Expect errorDetail like %s, but it is %s.", expectDetail, errorDetail)
                .isEqualTo(expectDetail);

        String errorFieldMessage = context.read("$.body.userId");
        String expectFieldMessage = "User id is required";
        assertThat(errorFieldMessage)
                .withFailMessage("Expect errorFieldMessage:userId like %s, " +
                                 "but got %s.", expectFieldMessage, errorFieldMessage)
                .isEqualTo(expectFieldMessage);
    }

    @Test
    void createAuth_shouldReturn400_whenTryToCreateAuthForNoExistingUser() {
        // given
        CreatedAuth createdAuth = CreatedAuth.getValidInstance();
        createdAuth.setUserId(1L); // user with id 1 already has auth data

        // when
        ResponseEntity<String> response = template.postForEntity(
                UserAuthController.authUri,
                createdAuth,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected status 400 BAD_REQUEST when trying to create auth for an existing user, but got %s.",
                        response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        DocumentContext context = JsonPath.parse(response.getBody());

        String errorTitle = context.read("$.body.title");
        String expectTitle = "Bad Request";
        assertThat(errorTitle)
                .withFailMessage("Expected error title to be '%s', but got '%s'.", expectTitle, errorTitle)
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "Entity with such user already exist.";
        assertThat(errorDetail)
                .withFailMessage("Expected error detail to be '%s', but got '%s'.", expectDetail, errorDetail)
                .isEqualTo(expectDetail);
    }

    @Test
    void createAuth_shouldReturn400_whenAuthHasFieldsWithAlreadyExistingDataInDB() {
        // given
        CreatedAuth createdAuth = CreatedAuth.getValidInstance();
        createdAuth.setUserId(4L); // set another user
        createdAuth.setEmail("example1@gmail.com"); // someone already has such email

        // when
        ResponseEntity<String> response = template.postForEntity(
                UserAuthController.authUri,
                createdAuth,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected status 400 BAD_REQUEST when auth data contains already " +
                                 "existing fields in DB, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        DocumentContext context = JsonPath.parse(response.getBody());

        String errorTitle = context.read("$.body.title");
        String expectTitle = "Bad Request";
        assertThat(errorTitle)
                .withFailMessage("Expected error title to be '%s', but got '%s'.", expectTitle, errorTitle)
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "Entity with such email already exist.";
        assertThat(errorDetail)
                .withFailMessage("Expected error detail to be '%s', but got '%s'.", expectDetail, errorDetail)
                .isEqualTo(expectDetail);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateAuth_shouldReturn200_whenIsValid() {
        // given
        ChangedUserAuth changedUserAuth = ChangedUserAuth.getValidInstance();

        ResponseEntity<UserAuthDTO> beforeResponse = template.getForEntity(
                UserAuthController.authUri + "/" + changedUserAuth.getId(),
                UserAuthDTO.class
        );
        assertThat(beforeResponse.getStatusCode())
                .withFailMessage("Expected status code 200 (OK) for initial GET request, " +
                                 "but got %s", beforeResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        UserAuthDTO before = beforeResponse.getBody();

        // when
        ResponseEntity<Void> putResponse = template.exchange(
                UserAuthController.authUri,
                HttpMethod.PUT,
                new HttpEntity<>(changedUserAuth),
                Void.class
        );
        assertThat(putResponse.getStatusCode())
                .withFailMessage("Expected status code 200 (OK) after PUT request, " +
                                 "but got %s", putResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(putResponse.getHeaders().getLocation())
                .withFailMessage("Expected non-null 'Location' header in PUT response, " +
                                 "but got null")
                .isNotNull();
        URI location = putResponse.getHeaders().getLocation();

        // then
        ResponseEntity<UserAuthDTO> afterResponse = template.getForEntity(location, UserAuthDTO.class);
        assertThat(afterResponse.getStatusCode())
                .withFailMessage("Expected status code 200 (OK) for GET request after update, " +
                                 "but got %s", afterResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        UserAuthDTO after = afterResponse.getBody();

        assertThat(after.getId())
                .withFailMessage("Expected ID to remain unchanged after update, but " +
                                 "got different ID: %s", after.getId())
                .isEqualTo(before.getId());

        assertThat(after.getUserId())
                .withFailMessage("Expected UserId to remain unchanged after update, " +
                                 "but got different UserId: %s", after.getUserId())
                .isEqualTo(before.getUserId());

        assertThat(after.getEmail())
                .withFailMessage("Expected email to be updated to %s, but got %s",
                        changedUserAuth.getEmail(), after.getEmail())
                .isEqualTo(changedUserAuth.getEmail());

        assertThat(after.getPassword())
                .withFailMessage("Expected password to remain unchanged after update, but got " +
                                 "different password: %s", after.getPassword())
                .isEqualTo(before.getPassword());

        assertThat(after.getCreated())
                .withFailMessage("Expected 'created' timestamp to remain unchanged, but got " +
                                 "different timestamp: %s", after.getCreated())
                .isEqualTo(before.getCreated());

        assertThat(after.getChanged())
                .withFailMessage("Expected 'changed' timestamp to be updated, but it remains " +
                                 "the same as before: %s", after.getChanged())
                .isNotEqualTo(before.getChanged());

        assertThat(after.getIsDeleted())
                .withFailMessage("Expected 'isDeleted' to be false, but got true")
                .isFalse();
    }

    @Test
    void updateAuth_shouldReturn400_whenIsInvalid() {
        // given
        ChangedUserAuth changedUserAuth = ChangedUserAuth.getValidInstance();
        changedUserAuth.setEmail("");  // invalidate changed entity

        // when
        ResponseEntity<String> response = template.exchange(
                UserAuthController.authUri,
                HttpMethod.PUT,
                new HttpEntity<>(changedUserAuth),
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected status code 400 (BAD_REQUEST) due to invalid email, " +
                                 "but got %s", response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        DocumentContext context = JsonPath.parse(response.getBody());

        String errorTitle = context.read("$.body.title");
        String expectTitle = "Validation Error";
        assertThat(errorTitle)
                .withFailMessage("Expected error title to be '%s', but got '%s'", expectTitle, errorTitle)
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "One or more fields have validation errors.";
        assertThat(errorDetail)
                .withFailMessage("Expected error detail to be '%s', but got '%s'", expectDetail, errorDetail)
                .isEqualTo(expectDetail);

        String errorFieldMessage = context.read("$.body.email");
        String expectFieldMessage = "Email address is required";
        assertThat(errorFieldMessage)
                .withFailMessage("Expected error message for 'email' field to be '%s', " +
                                 "but got '%s'", expectFieldMessage, errorFieldMessage)
                .isEqualTo(expectFieldMessage);
    }

    @Test
    void updateAuth_shouldReturn404_whenUserAuthIsNotExists() {
        // given
        ChangedUserAuth changedUserAuth = ChangedUserAuth.getValidInstance();
        changedUserAuth.setId(20L);  // such auth does not exist

        // when
        ResponseEntity<String> response = template.exchange(
                UserAuthController.authUri,
                HttpMethod.PUT,
                new HttpEntity<>(changedUserAuth),
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected status code 404 (NOT_FOUND) when Auth entity does not exist, " +
                                 "but got %s", response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        DocumentContext context = JsonPath.parse(response.getBody());
        String errorTitle = context.read("$.body.title");
        String expectTitle = "Not Found";
        assertThat(errorTitle)
                .withFailMessage("Expected error title to be '%s', but got '%s'", expectTitle, errorTitle)
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "Auth with id %d not found.".formatted(changedUserAuth.getId());
        assertThat(errorDetail)
                .withFailMessage("Expected error detail to be '%s', but got '%s'", expectDetail, errorDetail)
                .isEqualTo(expectDetail);
    }

    @Test
    void updateAuth_shouldReturn400_whenAuthHasTheSameDataThatAlreadyHasInDB() {
        // given
        ChangedUserAuth changedUserAuth = ChangedUserAuth.getValidInstance();
        changedUserAuth.setEmail("example2@gmail.com");         // someone already has such email

        // when
        ResponseEntity<String> response = template.exchange(
                UserAuthController.authUri,
                HttpMethod.PUT,
                new HttpEntity<>(changedUserAuth),
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected status code 400 (BAD_REQUEST) when changed entity has already " +
                                 "existing data, but got %s", response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        DocumentContext context = JsonPath.parse(response.getBody());
        String errorTitle = context.read("$.body.title");
        String expectTitle = "Bad Request";
        assertThat(errorTitle)
                .withFailMessage("Expected error title to be '%s', but got '%s'", expectTitle, errorTitle)
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "Entity with such email already exist.";
        assertThat(errorDetail)
                .withFailMessage("Expected error detail to be '%s', but got '%s'", expectDetail, errorDetail)
                .isEqualTo(expectDetail);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteAuth_shouldReturn204_whenItIsOk() {
        // given
        int authId = 1;

        ResponseEntity<String> beforeDelete = template.getForEntity(
                UserAuthController.authUri + "/" + authId,
                String.class
        );
        assertThat(beforeDelete.getStatusCode())
                .withFailMessage("Expected status 200 (OK) for existing Auth entity with " +
                                 "id %d, but got %s", authId, beforeDelete.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        // when
        ResponseEntity<Void> deleteResponse = template.exchange(
                UserAuthController.authUri + "/" + authId,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                Void.class
        );

        assertThat(deleteResponse.getStatusCode())
                .withFailMessage("Expected status 204 (NO_CONTENT) after deleting Auth " +
                                 "entity with id %d, but got %s", authId, deleteResponse.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

        // then
        ResponseEntity<String> afterDelete = template.getForEntity(
                UserAuthController.authUri + "/" + authId,
                String.class
        );
        assertThat(afterDelete.getStatusCode())
                .withFailMessage("Expected status 404 (NOT_FOUND) for Auth entity with id %d " +
                                 "after deletion, but got %s", authId, afterDelete.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteAuth_shouldReturn404_whenAuthWithSuchIdNotExisting() {
        // given
        int authId = 20;         // auth with such id is not exists

        ResponseEntity<String> getResponse = template.getForEntity(
                UserAuthController.authUri + "/" + authId,
                String.class
        );
        assertThat(getResponse.getStatusCode())
                .withFailMessage("Expected status 404 NOT_FOUND when auth does not exist with id %d, " +
                                 "but got %s.", authId, getResponse.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        // when
        ResponseEntity<String> deleteResponse = template.exchange(
                UserAuthController.authUri + "/" + authId,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                String.class
        );

        //then
        assertThat(deleteResponse.getStatusCode())
                .withFailMessage("Expected status 404 NOT_FOUND when attempting to delete " +
                                 "non-existing entity with id %d, but got %s.", authId, deleteResponse.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(deleteResponse.getBody())
                .withFailMessage("Expected non-null response body for 404 error, " +
                                 "but got %s.", deleteResponse.getBody())
                .isNotNull();

        DocumentContext context = JsonPath.parse(deleteResponse.getBody());
        String errorTitle = context.read("$.body.title");
        String expectedTitle = "Not Found";
        assertThat(errorTitle)
                .withFailMessage("Expected error title '%s', but got '%s'.", expectedTitle, errorTitle)
                .isEqualTo(expectedTitle);

        String errorDetail = context.read("$.body.detail");
        String expectedDetail = "Auth with id %s not found.".formatted(authId);
        assertThat(errorDetail)
                .withFailMessage("Expected error detail '%s', but got '%s'.", expectedDetail, errorDetail)
                .isEqualTo(expectedDetail);
    }

}








