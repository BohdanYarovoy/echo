package com.echoteam.app.entities.dto.entityDTO.changed;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChangedUserAuth {
    @NotNull
    private Long id;
    @NotNull
    @Size(max = 100, message = "Email length shouldn`t be greater than 100 characters")
    @Email(message = "Incorrect email address")
    private String email;

}
