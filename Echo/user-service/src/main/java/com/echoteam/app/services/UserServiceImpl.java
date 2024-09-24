package com.echoteam.app.services;

import com.echoteam.app.dao.UserRepository;
import com.echoteam.app.entities.User;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.exceptions.ParameterIsNullException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) throws ParameterIsNotValidException {
        if (user.getId() != null) {
            throw new ParameterIsNotValidException("There is no need user with id.");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) throws ParameterIsNotValidException {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            dbUser.acceptChanges(user);
            return userRepository.save(dbUser);
        } else {
            throw new EntityNotFoundException(String.format("User with id: %d not found.", user.getId()));
        }
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


}
