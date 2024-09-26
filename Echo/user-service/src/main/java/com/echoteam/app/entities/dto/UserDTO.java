package com.echoteam.app.entities.dto;

import com.echoteam.app.entities.Sex;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public record UserDTO(Long id,
                      String nickname,
                      String firstname,
                      String lastname,
                      String patronymic,
                      Sex sex,
                      String email,
                      String password,
                      LocalDate dateOfBirth,
                      Timestamp created,
                      Timestamp changed,
                      List<UserRoleDTO> roles) {
}
