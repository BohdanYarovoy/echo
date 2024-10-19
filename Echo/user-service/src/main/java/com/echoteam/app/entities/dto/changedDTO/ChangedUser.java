package com.echoteam.app.entities.dto.changedDTO;

import com.echoteam.app.entities.dto.nativeDTO.UserRoleDTO;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangedUser {
    @Positive(message = "Id should be positive")
    @NotNull(message = "User id is required")
    private Long id;
    @NotNull(message = "Nickname can`t be empty")
    @Size(min = 4, max = 50, message = "Nickname cannot be greater than 50 and less than 4 characters")
    @Pattern(regexp = "^[\\S]+$", message = "Nickname should be without: whitespaces")
    private String nickname;
    @NotEmpty(message = "List of roles is empty")
    private List<UserRoleDTO> roles;

    public static ChangedUser getValidInstance() {
        List<UserRoleDTO> roles = List.of(
                new UserRoleDTO((short) 2, "ADMIN")
        );
        return ChangedUser.builder()
                .id(1L)
                .nickname("nickname")
                .roles(roles)
                .build();
    }

}
