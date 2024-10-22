package com.echoteam.app.integration.controllers;

import com.echoteam.app.controllers.UserDetailController;
import com.echoteam.app.entities.Sex;
import com.echoteam.app.entities.dto.nativeDTO.UserDetailDTO;
import com.echoteam.app.exceptions.InProgressException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

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
        assertThat(body[0].getSex()).isEqualTo(Sex.MALE);
        assertThat(body[0].getDateOfBirth()).isEqualTo(LocalDate.of(1990,1,1));
        assertThat(body[0].getIsDeleted()).isFalse();
        assertThat(body[0].getCreated()).isNotNull();

        assertThat(body[0].getChanged()).isNull();
    }

    @Test
    void getById_shouldReturn200_whenItIsOk() {
        // todo: create test
        throw new InProgressException();
    }

}
