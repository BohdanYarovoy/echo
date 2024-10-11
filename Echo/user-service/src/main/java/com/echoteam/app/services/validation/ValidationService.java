package com.echoteam.app.services.validation;

import com.echoteam.app.exceptions.ValidationException;

public interface ValidationService {

    <T> boolean isValid(T object) throws ValidationException;

}
