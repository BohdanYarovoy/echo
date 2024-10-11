package com.echoteam.app.entities.dto.entityDTO.created;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatedAuth {
    private Long userId;
    @NotNull
    @Size(max = 100, message = "Email length shouldn`t be greater than 100 characters")
    @Email(message = "Incorrect email address")
    private String email;
    @NotNull
    @Size(min = 8, max = 32, message = "Length should be between 8-32 inclusive")
    @Pattern(regexp = "^[\\S]+$", message = "Password should be without: whitespaces")
    private String password;
}
