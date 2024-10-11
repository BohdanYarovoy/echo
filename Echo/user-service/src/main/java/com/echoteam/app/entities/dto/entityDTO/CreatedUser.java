package com.echoteam.app.entities.dto.entityDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatedUser {
    @NotNull(message = "Nickname can`t be empty")
    @Size(max = 50, message = "Nickname size shouldn`t be greater than 50 characters")
    private String nickname;
    @ForValidation
    private CreatedDetail userDetail;
    @ForValidation
    private CreatedAuth userAuth;
    private List<UserRoleDTO> roles;

}
