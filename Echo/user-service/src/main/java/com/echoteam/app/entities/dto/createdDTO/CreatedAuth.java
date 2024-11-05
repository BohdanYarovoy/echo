package com.echoteam.app.entities.dto.createdDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreatedAuth {
    @Positive(message = "Id should be positive")
    @NotNull(message = "User id is required")
    private Long userId;
    @NotNull(message = "Email address is required")
    @Size(max = 100, message = "Email length shouldn`t be greater than 100 characters")
    @Email(message = "Incorrect email address")
    private String email;
    @NotNull(message = "Password is required")
    @Size(min = 8, max = 32, message = "Length should be between 8-32 inclusive")
    @Pattern(regexp = "^[\\S]+$", message = "Password should be without: whitespaces")
    private String password;

    public static CreatedAuth getValidInstance() {
        return CreatedAuth.builder()
                .userId(1L)
                .email("example@gmail.com")
                .password("pass1234")
                .build();
    }
}
