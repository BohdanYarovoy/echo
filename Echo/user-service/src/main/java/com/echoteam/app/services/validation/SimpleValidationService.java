package com.echoteam.app.services.validation;

import com.echoteam.app.entities.dto.entityDTO.ForValidation;
import com.echoteam.app.entities.staff.Violation;
import com.echoteam.app.exceptions.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SimpleValidationService implements ValidationService {
    private final Validator validator;

    // https://javarush.com/groups/posts/3918-rest-api-i-validacija-dannihkh
    @Override
    public <T> boolean isValid(T object) throws ValidationException {
        Set<ConstraintViolation<?>> constraintViolations = new HashSet<>();
        validateObject(object, constraintViolations);
        if (!constraintViolations.isEmpty()) {
            throw new ValidationException(buildVioldationList(constraintViolations));
        }
        return true;
    }

    private <T> void validateObject(T target, Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.addAll(validator.validate(target));

        Class<?> clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ForValidation.class)) {
                try {
                    // Дозволяємо доступ до приватних полів
                    field.setAccessible(true);
                    Object object = field.get(target);
                    if (object != null) {
                        validateObject(object, constraintViolations);
                    }
                } catch (IllegalAccessException e) {
                    throw new ValidationException(List.of(new Violation("Failed", "Annotation process was failed")));
                }
            }
        }
    }

    private List<Violation> buildVioldationList(Set<ConstraintViolation<?>> violationSet) {
        return violationSet.stream()
                .map(e ->
                    new Violation(
                            e.getPropertyPath().toString(),
                            e.getMessage()
                    )
                ).toList();
    }

}
