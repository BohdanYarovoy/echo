package com.echoteam.app.dao;

import com.echoteam.app.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Short> {

    List<UserRole> getUserRolesByIdIn(Collection<Short> id);

}
