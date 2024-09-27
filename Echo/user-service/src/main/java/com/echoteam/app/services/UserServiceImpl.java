package com.echoteam.app.services;

import com.echoteam.app.dao.UserRepository;
import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.UserDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Transactional
    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(User::toDTO)
                .toList();
    }

    @Transactional
    @Override
    public UserDTO getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get().toDTO();
        } else {
            throw new EntityNotFoundException(String.format("User with id %d not found.", id));
        }
    }

    @Transactional
    @Override
    public UserDTO createUser(UserDTO userDto) {
        if (userDto.id() != null) {
            throw new ParameterIsNotValidException("There is no need user with id.");
        }
        return userRepository.save(User.of(userDto)).toDTO();
    }

    @Transactional
    @Override
    public UserDTO updateUser(UserDTO userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.id());
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            dbUser.acceptChanges(User.of(userDto));
            return userRepository.save(dbUser).toDTO();
        } else {
            throw new EntityNotFoundException(String.format("User with id %d not found.", userDto.id()));
        }
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
