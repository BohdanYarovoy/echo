package com.echoteam.app.entities.dto.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

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


    public void doRoutine() {
        hidePassword();
        makeReference();
    }

    public void hidePassword() {
        if (this.userAuth != null)
            this.userAuth.hidePassword();
    }

    public void makeReference() {
        if (Objects.nonNull(this.userDetail))
            this.userDetail.setUserId(this.id);

        if (Objects.nonNull(this.userAuth))
            this.userAuth.setUserId(this.id);
    }

}
