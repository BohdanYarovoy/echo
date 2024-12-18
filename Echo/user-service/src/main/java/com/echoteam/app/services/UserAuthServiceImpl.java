package com.echoteam.app.services;

import com.echoteam.app.dao.UserAuthRepository;
import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.dto.nativeDTO.UserAuthDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.echoteam.app.entities.mappers.UserAuthMapper.INSTANCE;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
    private final UserAuthRepository authRepository;
    private final UserService userService;

    @Transactional
    @Override
    public Page<UserAuth> getAll(Pageable pageable) {
        return authRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public UserAuth getById(Long id) {
        if (id == null)
            throw new ParameterIsNotValidException("There is need id for auth.");

        var authOptional = authRepository.findById(id);
        return authOptional.orElseThrow(() -> new EntityNotFoundException(
                String.format("Auth with id %d not found.", id)
        ));
    }

    @Transactional
    @Override
    public UserAuth create(UserAuthDTO createdAuth) {
        if (createdAuth.getId() != null)
            throw new ParameterIsNotValidException("There is no need auth with id.");

        var user = userService.getById(createdAuth.getUserId());
        var userAuth = INSTANCE.toUserAuth(createdAuth);
        userAuth.setUser(user);
        user.setUserAuth(userAuth);
        return authRepository.save(userAuth);
    }

    @Transactional
    @Override
    public UserAuth update(UserAuthDTO updatedAuth) {
        if (updatedAuth.getId() == null)
            throw new ParameterIsNotValidException("Id is require.");
        if (updatedAuth.getUserId() != null)
            throw new ParameterIsNotValidException("There is no need auth with user id.");

        var forUpdate = INSTANCE.toUserAuth(updatedAuth);
        var existing = getById(updatedAuth.getId());
        acceptChanges(existing, forUpdate);
        return authRepository.save(existing);
    }

    private void acceptChanges(UserAuth existing, UserAuth forUpdate) {
        existing.setEmail(forUpdate.getEmail());
    }

    @Transactional
    @Override
    public void delete(Long id) {
        boolean exists = authRepository.existsById(id);
        if (!exists)
            throw new EntityNotFoundException(String.format("Auth with id %d not found.", id));

        authRepository.deleteById(id);
    }
}
