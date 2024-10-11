package com.echoteam.app.entities.dto.entityDTO;

import com.echoteam.app.entities.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDTO {
    private Long id;
    private Long userId;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Sex sex;
    private LocalDate dateOfBirth;
    private String phone;
    private String about;
    private Timestamp created;
    private Timestamp changed;
    private Boolean isDeleted;
}
