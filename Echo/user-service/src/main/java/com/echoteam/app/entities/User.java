package com.echoteam.app.entities;

import com.echoteam.exceptions.ParameterIsNullException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "sex")
    private String sex;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "created", updatable = false)
    private LocalDate created;

    @Column(name = "changed")
    private LocalDate changed;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRole> roles;

    public void acceptChanges(User user) throws ParameterIsNullException {
        if (Objects.isNull(user)) {
            throw new ParameterIsNullException();
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
                throw new ParameterIsNullException(String.format("Date of birth cannot be like %s.", user.dateOfBirth));
            }
            this.dateOfBirth = user.getDateOfBirth();
        }

    }
}
