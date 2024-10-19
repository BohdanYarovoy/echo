package com.echoteam.app.unit.entities.dto.createdDTO;

import com.echoteam.app.entities.dto.createdDTO.CreatedDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@JsonTest
class CreatedDetailDeserializationTest {
    @Autowired
    JacksonTester<CreatedDetail> json;

    @Test
    void createdDetailDeserializationTest() throws IOException {
        String input = """
                {
                  "userId": 1,
                  "phone": "0971773437",
                  "dateOfBirth": "2003-03-03"
                }
                """;
        CreatedDetail detail = CreatedDetail.builder()
                .userId(1L)
                .phone("0971773437")
                .dateOfBirth(LocalDate.of(2003, 3, 3))
                .build();

        assertThat(json.parse(input)).isEqualTo(detail);
    }

}