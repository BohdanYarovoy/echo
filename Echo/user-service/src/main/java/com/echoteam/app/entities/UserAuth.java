package com.echoteam.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "user_auths")
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_auth_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

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
