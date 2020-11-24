package com.jp.dark.exceptions;

import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    private List<String> errors;

    public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach(error->this.errors.add(error.getDefaultMessage()));

    }

    public ApiErrors(BusinessException exception) {
        this.errors = Arrays.asList(exception.getMessage());
    }

    public ApiErrors(VisitaNotFoundException exception) {
        this.errors = Arrays.asList(exception.getMessage());
    }

    public ApiErrors(PersonaAlreadyExistsException exception) {
        this.errors = Arrays.asList(exception.getMessage());
    }

    public ApiErrors(ConstraintViolationException exception) {
        this.errors = Arrays.asList(exception.getMessage());
    }

    public List<String> getErrors() {
        return errors;
    }
}
