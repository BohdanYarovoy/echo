package com.echoteam.app.unit.entities.dto.nativeDTO;

import com.echoteam.app.entities.dto.nativeDTO.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserDTOSerializationTest {
    @Autowired
    JacksonTester<UserDTO> json;

    @Test
    void userDTOSerializationTest() throws IOException {
        String pathToJson = "/json/dto/entityDTO/nativeDTO/UserDTOExpectation.json";
        UserDTO user = UserDTO.getValidInstance();

        assertThat(json.write(user)).isEqualToJson(pathToJson);

        // Перевірка id
        assertThat(json.write(user)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(user)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(user.getId().intValue());

        // Перевірка nickname
        assertThat(json.write(user)).hasJsonPathStringValue("@.nickname");
        assertThat(json.write(user)).extractingJsonPathStringValue("@.nickname")
                .isEqualTo(user.getNickname());

        // Перевірка created
        assertThat(json.write(user)).hasJsonPathStringValue("@.created");
        assertThat(json.write(user)).extractingJsonPathStringValue("@.created")
                .isNotEmpty();

        // Перевірка changed
        assertThat(json.write(user)).hasJsonPathStringValue("@.changed");
        assertThat(json.write(user)).extractingJsonPathStringValue("@.changed")
                .isNotEmpty();

        // Перевірка isDeleted
        assertThat(json.write(user)).hasJsonPathBooleanValue("@.isDeleted");
        assertThat(json.write(user)).extractingJsonPathBooleanValue("@.isDeleted")
                .isEqualTo(user.getIsDeleted());

        // Перевірка userDetail
        assertThat(json.write(user)).hasJsonPathMapValue("@.userDetail");
        assertThat(json.write(user)).extractingJsonPathMapValue("@.userDetail")
                .isNotEmpty();

        // Перевірка userAuth
        assertThat(json.write(user)).hasJsonPathMapValue("@.userAuth");
        assertThat(json.write(user)).extractingJsonPathMapValue("@.userAuth")
                .isNotEmpty();

        // Перевірка roles
        assertThat(json.write(user)).hasJsonPathArrayValue("@.roles");
        assertThat(json.write(user)).extractingJsonPathArrayValue("@.roles")
                .isNotEmpty();
    }

}
