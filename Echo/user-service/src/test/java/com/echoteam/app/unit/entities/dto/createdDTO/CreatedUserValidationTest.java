package com.echoteam.app.unit.entities.dto.createdDTO;

import com.echoteam.app.entities.dto.createdDTO.CreatedUser;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatedUserValidationTest {
    private Validator validator;

    @BeforeEach
    void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void whenValidCreatedUser_thenNoConstraintViolation() {
        // given
        CreatedUser user = CreatedUser.getValidInstance();

        // when
        Set<ConstraintViolation<CreatedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void whenNicknameIsNull_thenHasConstraintViolation() {
        // given
        CreatedUser user = CreatedUser.getValidInstance();
        user.setNickname(null);

        // when
        Set<ConstraintViolation<CreatedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Nickname can't be empty");
    }

    @Test
    void whenNicknameTooShort_thenHasConstraintViolation() {
        // given
        CreatedUser user = CreatedUser.getValidInstance();
        user.setNickname("abc");

        // when
        Set<ConstraintViolation<CreatedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Nickname cannot be greater than 50 and less than 4 characters");
    }

    @Test
    void whenNicknameTooLarge_thenHasConstraintViolation() {
        // given
        CreatedUser user = CreatedUser.getValidInstance();
        user.setNickname("mynicknameisweryLargeandiknowthatitisnotvalidbuticansetmynicknamebytheway");

        // when
        Set<ConstraintViolation<CreatedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Nickname cannot be greater than 50 and less than 4 characters");
    }

    @Test
    void whenNicknameHasProhibitedCharacters_thenHasConstraintViolation() {
        // given
        CreatedUser user = CreatedUser.getValidInstance();
        user.setNickname("Best boy");

        // when
        Set<ConstraintViolation<CreatedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Nickname should be without: whitespaces");
    }

    @Test
    void whenRolesIsEmpty_thenHasConstraintViolation() {
        // given
        CreatedUser user = CreatedUser.getValidInstance();
        user.setRoles(List.of());

        // when
        Set<ConstraintViolation<CreatedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("List of roles is empty");
    }

    @Test
    void whenRolesIsNull_thenHasConstraintViolation() {
        // given
        CreatedUser user = CreatedUser.getValidInstance();
        user.setRoles(null);

        // when
        Set<ConstraintViolation<CreatedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("List of roles is empty");
    }

}
