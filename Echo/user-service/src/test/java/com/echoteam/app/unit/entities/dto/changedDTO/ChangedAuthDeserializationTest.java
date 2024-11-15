package com.echoteam.app.unit.entities.dto.changedDTO;

import com.echoteam.app.entities.dto.changedDTO.ChangedUserAuth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ChangedAuthDeserializationTest {
    @Autowired
    JacksonTester<ChangedUserAuth> json;

    @Test
    void changedAuthDeserializationTest() throws IOException {
        String input = """
                {
                  "id": 1,
                  "email": "example@gmail.com"
                }
                """;
        ChangedUserAuth expected = ChangedUserAuth.getValidInstance();

        assertThat(json.parse(input)).isEqualTo(expected);
    }

}





