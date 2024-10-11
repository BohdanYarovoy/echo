package com.echoteam.app.entities.dto.entityDTO.changed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChangedRole {
    @NotNull(message = "Id is required")
    private Short id;
    @NotNull(message = "Name for role is required")
    @NotBlank(message = "Name for role cannot be blank")
    private String name;
}
