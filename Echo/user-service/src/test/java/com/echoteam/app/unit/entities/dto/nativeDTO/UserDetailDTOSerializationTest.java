package com.echoteam.app.unit.entities.dto.nativeDTO;

import com.echoteam.app.entities.dto.nativeDTO.UserDetailDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
public class UserDetailDTOSerializationTest {
    @Autowired
    JacksonTester<UserDetailDTO> json;

    @Test
    void userDetailDTOSerializationTest() throws IOException {
        String pathToJson = "/json/dto/entityDTO/nativeDTO/UserDetailDTOExpectation.json";
        UserDetailDTO detail = UserDetailDTO.getValidInstance();

        assertThat(json.write(detail)).isEqualToJson(pathToJson);

        // Перевіряємо наявність окремих полів
        assertThat(json.write(detail)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(detail)).hasJsonPathNumberValue("@.userId");
        assertThat(json.write(detail)).hasJsonPathStringValue("@.firstname");
        assertThat(json.write(detail)).hasJsonPathStringValue("@.lastname");
        assertThat(json.write(detail)).hasJsonPathStringValue("@.patronymic");
        assertThat(json.write(detail)).hasJsonPathStringValue("@.sex");
        assertThat(json.write(detail)).hasJsonPathStringValue("@.dateOfBirth");
        assertThat(json.write(detail)).hasJsonPathStringValue("@.phone");
        assertThat(json.write(detail)).hasJsonPathStringValue("@.about");
        assertThat(json.write(detail)).hasJsonPathStringValue("@.created");
        assertThat(json.write(detail)).hasJsonPathStringValue("@.changed");

        // Перевірка значень конкретних полів
        assertThat(json.write(detail)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(detail.getId().intValue());
        assertThat(json.write(detail)).extractingJsonPathNumberValue("@.userId")
                .isEqualTo(detail.getUserId().intValue());
        assertThat(json.write(detail)).extractingJsonPathStringValue("@.firstname")
                .isEqualTo(detail.getFirstname());
        assertThat(json.write(detail)).extractingJsonPathStringValue("@.lastname")
                .isEqualTo(detail.getLastname());
        assertThat(json.write(detail)).extractingJsonPathStringValue("@.patronymic")
                .isEqualTo(detail.getPatronymic());
        assertThat(json.write(detail)).extractingJsonPathStringValue("@.sex")
                .isEqualTo(detail.getSex().toString()); // Якщо Sex - enum
        assertThat(json.write(detail)).extractingJsonPathStringValue("@.dateOfBirth")
                .isEqualTo(detail.getDateOfBirth().toString()); // LocalDate
        assertThat(json.write(detail)).extractingJsonPathStringValue("@.phone")
                .isEqualTo(detail.getPhone());
        assertThat(json.write(detail)).extractingJsonPathStringValue("@.about")
                .isEqualTo(detail.getAbout());
        assertThat(json.write(detail)).extractingJsonPathStringValue("@.created")
                .isNotEmpty();
        assertThat(json.write(detail)).extractingJsonPathStringValue("@.changed")
                .isNotEmpty();
    }

}
