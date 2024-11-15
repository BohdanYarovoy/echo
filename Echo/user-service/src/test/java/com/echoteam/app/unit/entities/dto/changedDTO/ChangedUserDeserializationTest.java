package com.echoteam.app.unit.entities.dto.changedDTO;

import com.echoteam.app.entities.dto.changedDTO.ChangedUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ChangedUserDeserializationTest {
    @Autowired
    JacksonTester<ChangedUser> json;

    @Test
    void changedUserDeserializationTest() throws IOException {
        String input = """
                {
                  "id": 1,
                  "nickname": "nickname",
                  "roles": [
                    {
                      "id": 2,
                      "name": "ADMIN"
                    }
                  ]
                }
                """;
        ChangedUser expected = ChangedUser.getValidInstance();

        assertThat(json.parse(input)).isEqualTo(expected);
    }

}
