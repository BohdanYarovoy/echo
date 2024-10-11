package com.echoteam.app.entities.dto.entityDTO.changed;

import com.echoteam.app.entities.Sex;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ChangedUserDetail {
    @NotNull
    private Long id;
    @Size(max = 50, message = "Firstname length shouldn`t be greater than 50 characters")
    private String firstname;
    @Size(max = 50, message = "Lastname length shouldn`t be greater than 50 characters")
    private String lastname;
    @Size(max = 50, message = "Patronymic length shouldn`t be greater than 50 characters")
    private String patronymic;
    private Sex sex;
    @Past
    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;
    @NotNull
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
    private String phone;
    @Size(max = 100, message = "Field about length shouldn`t be greater than 100 characters")
    private String about;

}
