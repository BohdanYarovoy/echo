package com.echoteam.app.services;

import com.echoteam.app.dao.UserRoleRepository;
import com.echoteam.app.entities.UserRole;
import com.echoteam.app.entities.dto.entityDTO.UserRoleDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.echoteam.app.entities.dto.mappers.UserRoleMapper.INSTANCE;

@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleRepository userRoleRepository;


    @Transactional
    @Override
    public List<UserRole> getAll() {
        List<UserRole> roles = userRoleRepository.findAll();
        return roles;
    }

    @Transactional
    @Override
    public UserRole findRoleById(Short id) {
        if (id == null)
            throw new ParameterIsNotValidException("There is need id for role.");

        Optional<UserRole> optionalUserRole = userRoleRepository.findById(id);
        if (optionalUserRole.isPresent()) {
            return optionalUserRole.get();
        } else {
            throw new EntityNotFoundException(String.format("Role with id %d not found.", id));
        }
    }

    @Transactional
    @Override
    public UserRole createUserRole(UserRoleDTO dto) throws ParameterIsNotValidException {
        if (dto.getId() != null) {
            throw new ParameterIsNotValidException("There is no need role with id.");
        }
        UserRole role = INSTANCE.toUserRole(dto);
        role = userRoleRepository.save(role);
        return role;
    }

    @Transactional
    @Override
    public UserRole updateUserRole(UserRoleDTO dto) {
        UserRole role = INSTANCE.toUserRole(dto);
        role = userRoleRepository.save(role);
        return role;
    }

    @Transactional
    @Override
    public void deleteById(Short id) {
        boolean exists = userRoleRepository.existsById(id);
        if (!exists) {
            throw new EntityNotFoundException(String.format("Role with id %d not found.", id));
        }
        userRoleRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<UserRole> getUserRolesByIdIn(Collection<Short> id) {
        List<UserRole> roles = userRoleRepository.getUserRolesByIdIn(id);
        return roles;
    }

}









