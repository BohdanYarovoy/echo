package com.echoteam.app.entities.dto.nativeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    public static UserAuthDTO getValidInstance() {
        return UserAuthDTO.builder()
                .id(1L)
                .userId(1L)
                .email("example@gmail.com")
                .password("pass1234")
                .created(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,1)))
                .changed(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,2)))
                .isDeleted(false)
                .build();
    }
}
