package com.echoteam.app.services;

import com.echoteam.app.entities.User;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.exceptions.ParameterIsNullException;

import java.util.List;
import java.util.Optional;


public interface UserService {

    public List<User> getAll();

    public Optional<User> getById(Long id);

    public User createUser(User user) throws ParameterIsNotValidException;

    public User updateUser(User user) throws ParameterIsNotValidException;

    public void deleteById(Long id);
}
