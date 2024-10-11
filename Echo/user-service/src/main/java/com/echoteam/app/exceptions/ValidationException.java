package com.echoteam.app.exceptions;

import com.echoteam.app.entities.staff.Violation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class ValidationException extends RuntimeException{
    private final List<Violation> violations;
}
