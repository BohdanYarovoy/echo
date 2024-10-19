package com.echoteam.app.entities.dto.changedDTO;

import com.echoteam.app.entities.Sex;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangedUserDetail {
    @Positive(message = "Id should be positive")
    @NotNull(message = "Detail id is required")
    private Long id;
    @Size(max = 50, message = "Firstname length shouldn`t be greater than 50 characters")
    private String firstname;
    @Size(max = 50, message = "Lastname length shouldn`t be greater than 50 characters")
    private String lastname;
    @Size(max = 50, message = "Patronymic length shouldn`t be greater than 50 characters")
    private String patronymic;
    private Sex sex;
    @Past(message = "Date of birth can be only in past")
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;
    @NotNull(message = "Phone is required")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phone;
    @Size(max = 100, message = "Length of about shouldn`t be greater than 100 characters")
    private String about;

    public static ChangedUserDetail getValidNecessaryFieldsInstance() {
        return ChangedUserDetail.builder()
                .id(1L)
                .dateOfBirth(LocalDate.of(2000,1,1))
                .phone("0971234567")
                .build();
    }

    public static ChangedUserDetail getValidInstance() {
        return ChangedUserDetail.builder()
                .id(1L)
                .firstname("Michael")
                .lastname("Jackson")
                .patronymic("Joseph")
                .sex(Sex.MALE)
                .dateOfBirth(LocalDate.of(2000,1,1))
                    .phone("0971234567")
                .about("American singer, songwriter, dancer, and philanthropist")
                .build();
    }

}
