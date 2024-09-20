package com.echoteam.app.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    Long id;

    @Column(name = "role_name")
    String name;

    @ManyToMany(mappedBy = "roles")
    List<User> users;

}
