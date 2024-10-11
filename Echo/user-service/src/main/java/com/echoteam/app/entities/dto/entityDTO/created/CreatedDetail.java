package com.echoteam.app.entities.dto.entityDTO.created;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatedDetail {
        private Long userId;
        @NotNull
        @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
        private String phone;
        @Past
        @NotNull(message = "Date of birth is required")
        private LocalDate dateOfBirth;
}
