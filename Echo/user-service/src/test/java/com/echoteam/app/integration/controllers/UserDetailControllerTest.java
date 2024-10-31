package com.echoteam.app.integration.controllers;

import com.echoteam.app.controllers.UserDetailController;
import com.echoteam.app.entities.Sex;
import com.echoteam.app.entities.dto.changedDTO.ChangedUserDetail;
import com.echoteam.app.entities.dto.createdDTO.CreatedDetail;
import com.echoteam.app.entities.dto.nativeDTO.UserDetailDTO;
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
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserDetailControllerTest {
    @Autowired
    private TestRestTemplate template;

    @Test
    void getAll_shouldReturn200_whenIsRequested() {
        ResponseEntity<UserDetailDTO[]> response = template.getForEntity(UserDetailController.detailUri, UserDetailDTO[].class);

        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status code 200 OK when it is " +
                                 "requested, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .as("Expect that response has body with UserDetailDTO[].")
                .isNotNull();

        UserDetailDTO[] body = response.getBody();
        assertThat(body)
                .as("Expect that body has array of UserDetailDTO with length 3 because db has only 3 entity.")
                .hasSize(3);
        assertThat(body[0])
                .as("Expect that we retrieve UserDetailDTO.class object.")
                .isInstanceOf(UserDetailDTO.class);

        // make sure that any fields was retrieved correct
        assertThat(body[0].getFirstname()).isEqualTo("firstname1");
        assertThat(body[0].getLastname()).isEqualTo("lastname1");
        assertThat(body[0].getPatronymic()).isEqualTo("patronymic1");
        assertThat(body[0].getPhone()).isEqualTo("0971234567");
        assertThat(body[0].getAbout()).isEqualTo("Some details about user.");
        assertThat(body[0].getSex())
                .as("Expect that UserDetailDTO that was retrieved from db has male sex.")
                .isEqualTo(Sex.MALE);
        assertThat(body[0].getDateOfBirth())
                .as("Expected dateOfBirth is 1990-01-01.")
                .isEqualTo(LocalDate.of(1990,1,1));
        assertThat(body[0].getIsDeleted())
                .as("Expected that field isDeleted is false.")
                .isFalse();
        assertThat(body[0].getCreated())
                .as("Expect that UserDetailDTO`s field 'created' has value.")
                .isNotNull();

        assertThat(body[0].getChanged())
                .as("Expect that field 'changed' hasn't any value, because it hasn't never changed.")
                .isNull();
    }

    @Test
    void getById_shouldReturn200_whenItIsOk() {
        int userID = 1;
        ResponseEntity<UserDetailDTO> response = template.getForEntity(
                UserDetailController.detailUri + "/" + userID,
                UserDetailDTO.class
        );
        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status code 200 OK when " +
                                 "requested, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .as("Expect that response has body.")
                .isNotNull();

        UserDetailDTO detail = response.getBody();
        assertThat(detail.getId().intValue()).isEqualTo(userID);
        assertThat(detail.getUserId().intValue()).isEqualTo(1);
        assertThat(detail.getFirstname()).isEqualTo("firstname1");
        assertThat(detail.getLastname()).isEqualTo("lastname1");
        assertThat(detail.getPatronymic()).isEqualTo("patronymic1");
        assertThat(detail.getPhone()).isEqualTo("0971234567");
        assertThat(detail.getAbout()).isEqualTo("Some details about user.");
        assertThat(detail.getSex())
                .as("Expect that UserDetailDTO that was retrieved from db has male sex.")
                .isEqualTo(Sex.MALE);
        assertThat(detail.getDateOfBirth())
                .as("Expected dateOfBirth is 1990-01-01.")
                .isEqualTo(LocalDate.of(1990,1,1));
        assertThat(detail.getIsDeleted())
                .as("Expected that field isDeleted is false.")
                .isFalse();
        assertThat(detail.getCreated())
                .as("Expect that UserDetailDTO`s field 'created' has value.")
                .isNotNull();

        assertThat(detail.getChanged())
                .as("Expect that field 'changed' hasn't any value, because it hasn't never changed.")
                .isNull();
    }

    @Test
    void getById_shouldReturn404_whenDetailWithSuchIdIsNotExists() {
        int userID = 20;    // not existing detail
        ResponseEntity<String> response = template.getForEntity(
                UserDetailController.detailUri + "/" + userID,
                String.class
        );
        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status code 404 NOT_FOUND when request has " +
                                 "not existing id of detail, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        DocumentContext context = JsonPath.parse(response.getBody());
        String title = context.read("$.body.title");
        String detail = context.read("$.body.detail");
        assertThat(title)
                .as("Expect response with problem details where title is 'Not Found'.")
                .isEqualTo("Not Found");
        assertThat(detail)
                .as("Expect response with problem details where title is 'No such element with id %d'.", userID)
                .isEqualTo("No such element with id 20");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createDetail_shouldReturn201_whenIsValid() {
        CreatedDetail createdDetail = CreatedDetail.getValidInstance();
        createdDetail.setUserId(4L);

        // create detail
        ResponseEntity<String> postResponse = template.postForEntity(
                UserDetailController.detailUri,
                createdDetail,
                String.class
        );
        assertThat(postResponse.getStatusCode())
                .withFailMessage("Expect postResponse status code 201 CREATED when we do post " +
                                 "new valid detail, but got %s.", postResponse.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        // get new detail
        URI locationToCreatedUser = postResponse.getHeaders().getLocation();
        ResponseEntity<UserDetailDTO> getResponse = template.getForEntity(
                locationToCreatedUser,
                UserDetailDTO.class
        );
        assertThat(getResponse.getStatusCode())
                .withFailMessage("Expect getResponse status code 200 after retrieving " +
                                 "new detail, but got %s.", getResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(getResponse)
                .as("Expect getResponse body not null.")
                .isNotNull();

        // check retrieved detail
        UserDetailDTO detail = getResponse.getBody();
        assertThat(detail.getId())
                .withFailMessage("Expect detail id to be 4, but got %d.", detail.getId())
                .isEqualTo(4);
        assertThat(detail.getUserId())
                .withFailMessage("Expect userId to be %d, but got %d.", createdDetail.getUserId(), detail.getUserId())
                .isEqualTo(createdDetail.getUserId());
        assertThat(detail.getDateOfBirth())
                .withFailMessage("Expect dateOfBirth to be %s, but got %s.", createdDetail.getDateOfBirth(), detail.getDateOfBirth())
                .isEqualTo(createdDetail.getDateOfBirth());
        assertThat(detail.getPhone())
                .withFailMessage("Expect phone to be %s, but got %s.", createdDetail.getPhone(), detail.getPhone())
                .isEqualTo(createdDetail.getPhone());
        assertThat(detail.getIsDeleted())
                .withFailMessage("Expect isDeleted to be false, but got %s.", detail.getIsDeleted())
                .isFalse();
        assertThat(detail.getCreated())
                .withFailMessage("Expect 'created' timestamp to not be null, but got null.")
                .isNotNull();

        assertThat(detail.getFirstname())
                .withFailMessage("Expect firstname to be null, but got %s.", detail.getFirstname())
                .isNull();
        assertThat(detail.getLastname())
                .withFailMessage("Expect lastname to be null, but got %s.", detail.getLastname())
                .isNull();
        assertThat(detail.getPatronymic())
                .withFailMessage("Expect patronymic to be null, but got %s.", detail.getPatronymic())
                .isNull();
        assertThat(detail.getSex())
                .withFailMessage("Expect sex to be null, but got %s.", detail.getSex())
                .isNull();
        assertThat(detail.getAbout())
                .withFailMessage("Expect about to be null, but got %s.", detail.getAbout())
                .isNull();
        assertThat(detail.getChanged())
                .withFailMessage("Expect changed to be null, but got %s.", detail.getChanged())
                .isNull();
    }

    @Test
    void createdDetail_shouldReturn400_whenUserWithSuchIdAlreadyHasDetail() {
        // given
        CreatedDetail createdDetail = CreatedDetail.getValidInstance();
        createdDetail.setUserId(1L);      // user with such id already has auth

        // when
        ResponseEntity<String> response = template.postForEntity(
                UserDetailController.detailUri,
                createdDetail,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status 400 BAD_REQUEST when try to create detail " +
                                 "to user already existing detail, but got %s.", response.getStatusCode())
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
    void createdDetail_shouldReturn400_whenDetailIsNotValid() {
        CreatedDetail createdDetail = CreatedDetail.getValidInstance();
        createdDetail.setUserId(null);      // invalidate entity

        // try to create entity
        ResponseEntity<String> response = template.postForEntity(
                UserDetailController.detailUri,
                createdDetail,
                String.class
        );
        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status code 400 BAD_REQUEST when detail is " +
                                 "not valid, but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        // check violation
        DocumentContext context = JsonPath.parse(response.getBody());
        String title = context.read("$.body.title");
        assertThat(title)
                .withFailMessage("Expect validation error title to be 'Validation Error', but got %s.", title)
                .isEqualTo("Validation Error");
        String violationDetail = context.read("$.body.userId");
        assertThat(violationDetail)
                .withFailMessage("Expect violation detail to be 'User id is required', but got %s.", violationDetail)
                .isEqualTo("User id is required");
    }

    @Test
    void createDetail_shouldReturn400_whenCreatedDetailHasFieldsThatAlreadyExistsInDb() {
        CreatedDetail createdDetail = CreatedDetail.getValidInstance();
        createdDetail.setUserId(4L);                // detail for user with id 4 already exists, but we want to test with phone field
        createdDetail.setPhone("0971234567");       // db already has detail with such phone number

        // try to create
        ResponseEntity<String> response = template.postForEntity(
                UserDetailController.detailUri,
                createdDetail,
                String.class
        );
        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status code 400 BAD_REQUEST when we try to " +
                                 "create detail with already existing phone, bug bot %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        DocumentContext context = JsonPath.parse(response.getBody());
        String violationTitle = context.read("$.body.title");
        assertThat(violationTitle)
                .withFailMessage("Expect violation title to be 'Bad Request', but got %s.", violationTitle)
                .isEqualTo("Bad Request");
        String violationDetails = context.read("$.body.detail");
        assertThat(violationDetails)
                .withFailMessage("Expect violation detail to be 'Entity with such phone already exist.', but got %s.", violationDetails)
                .isEqualTo("Entity with such phone already exist.");
    }

    @Test
    void createDetail_shouldReturn404_whenTryToCreateDetailForNoExistingUser() {
        // given
        CreatedDetail createdDetail = CreatedDetail.getValidInstance();
        createdDetail.setUserId(20L);       // user with such id is not exist

        // when
        ResponseEntity<String> response = template.postForEntity(
                UserDetailController.detailUri,
                createdDetail,
                String.class
        );

        // then
        assertThat(response.getStatusCode())
                .withFailMessage("Expect response status 404 NOT_FOUND when try to create detail " +
                                 "for no existing user< but got %s.", response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        DocumentContext context = JsonPath.parse(response.getBody());
        String errorTitle = context.read("$.body.title");
        String expectTitle = "Not Found";
        assertThat(errorTitle)
                .withFailMessage("Expect response errorTitle like %s, but it is %s.", errorTitle, expectTitle)
                .isEqualTo(expectTitle);

        String errorDetail = context.read("$.body.detail");
        String expectDetail = "User with id %s not found.".formatted(createdDetail.getUserId());
        assertThat(errorDetail)
                .withFailMessage("Expect response errorDetail like %s, but it is %s.", errorDetail, expectDetail)
                .isEqualTo(expectDetail);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void changeDetail_shouldReturn200_whenIsValid() {
        ChangedUserDetail changedDetail = ChangedUserDetail.getValidInstance();

        // get detail before changing
        ResponseEntity<UserDetailDTO> getResponseBeforeChanging = template.getForEntity(
                UserDetailController.detailUri + "/" + changedDetail.getId().intValue(),
                UserDetailDTO.class
        );
        assertThat(getResponseBeforeChanging.getStatusCode())
                .withFailMessage("Expected status code 200 OK before updating detail, " +
                                 "but got %s", getResponseBeforeChanging.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        UserDetailDTO before = getResponseBeforeChanging.getBody();

        // change detail
        ResponseEntity<Void> putResponse = template.exchange(
                UserDetailController.detailUri,
                HttpMethod.PUT,
                new HttpEntity<>(changedDetail),
                Void.class
        );
        assertThat(putResponse.getStatusCode())
                .withFailMessage("Expected status code 200 OK after updating detail, " +
                                 "but got %s", putResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        URI location = putResponse.getHeaders().getLocation();

        // get changed detail
        ResponseEntity<UserDetailDTO> getResponseAfterChanging = template.getForEntity(
                location,
                UserDetailDTO.class
        );
        assertThat(getResponseAfterChanging.getStatusCode())
                .withFailMessage("Expected status code 200 OK when retrieving updated " +
                                 "detail, but got %s", getResponseAfterChanging.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        UserDetailDTO after = getResponseAfterChanging.getBody();

        // check
        assertThat(after.getId())
                .withFailMessage("Expected unchanged ID after update, but got " +
                                 "different: before=%s, after=%s", before.getId(), after.getId())
                .isEqualTo(before.getId());
        assertThat(after.getUserId())
                .withFailMessage("Expected unchanged userId after update, but " +
                                 "got different: before=%s, after=%s", before.getUserId(), after.getUserId())
                .isEqualTo(before.getUserId());
        assertThat(after.getCreated())
                .withFailMessage("Expected unchanged creation date, but got " +
                                 "different: before=%s, after=%s", before.getCreated(), after.getCreated())
                .isEqualTo(before.getCreated());
        assertThat(after.getIsDeleted())
                .withFailMessage("Expected unchanged 'isDeleted' status, but got " +
                                 "different: before=%s, after=%s", before.getIsDeleted(), after.getIsDeleted())
                .isEqualTo(before.getIsDeleted());

        assertThat(after.getChanged())
                .withFailMessage("Expected 'changed' field to be updated, but it is still " +
                                 "the same: before=%s, after=%s", before.getChanged(), after.getChanged())
                .isNotEqualTo(before.getChanged());

        assertThat(after.getFirstname())
                .withFailMessage("Expected updated firstname, but got different: " +
                                 "expected=%s, actual=%s", changedDetail.getFirstname(), after.getFirstname())
                .isEqualTo(changedDetail.getFirstname());
        assertThat(after.getLastname())
                .withFailMessage("Expected updated lastname, but got different: " +
                                 "expected=%s, actual=%s", changedDetail.getLastname(), after.getLastname())
                .isEqualTo(changedDetail.getLastname());
        assertThat(after.getPatronymic())
                .withFailMessage("Expected updated patronymic, but got different: " +
                                 "expected=%s, actual=%s", changedDetail.getPatronymic(), after.getPatronymic())
                .isEqualTo(changedDetail.getPatronymic());
        assertThat(after.getSex())
                .withFailMessage("Expected updated sex, but got different: " +
                                 "expected=%s, actual=%s", changedDetail.getSex(), after.getSex())
                .isEqualTo(changedDetail.getSex());
        assertThat(after.getDateOfBirth())
                .withFailMessage("Expected updated date of birth, but got different: " +
                                 "expected=%s, actual=%s", changedDetail.getDateOfBirth(), after.getDateOfBirth())
                .isEqualTo(changedDetail.getDateOfBirth());
        assertThat(after.getPhone())
                .withFailMessage("Expected updated phone, but got different: " +
                                 "expected=%s, actual=%s", changedDetail.getPhone(), after.getPhone())
                .isEqualTo(changedDetail.getPhone());
        assertThat(after.getAbout())
                .withFailMessage("Expected updated 'about' field, but got different: " +
                                 "expected=%s, actual=%s", changedDetail.getAbout(), after.getAbout())
                .isEqualTo(changedDetail.getAbout());
        }

    @Test
    void changeDetail_shouldReturn404_whenDetailWithSuchIdIsNotExists() {
        ChangedUserDetail changedDetail = ChangedUserDetail.getValidInstance();
        changedDetail.setId(20L);       // invalidate userDetail

        ResponseEntity<String> getResponse = template.getForEntity(
                UserDetailController.detailUri + "/" + changedDetail.getId(),
                String.class
        );
        assertThat(getResponse.getStatusCode())
                .withFailMessage("Expect getResponse status 404 NOT_FOUND when changedDetail " +
                                 "with such id isn't exists, but got %s.", getResponse.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<String> putResponse = template.exchange(
                UserDetailController.detailUri,
                HttpMethod.PUT,
                new HttpEntity<>(changedDetail),
                String.class
        );
        assertThat(putResponse.getStatusCode())
                .withFailMessage("Expect putResponse status 404 NOT_FOUND when changedDetail " +
                                 "with such id isn't exists, but got %s.", putResponse.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void changeDetail_shouldReturn400_whenDetailIsInvalid() {
        // given
        ChangedUserDetail changedDetail = ChangedUserDetail.getValidInstance();
        changedDetail.setPhone("one2345");

        ResponseEntity<String> getResponse = template.getForEntity(
                UserDetailController.detailUri + "/" + changedDetail.getId(),
                String.class
        );
        assertThat(getResponse.getStatusCode())
                .withFailMessage("Expect getResponse status 200 OK when user is " +
                                 "exists, but got %s.", getResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        // when
        ResponseEntity<String> putResponse = template.exchange(
                UserDetailController.detailUri,
                HttpMethod.PUT,
                new HttpEntity<>(changedDetail),
                String.class
        );
        assertThat(putResponse.getStatusCode())
                .withFailMessage("Expect putResponse status 400 BAD_REQUEST, when user is " +
                                 "invalid, but got %s.", putResponse.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(putResponse.getBody())
                .withFailMessage("Expect putResponse not null, but got %s.",putResponse.getBody())
                .isNotNull();

        // then
        DocumentContext context = JsonPath.parse(putResponse.getBody());
        String errorTitle = context.read("$.body.title");
        String expectTitle = "Validation Error";
        assertThat(errorTitle)
                .withFailMessage("Expect error title like '%s' but got title like '%s'.", expectTitle, errorTitle)
                .isEqualTo(expectTitle);
        String errorDetail = context.read("$.body.detail");
        String expectDetail = "One or more fields have validation errors.";
        assertThat(errorDetail)
                .withFailMessage("Expect error detail like '%s' but got detail like '%s'.", expectDetail, errorDetail)
                .isEqualTo(expectDetail);
        String errorPhoneMessage = context.read("$.body.phone");
        String expectPhoneMessage = "Invalid phone number";
        assertThat(expectPhoneMessage)
                .withFailMessage("Expect error detail like '%s' but got " +
                                 "detail like '%s'.", expectPhoneMessage, errorPhoneMessage)
                .isEqualTo(errorPhoneMessage);
    }

    @Test
    void changeDetail_shouldReturn400_whenChangedDetailHasDataThatAlreadyExistingInDb() {
        // given
        ChangedUserDetail changedDetail = ChangedUserDetail.getValidInstance();
        changedDetail.setPhone("0972345678");       // number, that already existing in db

        ResponseEntity<String> getResponse = template.getForEntity(
                UserDetailController.detailUri + "/" + changedDetail.getId(),
                String.class
        );
        assertThat(getResponse.getStatusCode())
                .withFailMessage("Expect getResponse status 200 OK when user is " +
                                 "exists, but got %s.", getResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        // when
        ResponseEntity<String> putResponse = template.exchange(
                UserDetailController.detailUri,
                HttpMethod.PUT,
                new HttpEntity<>(changedDetail),
                String.class
        );
        assertThat(putResponse.getStatusCode())
                .withFailMessage("Expect putResponse status 400 BAD_REQUEST, when user has " +
                                 "already existing phone number, but got %s.", putResponse.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(putResponse.getBody())
                .withFailMessage("Expect putResponse not null, but got %s.",putResponse.getBody())
                .isNotNull();

        // then
        DocumentContext context = JsonPath.parse(putResponse.getBody());
        String errorTitle = context.read("$.body.title");
        String expectTitle = "Bad Request";
        assertThat(errorTitle)
                .withFailMessage("Expect error title like '%s' but got title like '%s'.", expectTitle, errorTitle)
                .isEqualTo(expectTitle);
        String errorDetail = context.read("$.body.detail");
        String expectDetail = "Entity with such phone already exist.";
        assertThat(errorDetail)
                .withFailMessage("Expect error detail like '%s' but got detail like '%s'.", expectDetail, errorDetail)
                .isEqualTo(expectDetail);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteById_shouldReturn204_whenIsOk() {
        // given
        int userDetailId = 1;

        ResponseEntity<String> getBeforeDeleteResponse = template.getForEntity(
                UserDetailController.detailUri + "/" + userDetailId,
                String.class
        );
        assertThat(getBeforeDeleteResponse.getStatusCode())
                .withFailMessage("Expect getBeforeDeleteResponse status 200 OK when detail is exists, " +
                                 "but got %s.", getBeforeDeleteResponse.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        // when
        ResponseEntity<Void> deleteResponse = template.exchange(
                UserDetailController.detailUri + "/" + userDetailId,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                Void.class
        );
        assertThat(deleteResponse.getStatusCode())
                .withFailMessage("Expect deleteResponse status 204 NO_CONTENT when entity " +
                                 "was deleted by id, but got %s.", deleteResponse.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

        // then
        ResponseEntity<String> getAfterDeleteResponse = template.getForEntity(
                UserDetailController.detailUri + "/" + userDetailId,
                String.class
        );
        assertThat(getAfterDeleteResponse.getStatusCode())
                .withFailMessage("Expect getAfterDeleteResponse status 404 NOT_FOUND when detail " +
                                 "is NOT exists after deleting, but got %s.", getAfterDeleteResponse.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteById_shouldReturn404_whenDetailWithSuchIdIsNotFound() {
        // given
        int userDetailId = 20;  // detail with such id does not exist

        ResponseEntity<String> getResponse = template.getForEntity(
                UserDetailController.detailUri + "/" + userDetailId,
                String.class
        );
        assertThat(getResponse.getStatusCode())
                .withFailMessage("Expected status 404 NOT_FOUND when detail does not exist with id %d, " +
                                 "but got %s.", userDetailId, getResponse.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        // when
        ResponseEntity<String> deleteResponse = template.exchange(
                UserDetailController.detailUri + "/" + userDetailId,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                String.class
        );

        assertThat(deleteResponse.getStatusCode())
                .withFailMessage("Expected status 404 NOT_FOUND when attempting to delete " +
                                 "non-existing entity with id %d, but got %s.", userDetailId, deleteResponse.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(deleteResponse.getBody())
                .withFailMessage("Expected non-null response body for 404 error, " +
                                 "but got %s.", deleteResponse.getBody())
                .isNotNull();

        // then
        DocumentContext context = JsonPath.parse(deleteResponse.getBody());
        String errorTitle = context.read("$.body.title");
        String expectedTitle = "Not Found";
        assertThat(errorTitle)
                .withFailMessage("Expected error title '%s', but got '%s'.", expectedTitle, errorTitle)
                .isEqualTo(expectedTitle);

        String errorDetail = context.read("$.body.detail");
        String expectedDetail = "No such element with id " + userDetailId;
        assertThat(errorDetail)
                .withFailMessage("Expected error detail '%s', but got '%s'.", expectedDetail, errorDetail)
                .isEqualTo(expectedDetail);
    }


}