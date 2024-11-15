package com.echoteam.app.unit.entities.dto.createdDTO;

import com.echoteam.app.entities.dto.createdDTO.CreatedRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CreatedRoleDeserializationTest {
    @Autowired
    JacksonTester<CreatedRole> json;

    @Test
    void createdRoleDeserializationTest() throws IOException {
        String input = """
                {
                  "name": "KING"
                }
                """;
        CreatedRole expected = new CreatedRole("KING");

        assertThat(json.parse(input)).isEqualTo(expected);
    }

}