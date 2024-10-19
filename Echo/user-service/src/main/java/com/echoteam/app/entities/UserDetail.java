package com.echoteam.app.entities;

import jakarta.persistence.*;
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
@Entity
@Builder
@Table(name = "user_details")
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_detail_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "sex")
    private Sex sex;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "about")
    private String about;

    @Column(name = "created", updatable = false)
    private Timestamp created;

    @Column(name = "changed")
    private Timestamp changed;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @PrePersist
    protected void onCreate() {
        this.created = Timestamp.valueOf(LocalDateTime.now());
        this.isDeleted = false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.changed = Timestamp.valueOf(LocalDateTime.now());
    }

    public static UserDetail getValidInstance() {
        return UserDetail.builder()
                .id(1L)
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
