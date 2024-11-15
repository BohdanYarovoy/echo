package com.echoteam.app.unit.entities.dto.changedDTO;

import com.echoteam.app.entities.dto.changedDTO.ChangedUserDetail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class ChangedDetailValidationTest {
    Validator validator;

    @BeforeEach
    void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void whenValidChangedUserDetail_thenNoConstraintViolation() {
        // given
        ChangedUserDetail detail = ChangedUserDetail.getValidInstance();

        // when
        Set<ConstraintViolation<ChangedUserDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void whenValidChangedUserDetailWithOnlyNecessaryFields_thenNoConstraintViolation() {
        // given
        ChangedUserDetail detail = ChangedUserDetail.getValidNecessaryFieldsInstance();

        // when
        Set<ConstraintViolation<ChangedUserDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void whenIdIsNull_thenHasConstraintViolation() {
        // given
        ChangedUserDetail detail = ChangedUserDetail.getValidInstance();
        detail.setId(null);

        // when
        Set<ConstraintViolation<ChangedUserDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Detail id is required");
    }

    @Test
    void whenIdIsNegative_thenHasConstraintViolation() {
        // given
        ChangedUserDetail detail = ChangedUserDetail.getValidInstance();
        detail.setId(-1L);

        // when
        Set<ConstraintViolation<ChangedUserDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Id should be positive");
    }

    @Test
    void whenFirstnameIsTooLarge_thenHasConstraintViolation() {
        // given
        ChangedUserDetail detail = ChangedUserDetail.getValidNecessaryFieldsInstance();
        detail.setFirstname("mynameistoolargebecauseihavemyloverparentsandtheylovemy");

        // when
        Set<ConstraintViolation<ChangedUserDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Firstname length shouldn`t be greater than 50 characters");
    }

    @Test
    void whenLastnameIsTooLarge_thenHasConstraintViolation() {
        // given
        ChangedUserDetail detail = ChangedUserDetail.getValidNecessaryFieldsInstance();
        detail.setLastname("mylastnameisprettylargebecauseiwanttobitaworldrecord");

        // when
        Set<ConstraintViolation<ChangedUserDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Lastname length shouldn`t be greater than 50 characters");
    }

    @Test
    void whenPatronymicIsTooLarge_thenHasConstraintViolation() {
        // given
        ChangedUserDetail detail = ChangedUserDetail.getValidNecessaryFieldsInstance();
        detail.setPatronymic("mypatronymicistoolargeandiamlikeatargetforboolingfrommyclassmate");

        // when
        Set<ConstraintViolation<ChangedUserDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Patronymic length shouldn`t be greater than 50 characters");
    }

    @Test
    void whenDateOfBirthIsNotNull_thenHasConstraintViolation() {
        // given
        ChangedUserDetail detail = ChangedUserDetail.getValidInstance();
        detail.setDateOfBirth(null);

        // when
        Set<ConstraintViolation<ChangedUserDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Date of birth is required");
    }

    @Test
    void whenDateOfBirthIsNotPast_thenHasConstraintViolation() {
        // given
        ChangedUserDetail detail = ChangedUserDetail.getValidInstance();
        detail.setDateOfBirth(LocalDate.now());

        // when
        Set<ConstraintViolation<ChangedUserDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Date of birth can be only in past");
    }

    @Test
    void whenPhoneIsNull_thenHasConstraintViolation() {
        // given
        ChangedUserDetail detail = ChangedUserDetail.getValidInstance();
        detail.setPhone(null);

        // when
        Set<ConstraintViolation<ChangedUserDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Phone is required");
    }

    @Test
    void whenPhoneIsNotLikePattern_thenHasConstraintViolation() {
        // given
        ChangedUserDetail detail = ChangedUserDetail.getValidInstance();
        detail.setPhone("321jj4few");

        // when
        Set<ConstraintViolation<ChangedUserDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid phone number");
    }

    @Test
    void whenAboutIsTooLarge_thenHasConstraintViolation() {
        // given
        ChangedUserDetail detail = ChangedUserDetail.getValidNecessaryFieldsInstance();
        detail.setAbout("""
            Hello, i`m Peter. I`m from Korea and i want to say, that i like animals.
            I have two daughters and i give them all of my love.
            I want to improve my life, and i do all for achieve it.
            Thank very much my family for supporting me, it is really helpful.
        """);

        // when
        Set<ConstraintViolation<ChangedUserDetail>> violations = validator.validate(detail);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Length of about shouldn`t be greater than 100 characters");
    }

}
