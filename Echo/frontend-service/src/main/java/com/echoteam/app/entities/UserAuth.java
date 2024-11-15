package com.echoteam.app.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserAuth {
    private Long id;
    private User user;
    private String email;
    private String password;
    private Timestamp created;
    private Timestamp changed;
    private Boolean isDeleted = false;

    protected void onCreate() {
        this.created = Timestamp.valueOf(LocalDateTime.now());
        this.isDeleted = false;
    }

    protected void onUpdate() {
        this.changed = Timestamp.valueOf(LocalDateTime.now());
    }

    public static UserAuth getValidInstance() {
        return UserAuth.builder()
                .id(1L)
                .email("example@gmail.com")
                .password("pass1234")
                .created(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,1)))
                .changed(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,2)))
                .isDeleted(false)
                .build();
    }
}
