package com.echoteam.app.entities.dto.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String nickname;
    private byte[] avatar;
    private Timestamp created;
    private Timestamp changed;
    private Boolean isDeleted;
    private UserDetailDTO userDetail;
    private UserAuthDTO userAuth;
    private List<UserRoleDTO> roles;

    public void hidePassword() {
        if (this.userAuth != null)
            this.userAuth.hidePassword();
    }

    public static List<UserDTO> removeAuth(List<UserDTO> users) {
        return users.stream()
                .peek(UserDTO::hidePassword)
                .toList();
    }

}
