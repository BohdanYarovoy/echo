package com.echoteam.app.entities.dto.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDTO {
    private Long id;
    private Long userId;
    private String email;
    private String password;
    private Timestamp created;
    private Timestamp changed;
    private Boolean isDeleted;

    public void hidePassword() {
        this.password = "********";
    }
}
