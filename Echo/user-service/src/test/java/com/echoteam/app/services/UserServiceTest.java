package com.echoteam.app.services;

import com.echoteam.app.entities.Sex;
import com.echoteam.app.entities.User;
import com.echoteam.app.entities.UserRole;
import com.echoteam.app.exceptions.ParameterIsNotValidException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Test
    public void addUserToDB() {
        List<UserRole> roles =  userRoleService.getUserRolesByIdIn(List.of((short)2));

        User user = User.builder()
                .nickname("jacoonda")
                .firstname("Evgene")
                .lastname("Olhovski")
                .sex(Sex.MALE)
                .email("evene.olhovski@gmail.com")
                .password("pass1234")
                .dateOfBirth(LocalDate.of(2005,05,17))
                .roles(roles)
                .build();
        try {
            userService.createUser(user);
        } catch (ParameterIsNotValidException e) {
            throw new RuntimeException(e);
        }
    }

}