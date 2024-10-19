package com.echoteam.app.unit.entities.dto.changedDTO;

import com.echoteam.app.entities.dto.changedDTO.ChangedRole;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangedRoleValidationTest {
    Validator validator;

    @BeforeEach
    void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void whenValidChangedRole_thenNoConstraintViolation() {
        // given
        ChangedRole role = ChangedRole.getValidInstance();

        // when
        Set<ConstraintViolation<ChangedRole>> violations = validator.validate(role);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void whenIdIsNull_thenHasConstraintViolation() {
        // given
        ChangedRole role = ChangedRole.getValidInstance();
        role.setId(null);

        // when
        Set<ConstraintViolation<ChangedRole>> violations = validator.validate(role);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id is required");
    }

    @Test
    void whenIdIsNegative_thenHasConstraintViolation() {
        // given
        ChangedRole role = ChangedRole.getValidInstance();
        role.setId((short) -1);

        // when
        Set<ConstraintViolation<ChangedRole>> violations = validator.validate(role);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id should be positive");
    }

    @Test
    void whenNameIsNull_thenHasConstraintViolation() {
        // given
        ChangedRole role = ChangedRole.getValidInstance();
        role.setName(null);

        // when
        Set<ConstraintViolation<ChangedRole>> violations = validator.validate(role);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Name for role is required");
    }

    @Test
    void whenNameIsNotLikePattern_thenHasConstraintViolation() {
        // given
        ChangedRole role = ChangedRole.getValidInstance();
        role.setName("Hello_guys");

        // when
        Set<ConstraintViolation<ChangedRole>> violations = validator.validate(role);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Role name must be upper case");
    }

    @Test
    void whenNameLengthIsTooLarge_thenHasConstraintViolation() {
        // given
        ChangedRole role = ChangedRole.getValidInstance();
        role.setName("IHAVEMY_KEYISPRESSEDALWAYS_IMSOSORRYBUTIWANTTOKILLMYSELFITISJOKE");

        // when
        Set<ConstraintViolation<ChangedRole>> violations = validator.validate(role);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Role name length cannot more than 50 characters");
    }

}






