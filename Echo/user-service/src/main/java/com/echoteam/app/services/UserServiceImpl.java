package com.echoteam.app.services;

import com.echoteam.app.dao.UserRepository;
import com.echoteam.app.entities.User;
import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.dto.entityDTO.UserDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.utils.customChanger.Changer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.echoteam.app.entities.dto.mappers.UserMapper.INSTANCE;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final Changer changer;

    @Transactional
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User getById(Long id) {
        if (id == null)
            throw new ParameterIsNotValidException("There is need id for user.");

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new EntityNotFoundException(String.format("User with id %d not found.", id));
        }
    }

    @Transactional
    @Override
    public User createUser(UserDTO userDto) {
        if (userDto.getId() != null)
            throw new ParameterIsNotValidException("There is no need id for user.");
        User newUser = this.mapToUserWithRelations(userDto);
        return userRepository.save(newUser);
    }

    @Transactional
    @Override
    public User updateUser(UserDTO userDto) {
        if (userDto.getId() == null)
            throw new ParameterIsNotValidException("Id is required.");
        User existingUser = getById(userDto.getId());
        User changedUser = INSTANCE.toUserFromDTO(userDto);
        changer.changeUser(existingUser, changedUser);
        return userRepository.save(existingUser);
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

    private User mapToUserWithRelations(UserDTO userDTO) {
        User user = INSTANCE.toUserFromDTO(userDTO);
        if (user.getUserAuth() != null) {
            UserAuth userAuth = user.getUserAuth();
            userAuth.setUser(user);
            user.setUserAuth(userAuth);
        }

        if (user.getUserDetail() != null) {
            UserDetail userDetail = user.getUserDetail();
            userDetail.setUser(user);
            user.setUserDetail(userDetail);
        }

        return user;
    }
}
