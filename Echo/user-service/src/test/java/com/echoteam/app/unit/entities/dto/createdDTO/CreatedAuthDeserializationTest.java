package com.echoteam.app.unit.entities.dto.createdDTO;

import com.echoteam.app.entities.dto.createdDTO.CreatedAuth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CreatedAuthDeserializationTest {

    @Autowired
    JacksonTester<CreatedAuth> json;

    @Test
    void createdAuthDeserializationTest() throws IOException {
        String input = """
                {
                  "userId": 1,
                  "email": "example@gmail.com",
                  "password": "pass1234"
                }
                """;
        CreatedAuth expected = CreatedAuth.builder()
                .userId(1L)
                .email("example@gmail.com")
                .password("pass1234")
                .build();

        assertThat(json.parse(input)).isEqualTo(expected);
    }
}