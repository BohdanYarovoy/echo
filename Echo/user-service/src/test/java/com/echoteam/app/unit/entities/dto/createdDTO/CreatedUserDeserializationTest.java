package com.echoteam.app.unit.entities.dto.createdDTO;

import com.echoteam.app.entities.dto.nativeDTO.UserRoleDTO;
import com.echoteam.app.entities.dto.createdDTO.CreatedUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
class CreatedUserDeserializationTest {
    @Autowired
    JacksonTester<CreatedUser> json;

    @Test
    void createdUserDeserializationTest() throws IOException {
        String input = """
                {
                  "nickname": "nickname",
                  "roles": [
                    {
                      "id": 2,
                      "name": "ADMIN"
                    }
                  ]
                }
                """;
        CreatedUser expected = CreatedUser.builder()
                .nickname("nickname")
                .roles(List.of(
                        new UserRoleDTO((short) 2, "ADMIN")
                ))
                .build();

        assertThat(json.parse(input)).isEqualTo(expected);

    }

}