package com.echoteam.app.unit.entities.dto.changedDTO;

import com.echoteam.app.entities.dto.changedDTO.ChangedUser;
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

public class ChangedUserValidationTest {
    Validator validator;

    @BeforeEach
    void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void whenValidChangedUser_thenNoConstraintViolation() {
        // given
        ChangedUser user = ChangedUser.getValidInstance();

        // when
        Set<ConstraintViolation<ChangedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void whenIdIsNull_thenHasConstraintViolation() {
        // given
        ChangedUser user = ChangedUser.getValidInstance();
        user.setId(null);

        // when
        Set<ConstraintViolation<ChangedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("User id is required");
    }

    @Test
    void whenIdIsNegative_thenHasConstraintViolation() {
        // given
        ChangedUser user = ChangedUser.getValidInstance();
        user.setId(-1L);

        // when
        Set<ConstraintViolation<ChangedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id should be positive");
    }

    @Test
    void whenNicknameIsNull_thenHasConstraintViolation() {
        // given
        ChangedUser user = ChangedUser.getValidInstance();
        user.setNickname(null);

        // when
        Set<ConstraintViolation<ChangedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Nickname can't be empty");
    }

    @Test
    void whenNicknameIsTooShort_thenHasConstraintViolation() {
        // given
        ChangedUser user = ChangedUser.getValidInstance();
        user.setNickname("jo");

        // when
        Set<ConstraintViolation<ChangedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Nickname cannot be greater than 50 and less than 4 characters");
    }

    @Test
    void whenNicknameIsTooLarge_thenHasConstraintViolation() {
        // given
        ChangedUser user = ChangedUser.getValidInstance();
        user.setNickname("mynicknameisweryLargeandiknowthatitisnotvalidbuticansetmynicknamebytheway");

        // when
        Set<ConstraintViolation<ChangedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Nickname cannot be greater than 50 and less than 4 characters");
    }

    @Test
    void whenNicknameHasProhibitedCharacters_thenHasConstraintViolation() {
        // given
        ChangedUser user = ChangedUser.getValidInstance();
        user.setNickname("Bad boy");

        // when
        Set<ConstraintViolation<ChangedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Nickname should be without: whitespaces");
    }

    @Test
    void whenRolesIsNull_thenHasConstraintViolation() {
        // given
        ChangedUser user = ChangedUser.getValidInstance();
        user.setRoles(null);

        // when
        Set<ConstraintViolation<ChangedUser>> violations = validator.validate(user);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("List of roles is empty");
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

}







