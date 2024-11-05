package com.echoteam.app.services;

import com.echoteam.app.dao.UserRepository;
import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.nativeDTO.UserDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.echoteam.app.entities.mappers.UserMapper.INSTANCE;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public User getById(Long id) {
        if (id == null)
            throw new ParameterIsNotValidException("There is need id for user.");

        var optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new EntityNotFoundException(String.format("User with id %d not found.", id));
        return optionalUser.get();
    }

    @Transactional
    @Override
    public User createUser(UserDTO userDto) {
        User newUser = INSTANCE.toUserFromDTO(userDto);
        return userRepository.save(newUser);
    }

    @Transactional
    @Override
    public User updateUser(UserDTO userDto) {
        if (userDto.getId() == null)
            throw new ParameterIsNotValidException("Id is required.");

        User existingUser = getById(userDto.getId());
        User changedUser = INSTANCE.toUserFromDTO(userDto);
        acceptChanges(existingUser, changedUser);
        return userRepository.save(existingUser);
    }

    private void acceptChanges(User exist, User changed) {
        exist.setNickname(changed.getNickname());
        exist.setRoles(changed.getRoles());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        boolean exists = userRepository.existsById(id);
        if (!exists) {
            throw new EntityNotFoundException(String.format("User with id %d not found.", id));
        }
        userRepository.deleteById(id);
    }

}
