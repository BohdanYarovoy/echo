package com.echoteam.app.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDetail {
    private Long id;
    private User user;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Sex sex;
    private LocalDate dateOfBirth;
    private String phone;
    private String about;
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

    public static UserDetail getValidInstance() {
        User user = User.builder()
                .id(1L)
                .build();
        return UserDetail.builder()
                .id(1L)
                .user(user)
                .firstname("firstname")
                .lastname("lastname")
                .patronymic("patronymic")
                .sex(Sex.MALE)
                .dateOfBirth(LocalDate.of(1990, 1,1))
                .phone("0971234567")
                .about("Details about user")
                .created(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,1)))
                .changed(Timestamp.valueOf(LocalDateTime.of(2000,1,1,3,2)))
                .isDeleted(false)
                .build();
    }
}
