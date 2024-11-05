package com.echoteam.app.unit.entities.dto.createdDTO;

import com.echoteam.app.entities.dto.createdDTO.CreatedDetail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CreatedDetailValidationTest {
    Validator validator;

    @BeforeEach
    void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void whenValidCreatedDetails_thenNoConstraintViolation() {
        // given
        CreatedDetail detail = CreatedDetail.getValidInstance();

        // when
        Set<ConstraintViolation<CreatedDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void whenUserIdIsNull_thenHasConstraintViolation() {
        // given
        CreatedDetail detail = CreatedDetail.getValidInstance();
        detail.setUserId(null);

        // when
        Set<ConstraintViolation<CreatedDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("User id is required");
    }

    @Test
    void whenUserIdIsNegative_thenHasConstraintViolation() {
        // given
        CreatedDetail detail = CreatedDetail.getValidInstance();
        detail.setUserId(-1L);

        // when
        Set<ConstraintViolation<CreatedDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id should be positive");
    }

    @Test
    void whenPhoneIsNull_thenHasConstraintViolation() {
        // given
        CreatedDetail detail = CreatedDetail.getValidInstance();
        detail.setPhone(null);

        // when
        Set<ConstraintViolation<CreatedDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Phone is required");
    }

    @Test
    void whenPhoneIsNoLikePattern_thenHasConstraintViolation() {
        // given
        CreatedDetail detail = CreatedDetail.getValidInstance();
        detail.setPhone("123*fds3");

        // when
        Set<ConstraintViolation<CreatedDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid phone number");
    }

    @Test
    void whenDateOfBirthIsNull_thenHasConstraintViolation() {
        // given
        CreatedDetail detail = CreatedDetail.getValidInstance();
        detail.setDateOfBirth(null);

        // when
        Set<ConstraintViolation<CreatedDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Date of birth is required");
    }

    @Test
    void whenDateOfBirthIsNotPast_thenHasConstraintViolation() {
        // given
        CreatedDetail detail = CreatedDetail.getValidInstance();
        detail.setDateOfBirth(LocalDate.now());

        // when
        Set<ConstraintViolation<CreatedDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Date of birth can be only in past");
    }

}
