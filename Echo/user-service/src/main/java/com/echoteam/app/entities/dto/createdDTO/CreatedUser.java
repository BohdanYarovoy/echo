package com.echoteam.app.entities.dto.createdDTO;

import com.echoteam.app.entities.dto.nativeDTO.UserRoleDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreatedUser {
    @NotNull(message = "Nickname can`t be empty")
    @Size(min = 4, max = 50, message = "Nickname cannot be greater than 50 and less than 4 characters")
    @Pattern(regexp = "^[\\S]+$", message = "Nickname should be without: whitespaces")
    private String nickname;
    @NotEmpty(message = "List of roles is empty")
    private List<UserRoleDTO> roles;

    public static CreatedUser getValidInstance() {
        List<UserRoleDTO> roles = List.of(
                new UserRoleDTO((short) 1, "USER")
        );
        return CreatedUser.builder()
                .nickname("nickname")
                .roles(roles)
                .build();
    }

}
