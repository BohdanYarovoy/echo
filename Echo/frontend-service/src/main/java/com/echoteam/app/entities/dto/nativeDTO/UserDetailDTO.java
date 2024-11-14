package com.echoteam.app.entities.dto.nativeDTO;

import com.echoteam.app.entities.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    public static UserDetailDTO getValidInstance() {
        return UserDetailDTO.builder()
                .id(1L)
                .userId(1L)
                .firstname("firstname")
                .lastname("lastname")
                .patronymic("patronymic")
                .sex(Sex.MALE)
                .dateOfBirth(LocalDate.of(1990, 1,1))
                .phone("0971234567")
                .about("Details about user")
                .created(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,1)))
                .changed(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,2)))
                .isDeleted(false)
                .build();
    }
}
