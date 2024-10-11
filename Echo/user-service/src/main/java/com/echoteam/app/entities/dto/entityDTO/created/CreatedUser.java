package com.echoteam.app.entities.dto.entityDTO.created;

import com.echoteam.app.entities.dto.entityDTO.UserRoleDTO;
import jakarta.validation.Valid;
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
    @Size(min = 4, max = 50, message = "Nickname cannot be greater than 50 and less than 4 characters")
    private String nickname;
    @Valid
    private CreatedDetail userDetail;
    @Valid
    private CreatedAuth userAuth;
    private List<UserRoleDTO> roles;

}
