package com.echoteam.app.integration.controllers;

import com.echoteam.app.controllers.UserRoleController;
import com.echoteam.app.entities.dto.changedDTO.ChangedRole;
import com.echoteam.app.entities.dto.createdDTO.CreatedRole;
import com.echoteam.app.entities.dto.nativeDTO.UserRoleDTO;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRoleControllerTest {
    @Autowired
    TestRestTemplate template;

    @Test
    void getAllUserRoles_shouldReturn200_whenRequestedWithoutParameters() {
        // when
        ResponseEntity<String> response = template.getForEntity(
                UserRoleController.roleUri,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status 200 OK, but received: %s", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .withFailMessage("Response body is null, expected an array of UserRoleDTO.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());

        int pageNumber = context.read("$.page.number");
        assertThat(pageNumber)
                .withFailMessage("Expected page number to be 0, but got: %d", pageNumber)
                .isEqualTo(0);

        int pageSize = context.read("$.page.size");
        assertThat(pageSize)
                .withFailMessage("Expected page size to be 10, but got: %d", pageSize)
                .isEqualTo(10);

        int contentLength = context.read("$.content.length()");
        assertThat(contentLength)
                .withFailMessage("Expected content length to be 3, but got: %d", contentLength)
                .isEqualTo(3);

        JSONArray ids = context.read("$.content[*].id");
        assertThat(ids)
                .withFailMessage("Expected IDs [1, 2, 3], but got: %s", ids)
                .containsExactly(1, 2, 3);

        JSONArray roleNames = context.read("$.content[*].name");
        assertThat(roleNames)
                .withFailMessage("Expected role names [\"USER\", \"ADMIN\", \"MODERATOR\"], but got: %s", roleNames)
                .containsExactly("USER", "ADMIN", "MODERATOR");
    }

    @Test
    void getAllUserRoles_shouldReturn200_whenRequestedWithParameters() {
        // given
        int page = 0;
        int size = 2;
        String sortBy = "name";
        String direction = "desc";

        // when
        ResponseEntity<String> response = template.getForEntity(
                UserRoleController.roleUri + "?page=" + page + "&size=" + size + "&sortBy=" + sortBy + "&direction=" + direction,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status 200 OK, but received: %s", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .withFailMessage("Response body is null, expected JSON with pagination details and content array.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        int pageNumber = context.read("$.page.number");
        assertThat(pageNumber)
                .withFailMessage("Expected page number to be %d, but received: %d", page, pageNumber)
                .isEqualTo(page);

        int pageSize = context.read("$.page.size");
        assertThat(pageSize)
                .withFailMessage("Expected page size to be %d, but received: %d", size, pageSize)
                .isEqualTo(size);

        int contentLength = context.read("$.content.length()");
        assertThat(contentLength)
                .withFailMessage("Expected content length to be %d, but received: %d", 2, contentLength)
                .isEqualTo(size);

        JSONArray ids = context.read("$.content[*].id");
        assertThat(ids)
                .withFailMessage("Expected content IDs to be [1, 3], but received: %s", ids)
                .containsExactly(1, 3);

        JSONArray roleNames = context.read("$.content[*].name");
        assertThat(roleNames)
                .withFailMessage("Expected role names to be ['USER', 'MODERATOR'], but received: %s", roleNames)
                .containsExactly("USER", "MODERATOR");
    }

    @Test
    void getAllUserRoles_shouldReturn200_whenRequestedNoExistingPage() {
        // given
        int page = 10;          // there no 10 page
        int size = 10;
        String sortBy = "";
        String direction = "";

        // when
        ResponseEntity<String> response = template.getForEntity(
                UserRoleController.roleUri + "?page=" + page + "&size=" + size + "&sortBy=" + sortBy + "&direction=" + direction,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status 200 OK, but received: %s", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .withFailMessage("Response body is null, expected JSON with pagination details and content array.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        int pageNumber = context.read("$.page.number");
        assertThat(pageNumber)
                .withFailMessage("Expected page number to be %d, but received: %d", page, pageNumber)
                .isEqualTo(page);

        int pageSize = context.read("$.page.size");
        assertThat(pageSize)
                .withFailMessage("Expected page size to be %d, but received: %d", size, pageSize)
                .isEqualTo(size);

        int contentLength = context.read("$.content.length()");
        assertThat(contentLength)
                .withFailMessage("Expected content length to be %d, but received: %d", 2, contentLength)
                .isEqualTo(0);
    }

    @Test
    void getUserRoleByID_shouldReturn200_whenIsOk() {
        // given
        int roleId = 1;

        // when
        ResponseEntity<UserRoleDTO> response = template.getForEntity(
                UserRoleController.roleUri + "/" + roleId,
                UserRoleDTO.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status 200 OK, but received: %s", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        UserRoleDTO role = response.getBody();
        assertThat(role)
                .withFailMessage("Response body is null, expected a UserRoleDTO object.")
                .isNotNull();

        assertThat(role.getId().intValue())
                .withFailMessage("Expected role ID: %d, but received: %d", roleId, role.getId().intValue())
                .isEqualTo(roleId);

        assertThat(role.getName())
                .withFailMessage("Expected role name: 'USER', but received: '%s'", role.getName())
                .isEqualTo("USER");
    }

    @Test
    void getUserRoleByID_shouldReturn404_whenRoleWithSuchIdIsNotExists() {
        // given
        int roleId = 20;  // role with such id does not exist

        // when
        ResponseEntity<String> response = template.getForEntity(
                UserRoleController.roleUri + "/" + roleId,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status 404 NOT_FOUND, " +
                                 "but received: %s", response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(response.getBody())
                .withFailMessage("Response body is null, expected an error JSON message.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        String errorTitle = context.read("$.body.title");
        String expectTitle = "Not Found";
        assertThat(errorTitle)
                .withFailMessage("Expected error title: '%s', but received: '%s'", expectTitle, errorTitle)
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "Role with id %s not found.".formatted(roleId);
        assertThat(errorDetail)
                .withFailMessage("Expected error detail message: '%s', but " +
                                 "received: '%s'", expectDetail, errorDetail)
                .isEqualTo(expectDetail);
    }

    @Test
    void getUserRoleByIds_shouldReturn200_whenItIsOk() {
        // given
        List<Short> ids = List.of(
                (short) 1,
                (short) 2,
                (short) 5 // role with id 5 does not exist
        );
        String requestUrl = UriComponentsBuilder.fromUriString(UserRoleController.roleUri + "/selectively")
                .queryParam("ids", ids)
                .toUriString();

        // when
        ResponseEntity<UserRoleDTO[]> response = template.getForEntity(
                requestUrl,
                UserRoleDTO[].class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status 200 OK, but received: %s", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .withFailMessage("Response body is null, expected an array of UserRoleDTO.")
                .isNotNull();

        assertThat(response.getBody())
                .withFailMessage("Expected array size: 2, but received: %d", response.getBody().length)
                .hasSize(2);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createUserRole_shouldReturn200_whenItIsValid() {
        // given
        CreatedRole createdRole = CreatedRole.getValidInstance();

        // when
        ResponseEntity<Void> response = template.postForEntity(
                UserRoleController.roleUri,
                createdRole,
                Void.class
        );
        URI location = response.getHeaders().getLocation();

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status 201 CREATED, but received: %s", response.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        assertThat(location)
                .withFailMessage("Expected a non-null location in response headers, but received: %s", location)
                .isNotNull();

        // try to use location
        ResponseEntity<UserRoleDTO> afterResponse = template.getForEntity(location, UserRoleDTO.class);
        assertThat(afterResponse.getStatusCode())
                .withFailMessage("Expected response status 200 OK for location: %s, " +
                                 "but received: %s", location, afterResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(afterResponse.getBody())
                .withFailMessage("Expected a non-null UserRoleDTO in the response body, " +
                                 "but received: %s", afterResponse.getBody())
                .isNotNull();

        UserRoleDTO role = afterResponse.getBody();
        assertThat(role.getId())
                .withFailMessage("Expected a non-null ID for the created role, but received: %s", role.getId())
                .isNotNull();

        assertThat(role.getName())
                .withFailMessage("Expected role name: '%s', but received: '%s'", createdRole.getName(), role.getName())
                .isEqualTo(createdRole.getName());
    }

    @Test
    void createUserRole_shouldReturn400_whenItIsInvalid() {
        // given
        CreatedRole createdRole = CreatedRole.getValidInstance();
        createdRole.setName("in_lover_case");        // invalidate entity

        // when
        ResponseEntity<String> response = template.postForEntity(
                UserRoleController.roleUri,
                createdRole,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .as("Expected HTTP status 400 for invalid role name.")
                .isEqualTo(HttpStatus.BAD_REQUEST);

        DocumentContext context = JsonPath.parse(response.getBody());

        String errorTitle = context.read("$.body.title");
        String expectTitle = "Validation Error";
        assertThat(errorTitle)
                .as("Expected error title to be '%s' but was '%s'", expectTitle, errorTitle)
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "One or more fields have validation errors.";
        assertThat(errorDetail)
                .as("Expected error detail message to be '%s' but was '%s'", expectDetail, errorDetail)
                .isEqualTo(expectDetail);

        String errorFieldMessage = context.read("$.body.name");
        String expectFieldMessage = "Role name must be upper case";
        assertThat(errorFieldMessage)
                .as("Expected field error message to be '%s' but was '%s'", expectFieldMessage, errorFieldMessage)
                .isEqualTo(expectFieldMessage);
    }

    @Test
    void createUserRole_shouldReturn400_whenWeTryToCreateUserRoleWithDataThatAlreadyExistingInDB() {
        // given
        CreatedRole createdRole = CreatedRole.getValidInstance();
        createdRole.setName("USER");            // role with such name already exists

        // when
        ResponseEntity<String> response = template.postForEntity(
                UserRoleController.roleUri,
                createdRole,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .as("Expected HTTP status 400 for creating a duplicate role.")
                .isEqualTo(HttpStatus.BAD_REQUEST);

        DocumentContext context = JsonPath.parse(response.getBody());

        String errorTitle = context.read("$.body.title");
        String expectTitle = "Bad Request";
        assertThat(errorTitle)
                .as("Expected error title to be '%s' but was '%s'", expectTitle, errorTitle)
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "Entity with such name already exist.";
        assertThat(errorDetail)
                .as("Expected error detail message to be '%s' but was '%s'", expectDetail, errorDetail)
                .isEqualTo(expectDetail);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateUserRole_shouldReturn200_whenIsValid() {
        // given
        ChangedRole changedRole = ChangedRole.getValidInstance();

        ResponseEntity<UserRoleDTO> before = template.getForEntity(
                UserRoleController.roleUri + "/" + changedRole.getId(),
                UserRoleDTO.class
        );

        assertThat(before.getStatusCode())
                .as("Expected initial GET request to return status 200 for existing role.")
                .isEqualTo(HttpStatus.OK);

        assertThat(before.getBody())
                .as("Expected role data to be present in the initial GET response.")
                .isNotNull();

        UserRoleDTO roleBefore = before.getBody();

        // when
        ResponseEntity<Void> putResponse = template.exchange(
                UserRoleController.roleUri,
                HttpMethod.PUT,
                new HttpEntity<>(changedRole),
                Void.class
        );

        // then
        assertThat(putResponse.getStatusCode())
                .as("Expected PUT request to return status 200 when updating valid role.")
                .isEqualTo(HttpStatus.OK);

        assertThat(putResponse.getHeaders().getLocation())
                .as("Expected PUT response to include a non-null Location header.")
                .isNotNull();

        URI location = putResponse.getHeaders().getLocation();
        ResponseEntity<UserRoleDTO> after = template.getForEntity(
                location,
                UserRoleDTO.class
        );

        assertThat(after.getStatusCode())
                .as("Expected GET request to new Location URI to return status 200.")
                .isEqualTo(HttpStatus.OK);

        assertThat(after.getBody())
                .as("Expected updated role data to be present at new Location URI.")
                .isNotNull();

        UserRoleDTO roleAfter = after.getBody();

        assertThat(roleAfter.getId())
                .as("Expected the ID of the updated role to remain unchanged.")
                .isEqualTo(roleBefore.getId());

        assertThat(roleAfter.getName())
                .as("Expected the name of the updated role to match the new name in 'changedRole'.")
                .isEqualTo(changedRole.getName());
    }

    @Test
    void updateUserRole_shouldReturn400_whenItIsNotValid() {
        // given
        ChangedRole changedRole = ChangedRole.getValidInstance();
        changedRole.setName("in_lover_case");  // робимо роль невалідною

        // when
        ResponseEntity<String> response = template.exchange(
                UserRoleController.roleUri,
                HttpMethod.PUT,
                new HttpEntity<>(changedRole),
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .as("Expected status code to be 400 when role data is invalid.")
                .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(response.getBody())
                .as("Expected non-null body in response when request is invalid.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        String errorTitle = context.read("$.body.title");
        String expectTitle = "Validation Error";
        assertThat(errorTitle)
                .as("Expected error title to be 'Validation Error' when there are validation issues.")
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "One or more fields have validation errors.";
        assertThat(errorDetail)
                .as("Expected error detail message to indicate validation issues in request body.")
                .isEqualTo(expectDetail);

        String errorFieldMessage = context.read("$.body.name");
        String expectFieldMessage = "Role name must be upper case";
        assertThat(errorFieldMessage)
                .as("Expected error message for 'name' field to indicate it must be upper case.")
                .isEqualTo(expectFieldMessage);
    }

    @Test
    void updateUserRole_shouldReturn400_whenChangedRoleHasDataThatAlreadyExitingInDB() {
        // given
        ChangedRole changedRole = ChangedRole.getValidInstance();
        changedRole.setId((short)3);
        changedRole.setName("USER");  // a role with that name already exists in the DB

        // when
        ResponseEntity<String> response = template.exchange(
                UserRoleController.roleUri,
                HttpMethod.PUT,
                new HttpEntity<>(changedRole),
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .as("Expected status code 400 when updating role with a name that already exists.")
                .isEqualTo(HttpStatus.BAD_REQUEST);

        assertThat(response.getBody())
                .as("Expected non-null response body when there is a conflict due to existing role name.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        String errorTitle = context.read("$.body.title");
        String expectTitle = "Bad Request";
        assertThat(errorTitle)
                .as("Expected error title to be 'Bad Request' when role name already exists.")
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "Entity with such name already exist.";
        assertThat(errorDetail)
                .as("Expected error detail message to indicate name conflict in the database.")
                .isEqualTo(expectDetail);
    }

    @Test
    void updateUserRole_shouldReturn404_whenRoleThatIsTargetForChangeIsNotExists() {
        // given
        ChangedRole changedRole = ChangedRole.getValidInstance();
        changedRole.setId((short)20);  // a role with such id is not exists in DB

        // when
        ResponseEntity<String> response = template.exchange(
                UserRoleController.roleUri,
                HttpMethod.PUT,
                new HttpEntity<>(changedRole),
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .as("Expected status code 404 when trying to update a non-existent role.")
                .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(response.getBody())
                .as("Expected non-null response body when target role is not found.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(response.getBody());
        String errorTitle = context.read("$.body.title");
        String expectTitle = "Not Found";
        assertThat(errorTitle)
                .as("Expected error title to be 'Not Found' when role does not exist.")
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "Role with id %d not found.".formatted(changedRole.getId());
        assertThat(errorDetail)
                .as("Expected error detail message to mention the missing role ID.")
                .isEqualTo(expectDetail);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteById_shouldReturn204_whenItIsValid() {
        // given
        short roleId = 2;

        ResponseEntity<UserRoleDTO> responseBefore = template.getForEntity(
                UserRoleController.roleUri + "/" + roleId,
                UserRoleDTO.class
        );
        assertThat(responseBefore.getStatusCode())
                .as("Expected status code 200 for a valid role before deletion.")
                .isEqualTo(HttpStatus.OK);

        // when
        ResponseEntity<Void> deleteResponse = template.exchange(
                UserRoleController.roleUri + "/" + roleId,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                Void.class
        );

        // then
        assertThat(deleteResponse.getStatusCode())
                .as("Expected status code 204 after successful deletion.")
                .isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<String> responseAfter = template.getForEntity(
                UserRoleController.roleUri + "/" + roleId,
                String.class
        );
        assertThat(responseAfter.getStatusCode())
                .as("Expected status code 404 when trying to fetch a deleted role.")
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteById_shouldReturn404_whenItIsNotFound() {
        // given
        short roleId = 20;          // there is no role with such id

        ResponseEntity<String> getResponse = template.getForEntity(
                UserRoleController.roleUri + "/" + roleId,
                String.class
        );
        assertThat(getResponse.getStatusCode())
                .as("Expected status code 404 when trying to fetch a non-existing role.")
                .isEqualTo(HttpStatus.NOT_FOUND);

        // when
        ResponseEntity<String> deleteResponse = template.exchange(
                UserRoleController.roleUri + "/" + roleId,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                String.class
        );

        // then
        assertThat(deleteResponse.getStatusCode())
                .as("Expected status code 404 when trying to delete a non-existing role.")
                .isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(deleteResponse.getBody())
                .as("Expected response body to be not null for a not found error.")
                .isNotNull();

        DocumentContext context = JsonPath.parse(deleteResponse.getBody());
        String errorTitle = context.read("$.body.title");
        String expectTitle = "Not Found";
        assertThat(errorTitle)
                .as("Expected error title to be 'Not Found'.")
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "Role with id %d not found.".formatted(roleId);
        assertThat(errorDetail)
                .as("Expected error detail to indicate that the role with the specified id is not found.")
                .isEqualTo(expectDetail);
    }

}