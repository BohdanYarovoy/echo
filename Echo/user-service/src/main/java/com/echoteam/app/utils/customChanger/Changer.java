package com.echoteam.app.utils.customChanger;

import com.echoteam.app.entities.User;
import com.echoteam.app.entities.UserAuth;
import com.echoteam.app.entities.UserDetail;
import com.echoteam.app.entities.UserRole;

import java.util.List;

public interface Changer {
    void changeUser(User userToChange, User changes);
    void changeUserDetail(UserDetail detailToChange, UserDetail changes);
    void changeUserAuth(UserAuth authToChange, UserAuth changes);
    void changeUserRoles(List<UserRole> userRolesToChange, List<UserRole> changes);

}
