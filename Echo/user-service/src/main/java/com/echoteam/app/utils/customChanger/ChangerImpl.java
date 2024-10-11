package com.echoteam.app.utils.customChanger;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.UserRole;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ChangerImpl implements Changer {

    @Override
    public void changeUser(User userToChange, User changes) {
        if (Objects.isNull(userToChange) || Objects.isNull(changes))
            throw new ParameterIsNotValidException("One of two parameter is null");

        if (Objects.equals(userToChange.getNickname(), changes.getNickname()))
            userToChange.setNickname(changes.getNickname());

        if (Objects.equals(userToChange.getIsDeleted(),changes.getIsDeleted()))
            userToChange.setIsDeleted(changes.getIsDeleted());

        if (!Objects.isNull(changes.getUserDetail())) {
            if (Objects.isNull(userToChange.getUserDetail()))
                userToChange.setUserDetail(new UserDetail());
                userToChange.getUserDetail().setUser(userToChange);
            changeUserDetail(userToChange.getUserDetail(), changes.getUserDetail());
        }

        if (!Objects.isNull(changes.getUserAuth())) {
            if (Objects.isNull(userToChange.getUserAuth())) {
                userToChange.setUserAuth(new UserAuth());
                userToChange.getUserAuth().setUser(userToChange);
            }
            changeUserAuth(userToChange.getUserAuth(), changes.getUserAuth());
        }
        changeUserRoles(userToChange.getRoles(), changes.getRoles());
    }

    @Override
    public void changeUserDetail(UserDetail detailToChange, UserDetail changes) {
        if (!Objects.equals(detailToChange.getFirstname(), changes.getFirstname()))
            detailToChange.setFirstname(changes.getFirstname());

        if (!Objects.equals(detailToChange.getLastname(), changes.getLastname()))
            detailToChange.setLastname(changes.getLastname());

        if (!Objects.equals(detailToChange.getPatronymic(), changes.getPatronymic()))
            detailToChange.setPatronymic(changes.getPatronymic());

        if (!Objects.equals(detailToChange.getSex(), changes.getSex()))
            detailToChange.setSex(changes.getSex());

        if (!Objects.equals(detailToChange.getDateOfBirth(), changes.getDateOfBirth()))
            detailToChange.setDateOfBirth(changes.getDateOfBirth());

        if (!Objects.equals(detailToChange.getPhone(), changes.getPhone()))
            detailToChange.setPhone(changes.getPhone());

        if (!Objects.equals(detailToChange.getAbout(), changes.getAbout()))
            detailToChange.setAbout(changes.getAbout());
    }

    @Override
    public void changeUserAuth(UserAuth authToChange, UserAuth changes) {
        if (Objects.isNull(changes))
            return;

        if (!Objects.equals(authToChange.getEmail(), changes.getEmail()))
            authToChange.setEmail(changes.getEmail());

        if (!Objects.equals(authToChange.getPassword(), changes.getPassword()))
            authToChange.setPassword(changes.getPassword());
    }

    @Override
    public void changeUserRoles(List<UserRole> userRolesToChange, List<UserRole> changes) {
        if (Objects.isNull(changes))
            return;

        if (Objects.isNull(userRolesToChange))
            userRolesToChange = new ArrayList<>();

        for (UserRole role : changes) {
            if (!userRolesToChange.contains(role)) {
                userRolesToChange.add(role);
            }
        }
        userRolesToChange.removeIf(role -> !changes.contains(role));
    }
}
