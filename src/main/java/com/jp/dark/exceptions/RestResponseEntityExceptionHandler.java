package com.jp.dark.exceptions;

import com.jp.dark.security.exceptions.InvaliPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.DateTimeException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBusinessException(BusinessException exception) {
        return new ApiErrors(exception);
    }

    @ExceptionHandler(VisitaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleVisitaNotFoundException(VisitaNotFoundException exception) {
        return new ApiErrors(exception);
    }
    @ExceptionHandler(PersonaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePersonaNotFoundExceptionException(PersonaNotFoundException exception) {
        return new ApiErrors(exception.getMessage());
    }
    @ExceptionHandler(PasswordInvalidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePasswordInvalidException(PasswordInvalidException exception) {
        return new ApiErrors(exception.getMessage());
    }

    @ExceptionHandler(InvaliPasswordException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleInvaliPasswordException(InvaliPasswordException exception) {
        return new ApiErrors(exception);
    }
    @ExceptionHandler(DateTimeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleDateTimeException(DateTimeException exception) {
        return new ApiErrors(exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintViolationException(ConstraintViolationException exception) {

        return new ApiErrors(exception);
    }

    @ExceptionHandler(com.mysql.cj.jdbc.exceptions.MysqlDataTruncation.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlecomMysqlDataTruncation(com.mysql.cj.jdbc.exceptions.MysqlDataTruncation exception) {

        return new ApiErrors(exception.getMessage());
    }

    @ExceptionHandler(ServiceProvidedNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintViolationException(ServiceProvidedNotFoundException exception) {

        return new ApiErrors(exception);
    }

    @ExceptionHandler(PersonaAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlePersonaAlreadyExistsException(PersonaAlreadyExistsException exception) {
        return new ApiErrors(exception);
    }

    @ExceptionHandler(ServiceProvidedAlreadyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleServiceProvidedAlreadyException(ServiceProvidedAlreadyException exception) {
        return new ApiErrors(exception);
    }

    @ExceptionHandler(FailedOnCreateFolderException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleFailedOnCreateFolderException(FailedOnCreateFolderException exception) {
        return new ApiErrors(exception.getMessage());
    }

    @ExceptionHandler(CallNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleCallNotFoundException(CallNotFoundException exception) {
        return new ApiErrors(exception.getMessage());
    }

    @ExceptionHandler(EslocNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors EslocNotFoundException(EslocNotFound exception) {
        return new ApiErrors(exception.getMessage());
    }

    @ExceptionHandler(ServiceOnEslocAlreadyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors ServiceOnEslocAlreadyExceptionException(ServiceOnEslocAlreadyException exception) {
        return new ApiErrors(exception.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors NumberFormatExceptionException(NumberFormatException exception) {
        return new ApiErrors(exception.getMessage());
    }
}