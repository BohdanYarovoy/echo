package com.echoteam.app.entities.dto.changedDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ChangedRole {
    @Positive(message = "Id should be positive")
    @NotNull(message = "Id is required")
    private Short id;
    @NotNull(message = "Name for role is required")
    @Pattern(regexp = "^[A-Z_]+$", message = "Role name must be upper case")
    @Size(max = 50, message = "Role name length cannot more than 50 characters")
    private String name;

    public static ChangedRole getValidInstance() {
        return ChangedRole.builder()
                .id((short) 1)
                .name("KING_SIZE")
                .build();
    }
}