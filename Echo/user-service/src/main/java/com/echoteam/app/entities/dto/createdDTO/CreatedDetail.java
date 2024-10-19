package com.echoteam.app.entities.dto.createdDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreatedDetail {
        @Positive(message = "Id should be positive")
        @NotNull(message = "User id is required")
        private Long userId;
        @Past(message = "Date of birth can be only in past")
        @NotNull(message = "Date of birth is required")
        private LocalDate dateOfBirth;
        @NotNull(message = "Phone is required")
        @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number")
        private String phone;

        public static CreatedDetail getValidInstance() {
                return CreatedDetail.builder()
                        .userId(1L)
                        .phone("0981234567")
                        .dateOfBirth(LocalDate.of(2000, 1, 1))
                        .build();
        }
}
