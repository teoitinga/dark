package com.jp.dark.exceptions;

import com.jp.dark.security.exceptions.InvaliPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ApiErrors handleValidationException(MethodArgumentNotValidException exception){
//        BindingResult bindingResult = exception.getBindingResult();
//        return new ApiErrors(bindingResult);
//    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessException(BusinessException exception){
        return new ApiErrors(exception);
    }

    @ExceptionHandler(VisitaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleVisitaNotFoundException(VisitaNotFoundException exception){
        return new ApiErrors(exception);
    }
    @ExceptionHandler(InvaliPasswordException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleInvaliPasswordException(InvaliPasswordException exception){
        return new ApiErrors(exception);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintViolationException(ConstraintViolationException exception){

        return new ApiErrors(exception);
    }
    @ExceptionHandler(ServiceProvidedNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintViolationException(ServiceProvidedNotFoundException exception){

        return new ApiErrors(exception);
    }

    @ExceptionHandler(PersonaAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlePersonaAlreadyExistsException(PersonaAlreadyExistsException exception){
        return new ApiErrors(exception);
    }
    @ExceptionHandler(ServiceProvidedAlreadyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleServiceProvidedAlreadyException(ServiceProvidedAlreadyException exception){
        return new ApiErrors(exception);
    }
}
