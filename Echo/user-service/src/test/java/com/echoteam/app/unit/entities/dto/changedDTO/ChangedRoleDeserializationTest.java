package com.echoteam.app.unit.entities.dto.changedDTO;

import com.echoteam.app.entities.dto.changedDTO.ChangedRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ChangedRoleDeserializationTest {
    @Autowired
    JacksonTester<ChangedRole> json;

    @Test
    void changedRoleDeserializationTest() throws IOException {
        String input = """
                {
                  "id": 1,
                  "name": "KING_SIZE"
                }
                """;
        ChangedRole expected = ChangedRole.getValidInstance();

        assertThat(json.parse(input)).isEqualTo(expected);
    }

}
