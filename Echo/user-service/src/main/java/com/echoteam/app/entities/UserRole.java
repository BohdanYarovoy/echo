package com.echoteam.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    List<User> users;

    @Override
    public boolean equals(Object o) {
        return Objects.equals(this.id, ((UserRole)o).id);
    }
}
