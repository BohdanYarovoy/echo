package com.echoteam.app.services;

import com.echoteam.app.entities.User;
import com.echoteam.exceptions.ParameterIsNullException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    public List<User> getAll();

    public Optional<User> getById(Long id);

    public User createUser(User user);

    public User updateUser(User user) throws ParameterIsNullException;

    public void deleteUser(Long id);
}
