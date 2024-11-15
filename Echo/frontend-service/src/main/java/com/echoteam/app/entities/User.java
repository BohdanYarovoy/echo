package com.echoteam.app.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String nickname;
    private byte[] avatar;
    private Timestamp created;
    private Timestamp changed;
    private Boolean isDeleted = false;
    private UserDetail userDetail;
    private UserAuth userAuth;
    private List<UserRole> roles;

    protected void onCreate() {
        this.created = Timestamp.valueOf(LocalDateTime.now());
        this.isDeleted = false;
    }

    protected void onUpdate() {
        this.changed = Timestamp.valueOf(LocalDateTime.now());
    }

    public static User getValidInstance() {
        return User.builder()
                .nickname("nickname")
                .created(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,1)))
                .changed(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,2)))
                .isDeleted(false)
                .userDetail(UserDetail.getValidInstance())
                .userAuth(UserAuth.getValidInstance())
                .roles(UserRole.getValidInstanceList())
                .build();
    }
}
