package com.echoteam.app.unit.entities.dto.changedDTO;

import com.echoteam.app.entities.dto.changedDTO.ChangedUserAuth;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangedAuthValidationTest {
    Validator validator;

    @BeforeEach
    void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void whenValidChangedUserAuth_thenNoConstraintViolation() {
        // given
        ChangedUserAuth auth = ChangedUserAuth.getValidInstance();

        // when
        Set<ConstraintViolation<ChangedUserAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void whenIdIsNull_thenHasConstraintViolation() {
        // given
        ChangedUserAuth auth = ChangedUserAuth.getValidInstance();
        auth.setId(null);

        // when
        Set<ConstraintViolation<ChangedUserAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Auth id is required");
    }

    @Test
    void whenIdIsNegative_thenHasConstraintViolation() {
        // given
        ChangedUserAuth auth = ChangedUserAuth.getValidInstance();
        auth.setId(-1L);

        // when
        Set<ConstraintViolation<ChangedUserAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id should be positive");
    }

    @Test
    void whenEmailIsNull_thenHasConstraintViolation() {
        // given
        ChangedUserAuth auth = ChangedUserAuth.getValidInstance();
        auth.setEmail(null);

        // when
        Set<ConstraintViolation<ChangedUserAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Email address is required");
    }

    @Test
    void whenEmailIsNotValid_thenHasConstraintViolation() {
        // given
        ChangedUserAuth auth = ChangedUserAuth.getValidInstance();
        auth.setEmail("hello my friend");

        // when
        Set<ConstraintViolation<ChangedUserAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Incorrect email address");
    }

    @Test
    void whenEmailLengthIsTooLarge_thenHasConstraintViolation() {
        // given
        ChangedUserAuth auth = ChangedUserAuth.getValidInstance();
        auth.setEmail("very.long.email.address.that.exceeds.limit.but.is.still.valid@subdomain.example.the.typical.length.com");

        // when
        Set<ConstraintViolation<ChangedUserAuth>> violations = validator.validate(auth);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Email length shouldn`t be greater than 100 characters");
    }

}



