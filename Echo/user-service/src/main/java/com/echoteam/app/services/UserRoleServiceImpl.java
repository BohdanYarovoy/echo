package com.echoteam.app.services;

import com.echoteam.app.dao.UserRoleRepository;
import com.echoteam.app.entities.UserRole;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.exceptions.ParameterIsNullException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> getAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public Optional<UserRole> findRoleById(Short id) {
        return userRoleRepository.findById(id);
    }

    @Override
    public UserRole createUserRole(UserRole role) throws ParameterIsNotValidException {
        if (role.getId() != null) {
            throw new ParameterIsNotValidException("There is no need role with id.");
        }
        return userRoleRepository.save(role);
    }

    @Override
    public UserRole updateUserRole(UserRole role) {
        Optional<UserRole> userRoleOptional = userRoleRepository.findById(role.getId());
        UserRole userRoleDB = userRoleOptional
                .orElseThrow(() -> new EntityNotFoundException(String.format("Role with id:%d not found", role.getId())));
        userRoleDB.acceptChanges(role);
        return userRoleDB;
    }

    @Override
    public void deleteById(Short id) {
        userRoleRepository.deleteById(id);
    }

    @Override
    public List<UserRole> getUserRolesByIdIn(Collection<Short> id) {
        return userRoleRepository.getUserRolesByIdIn(id);
    }
}
