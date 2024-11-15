package com.echoteam.app.entities.dto.changedDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangedUserAuth {
    @Positive(message = "Id should be positive")
    @NotNull(message = "Auth id is required")
    private Long id;
    @NotBlank(message = "Email address is required")
    @Size(max = 100, message = "Email length shouldn`t be greater than 100 characters")
    @Email(message = "Incorrect email address")
    private String email;

    public static ChangedUserAuth getValidInstance() {
        return ChangedUserAuth.builder()
                .id(1L)
                .email("example@gmail.com")
                .build();
    }

}