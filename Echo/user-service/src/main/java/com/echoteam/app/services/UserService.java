package com.echoteam.app.services;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.UserDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.exceptions.ParameterIsNullException;
import com.echoteam.app.exceptions.UniqueRecordAlreadyExistsException;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.Optional;


public interface UserService {

    public List<UserDTO> getAll();

    public UserDTO getById(Long id);

    public UserDTO createUser(UserDTO user) throws ParameterIsNotValidException, ConstraintViolationException, UniqueRecordAlreadyExistsException;

    public UserDTO updateUser(UserDTO user) throws ParameterIsNotValidException, UniqueRecordAlreadyExistsException;

    public void deleteById(Long id);
}
