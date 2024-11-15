package com.echoteam.app.services;

import com.echoteam.app.dao.UserRoleRepository;
import com.echoteam.app.entities.UserRole;
import com.echoteam.app.entities.dto.nativeDTO.UserRoleDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static com.echoteam.app.entities.mappers.UserRoleMapper.INSTANCE;

@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl implements UserRoleService{
    private final UserRoleRepository userRoleRepository;

    @Transactional
    @Override
    public Page<UserRole> getAll(Pageable pageable) {
        return userRoleRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public UserRole findRoleById(Short id) {
        if (id == null)
            throw new ParameterIsNotValidException("There is need id for role.");

        var optionalUserRole = userRoleRepository.findById(id);
        if (optionalUserRole.isEmpty())
            throw new EntityNotFoundException(String.format("Role with id %d not found.", id));
        return optionalUserRole.get();
    }

    @Transactional
    @Override
    public UserRole createUserRole(UserRoleDTO dto) throws ParameterIsNotValidException {
        if (dto.getId() != null)
            throw new ParameterIsNotValidException("There is no need role with id.");

        var role = INSTANCE.toUserRole(dto);
        return userRoleRepository.save(role);
    }

    @Transactional
    @Override
    public UserRole updateUserRole(UserRoleDTO dto) {
        if (dto.getId() == null)
            throw new ParameterIsNotValidException("Id is required.");

        var changed = INSTANCE.toUserRole(dto);
        var existing = findRoleById(dto.getId());
        acceptChanges(existing, changed);
        return userRoleRepository.save(existing);
    }

    private void acceptChanges(UserRole existing, UserRole changed) {
        existing.setName(changed.getName());
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
        return userRoleRepository.getUserRolesByIdIn(id);
    }

}









