package com.echoteam.app.entities;

import com.echoteam.app.entities.dto.UserRoleDTO;
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

    public static UserRole of(UserRoleDTO dto) {
        UserRole role = new UserRole();
        role.setId(dto.id());
        role.setName(dto.name());
        return role;
    }


    public void acceptChanges(UserRole userRole) {
        if (!Objects.equals(this.name, userRole.getName())) {
            this.name = userRole.getName();
        }
    }

    public UserRoleDTO toDTO() {
        return new UserRoleDTO(this.id, this.name);
    }

}
