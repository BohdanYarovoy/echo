package com.echoteam.app.entities.dto.entityDTO.changed;

import com.echoteam.app.entities.dto.entityDTO.UserRoleDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ChangedUser {
    @NotNull(message = "Entities ID is null.")
    private Long id;
    @NotNull(message = "Nickname can`t be empty")
    @Size(min = 4, max = 50, message = "Nickname cannot be greater than 50 and less than 4 characters")
    private String nickname;
    @Valid
    private ChangedUserDetail userDetail;
    @Valid
    private ChangedUserAuth userAuth;
    private List<UserRoleDTO> roles;

}
