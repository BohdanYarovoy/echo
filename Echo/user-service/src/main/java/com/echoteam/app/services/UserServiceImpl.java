package com.echoteam.app.services;

import com.echoteam.app.dao.UserRepository;
import com.echoteam.app.entities.User;
import com.echoteam.app.entities.dto.UserDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.exceptions.UniqueRecordAlreadyExistsException;
import jakarta.persistence.Column;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(User::toDTO)
                .toList();
    }

    @Override
    public UserDTO getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get().toDTO();
        } else {
            throw new EntityNotFoundException(String.format("User with id %d not found.", id));
        }
    }

    @Override
    public UserDTO createUser(UserDTO userDto) throws ParameterIsNotValidException,
                                                        UniqueRecordAlreadyExistsException {
        if (userDto.id() != null) {
            throw new ParameterIsNotValidException("There is no need user with id.");
        }
        try {
             return userRepository.save(User.of(userDto)).toDTO();
        } catch (DataIntegrityViolationException ex) {
            String errorMessage = recognizeDataIntegrityViolationException(ex);
            throw new UniqueRecordAlreadyExistsException(errorMessage, ex);
        }
    }

    @Override
    public UserDTO updateUser(UserDTO userDto) throws ParameterIsNotValidException, UniqueRecordAlreadyExistsException {
        Optional<User> optionalUser = userRepository.findById(userDto.id());
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            dbUser.acceptChanges(User.of(userDto));
            try {
                return userRepository.save(dbUser).toDTO();
            } catch (DataIntegrityViolationException ex) {
                String errorMessage = recognizeDataIntegrityViolationException(ex);
                throw new UniqueRecordAlreadyExistsException(errorMessage, ex);
            }
        } else {
            throw new EntityNotFoundException(String.format("UserDTO with id: %d not found.", userDto.id()));
        }
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private String recognizeDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();
        String requestMessage = "User with such %s already exist.";

        Class userClass = User.class;
        Field[] declaredFields = userClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation.unique() && message.contains("(" + columnAnnotation.name() + ")")) {
                    return requestMessage.formatted(columnAnnotation.name());
                }
            }
        }
        return "Something was wrong. Try again later please.";
    }
}
