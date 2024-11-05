package com.echoteam.app.unit.entities.dto.changedDTO;

import com.echoteam.app.entities.dto.changedDTO.ChangedUserDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ChangedDetailDeserializationTest {
    @Autowired
    JacksonTester<ChangedUserDetail> json;

    @Test
    void changedDetailDeserializationTest() throws IOException {
        String input = """
                {
                  "id": 1,
                  "firstname": "Michael",
                  "lastname": "Jackson",
                  "patronymic": "Joseph",
                  "sex": "MALE",
                  "phone": "0971234567",
                  "dateOfBirth": "2000-01-01",
                  "about": "American singer, songwriter, dancer, and philanthropist"
                }
                """;
        ChangedUserDetail expected = ChangedUserDetail.getValidInstance();

        assertThat(json.parse(input)).isEqualTo(expected);
    }

}
