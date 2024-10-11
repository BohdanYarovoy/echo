package com.echoteam.app.services;

import com.echoteam.app.dao.UserDetailRepository;
import com.echoteam.app.entities.User;
import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.dto.entityDTO.UserDetailDTO;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import com.echoteam.app.utils.customChanger.Changer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.echoteam.app.entities.dto.mappers.UserDetailMapper.INSTANCE;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService{

    private final UserDetailRepository userDetailRepository;
    private final UserService userService;
    private final Changer changer;

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

        Optional<UserDetail> optionalUserDetail = userDetailRepository.findById(id);
        return optionalUserDetail
                .orElseThrow(() -> new EntityNotFoundException("No such element with id " + id));
    }

    @Transactional
    @Override
    public UserDetail create(UserDetailDTO userDetailDTO) {
        if (userDetailDTO.getId() != null)
            throw new ParameterIsNotValidException("There is no need details with id.");

        User user = userService.getById(userDetailDTO.getUserId());
        UserDetail userDetail = INSTANCE.toUserDetail(userDetailDTO);
        userDetail.setUser(user);
        user.setUserDetail(userDetail);
        return userDetailRepository.save(userDetail);
    }

    @Override
    public UserDetail update(UserDetailDTO userDetailDTO) {
        if (userDetailDTO.getId() == null)
            throw new ParameterIsNotValidException("Id is require.");
        if (userDetailDTO.getUserId() != null)
            throw new ParameterIsNotValidException("There is no need details with user id.");

        UserDetail existingDetail = getById(userDetailDTO.getId());
        UserDetail changedDetail = INSTANCE.toUserDetail(userDetailDTO);
        changer.changeUserDetail(existingDetail, changedDetail);
        return userDetailRepository.save(existingDetail);
    }

    @Override
    public void delete(Long id) {
        boolean exists = userDetailRepository.existsById(id);
        if (!exists)
            throw new EntityNotFoundException("No such element with id " + id);

        userDetailRepository.deleteById(id);
    }

}
