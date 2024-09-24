package com.echoteam.app.entities;

import com.echoteam.app.exceptions.ParameterIsNullException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    Short id;

    @Column(name = "role_name", unique = true, nullable = false)
    String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JsonBackReference
    // todo: replace this solution. Create standard DTO for userRole.
    List<User> users;


    public void acceptChanges(UserRole userRole) {
        if (!Objects.equals(this.name, userRole.getName())) {
            this.name = userRole.getName();
        }
    }

}
