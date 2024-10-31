package com.echoteam.app.services;

import com.echoteam.app.dao.UserDetailRepository;
import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.dto.nativeDTO.UserDetailDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.echoteam.app.entities.mappers.UserDetailMapper.INSTANCE;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService{
    private final UserDetailRepository userDetailRepository;
    private final UserService userService;

    @Transactional
    @Override
    public List<UserDetail> getAll() {
        return userDetailRepository.findAll();
    }

    @Transactional
    @Override
    public UserDetail getById(Long id) {
        if (id == null)
            throw new ParameterIsNotValidException("There is need id for detail.");

        var optionalUserDetail = userDetailRepository.findById(id);
        return optionalUserDetail.orElseThrow(() -> new EntityNotFoundException(
                String.format("No such element with id %d", id)
        ));
    }

    @Transactional
    @Override
    public UserDetail create(UserDetailDTO userDetailDTO) {
        if (userDetailDTO.getId() != null)
            throw new ParameterIsNotValidException("There is no need details with id.");

        var user = userService.getById(userDetailDTO.getUserId());
        var userDetail = INSTANCE.toUserDetail(userDetailDTO);
        userDetail.setUser(user);
        user.setUserDetail(userDetail);
        return userDetailRepository.save(userDetail);
    }

    @Transactional
    @Override
    public UserDetail update(UserDetailDTO userDetailDTO) {
        if (userDetailDTO.getId() == null)
            throw new ParameterIsNotValidException("Id is require.");
        if (userDetailDTO.getUserId() != null)
            throw new ParameterIsNotValidException("There is no need details with user id.");

        var existing = getById(userDetailDTO.getId());
        var changed = INSTANCE.toUserDetail(userDetailDTO);
        acceptChanges(existing,changed);
        return userDetailRepository.save(existing);
    }

    private void acceptChanges(UserDetail existing, UserDetail changed) {
        existing.setFirstname(changed.getFirstname());
        existing.setLastname(changed.getLastname());
        existing.setPatronymic(changed.getPatronymic());
        existing.setSex(changed.getSex());
        existing.setDateOfBirth(changed.getDateOfBirth());
        existing.setPhone(changed.getPhone());
        existing.setAbout(changed.getAbout());
    }

    @Override
    public void delete(Long id) {
        boolean exists = userDetailRepository.existsById(id);
        if (!exists)
            throw new EntityNotFoundException("No such element with id " + id);

        userDetailRepository.deleteById(id);
    }

}
