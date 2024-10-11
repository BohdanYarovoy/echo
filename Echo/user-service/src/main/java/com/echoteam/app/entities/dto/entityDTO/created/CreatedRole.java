package com.echoteam.app.entities.dto.entityDTO.created;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreatedRole {
    @NotNull(message = "Name for role is required")
    @NotBlank(message = "Name for role cannot be blank")
    private String name;
}
