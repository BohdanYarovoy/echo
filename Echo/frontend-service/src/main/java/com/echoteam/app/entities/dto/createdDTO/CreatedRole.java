package com.echoteam.app.entities.dto.createdDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatedRole {
    @NotNull(message = "Name for role is required")
    @Pattern(regexp = "^[A-Z_]+$", message = "Role name must be upper case")
    @Size(max = 50, message = "Role name length cannot more than 50 characters")
    private String name;

    public static CreatedRole getValidInstance() {
        return new CreatedRole("KING");
    }
}
