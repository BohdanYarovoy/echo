package com.echoteam.app.integration.controllers;

import com.echoteam.app.controllers.UserRoleController;
import com.echoteam.app.entities.dto.createdDTO.CreatedRole;
import com.echoteam.app.entities.dto.nativeDTO.UserRoleDTO;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
    void getAllUserRoles_shouldReturn200_whenRequested() {
        // when
        ResponseEntity<UserRoleDTO[]> response = template.getForEntity(UserRoleController.roleUri, UserRoleDTO[].class);

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expected response status 200 OK, but received: %s", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .withFailMessage("Response body is null, expected an array of UserRoleDTO.")
                .isNotNull();

        assertThat(response.getBody())
                .withFailMessage("Expected array size of roles: 3, but received: %d", response.getBody().length)
                .hasSize(3);
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

    // todo: finalize creating test methods for RoleController
}












