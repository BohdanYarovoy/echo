package com.echoteam.app.services;

import com.echoteam.app.dao.UserRoleRepository;
import com.echoteam.app.entities.UserRole;
import com.echoteam.app.entities.dto.UserRoleDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleRepository userRoleRepository;

    @Override
    public List<UserRoleDTO> getAll() {
        List<UserRole> roles = userRoleRepository.findAll();
        return roles.stream().map(UserRole::toDTO).toList();
    }

    @Override
    public UserRoleDTO findRoleById(Short id) {
        Optional<UserRole> role = userRoleRepository.findById(id);
        if (role.isPresent()) {
            return role.get().toDTO();
        } else {
            throw new EntityNotFoundException(String.format("Role with id %d not found.", id));
        }
    }

    @Override
    public UserRoleDTO createUserRole(UserRoleDTO dto) throws ParameterIsNotValidException {
        if (dto.id() != null) {
            throw new ParameterIsNotValidException("There is no need role with id.");
        }
        return userRoleRepository.save(UserRole.of(dto)).toDTO();
    }

    @Override
    public UserRoleDTO updateUserRole(UserRoleDTO dto) {
        Optional<UserRole> userRoleOptional = userRoleRepository.findById(dto.id());
        UserRole userRoleDB = userRoleOptional
                .orElseThrow(() -> new EntityNotFoundException(String.format("Role with id:%d not found", dto.id())));
        userRoleDB.acceptChanges(UserRole.of(dto));
        return userRoleDB.toDTO();
    }

    @Override
    public void deleteById(Short id) {
        userRoleRepository.deleteById(id);
    }

    @Override
    public List<UserRoleDTO> getUserRolesByIdIn(Collection<Short> id) {
        List<UserRole> roles = userRoleRepository.getUserRolesByIdIn(id);
        return roles.stream()
                .map(UserRole::toDTO)
                .toList();
    }
}
