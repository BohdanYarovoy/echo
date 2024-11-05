package com.echoteam.app.unit.entities.dto.nativeDTO;

import com.echoteam.app.entities.dto.nativeDTO.UserRoleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserRoleDTOSerializationTest {
    @Autowired
    JacksonTester<UserRoleDTO> json;

    @Test
    void userRoleDTOSerializationTest() throws IOException {
        String pathToJson = "/json/dto/entityDTO/nativeDTO/UserRoleDTOExpectation.json";
        UserRoleDTO role = UserRoleDTO.getValidInstance();

        assertThat(json.write(role)).isEqualToJson(pathToJson);

        // id
        assertThat(json.write(role)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(role)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(role.getId().intValue());

        // name
        assertThat(json.write(role)).hasJsonPathStringValue("@.name");
        assertThat(json.write(role)).extractingJsonPathStringValue("@.name")
                .isEqualTo(role.getName());
    }
}
