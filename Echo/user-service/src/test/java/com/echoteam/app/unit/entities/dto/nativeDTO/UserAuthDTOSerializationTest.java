package com.echoteam.app.unit.entities.dto.nativeDTO;

import com.echoteam.app.entities.dto.nativeDTO.UserAuthDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserAuthDTOSerializationTest {
    @Autowired
    JacksonTester<UserAuthDTO> json;

    @Test
    void userAuthDTOSerializationTest() throws IOException {
        String pathToJson = "/json/dto/entityDTO/nativeDTO/UserAuthDTOExpectation.json";
        UserAuthDTO auth = UserAuthDTO.getValidInstance();

        assertThat(json.write(auth)).isEqualToJson(pathToJson);

        // Перевіряємо наявність окремих полів
        assertThat(json.write(auth)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(auth)).hasJsonPathNumberValue("@.userId");
        assertThat(json.write(auth)).hasJsonPathStringValue("@.email");
        assertThat(json.write(auth)).hasJsonPathStringValue("@.password");
        assertThat(json.write(auth)).hasJsonPathStringValue("@.created");
        assertThat(json.write(auth)).hasJsonPathStringValue("@.changed");
        assertThat(json.write(auth)).hasJsonPathBooleanValue("@.isDeleted");

        // Перевірка значень конкретних полів
        assertThat(json.write(auth)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(auth.getId().intValue());
        assertThat(json.write(auth)).extractingJsonPathNumberValue("@.userId")
                .isEqualTo(auth.getUserId().intValue());
        assertThat(json.write(auth)).extractingJsonPathStringValue("@.email")
                .isEqualTo(auth.getEmail());
        assertThat(json.write(auth)).extractingJsonPathStringValue("@.password")
                .isEqualTo(auth.getPassword());
        assertThat(json.write(auth)).extractingJsonPathStringValue("@.created")
                .isNotEmpty();
        assertThat(json.write(auth)).extractingJsonPathStringValue("@.changed")
                .isNotEmpty();
        assertThat(json.write(auth)).extractingJsonPathBooleanValue("@.isDeleted")
                .isEqualTo(auth.getIsDeleted());
    }
}
