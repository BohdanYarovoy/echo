package com.echoteam.app.unit.entities.dto.createdDTO;

import com.echoteam.app.entities.dto.createdDTO.CreatedRole;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class CreatedRoleValidationTest {
    Validator validator;

    @BeforeEach
    void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void whenValidCreatedRole_thenNoConstraintViolation() {
        // given
        CreatedRole role = CreatedRole.getValidInstance();

        // when
        Set<ConstraintViolation<CreatedRole>> violations = validator.validate(role);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void whenNameIsNull_thenHasConstraintViolation() {
        // given
        CreatedRole role = CreatedRole.getValidInstance();
        role.setName(null);

        // when
        Set<ConstraintViolation<CreatedRole>> violations = validator.validate(role);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Name for role is required");
    }

    @Test
    void whenNameIsNotLikePattern_thenHasConstraintViolation() {
        // given
        CreatedRole role = CreatedRole.getValidInstance();
        role.setName("King");

        // when
        Set<ConstraintViolation<CreatedRole>> violations = validator.validate(role);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Role name must be upper case");
    }

    @Test
    void whenNameLengthIsTooLarge_thenHasConstraintViolation() {
        // given
        CreatedRole role = CreatedRole.getValidInstance();
        role.setName("IHAVEMY_KEYISPRESSEDALWAYS_IMSOSORRYBUTIWANTTOKILLMYSELFITISJOKE");

        // when
        Set<ConstraintViolation<CreatedRole>> violations = validator.validate(role);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Role name length cannot more than 50 characters");
    }

}
