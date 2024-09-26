package com.echoteam.app.entities;

import com.echoteam.app.entities.dto.UserDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.exceptions.ParameterIsNullException;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "nickname", unique = true, nullable = false)
    private String nickname;

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "sex")
    private Sex sex;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "created", updatable = false)
    private Timestamp created;

    @Column(name = "changed")
    private Timestamp changed;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRole> roles;


    @PrePersist
    protected void onCreate() {
        this.created = Timestamp.valueOf(LocalDateTime.now());
        this.changed = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() {
        this.changed = Timestamp.valueOf(LocalDateTime.now());
    }

    public void acceptChanges(User user) throws ParameterIsNotValidException {
        if (!Objects.equals(this.nickname, user.getNickname())) {
            this.nickname = user.getNickname();
        }

        if (!Objects.equals(this.firstname, user.getFirstname())) {
            this.firstname = user.getFirstname();
        }

        if (!Objects.equals(this.lastname, user.getFirstname())) {
            this.lastname = user.getLastname();
        }

        if (!Objects.equals(this.patronymic, user.getPatronymic())) {
            this.patronymic = user.getPatronymic();
        }

        if (!Objects.equals(this.sex, user.getSex())) {
            this.sex = user.getSex();
        }

        if (!Objects.equals(this.email, user.getEmail())) {
            this.email = user.getEmail();
        }

        if (!Objects.equals(this.dateOfBirth, user.getDateOfBirth())) {
            if (user.getDateOfBirth().isAfter(LocalDate.now())) {
                throw new ParameterIsNotValidException(String.format("Date of birth cannot be like %s.", user.dateOfBirth));
            }
            this.dateOfBirth = user.getDateOfBirth();
        }

    }

    public UserDTO toDTO() {
        return new UserDTO(this.id,
                this.nickname,
                this.firstname,
                this.lastname,
                this.patronymic,
                this.sex,
                this.email,
                this.password,
                this.dateOfBirth,
                this.created,
                this.changed,
                roles.stream().map(UserRole::toDTO).toList());
    }

    public static User of(UserDTO dto) {
        User user = new User();
        user.setId(dto.id());
        user.setNickname(dto.nickname());
        user.setFirstname(dto.firstname());
        user.setLastname(dto.lastname());
        user.setPatronymic(dto.patronymic());
        user.setSex(dto.sex());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setDateOfBirth(dto.dateOfBirth());
        user.setCreated(dto.created());
        user.setChanged(dto.changed());
        user.setRoles(dto.roles().stream().map(UserRole::of).toList());
        return user;
    }
}
