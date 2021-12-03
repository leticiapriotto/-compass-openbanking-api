package br.com.compass.products.exception;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class ErrorHandler {

    private String errors = "";

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handle(MethodArgumentNotValidException exception) {
        exception.getBindingResult().getFieldErrors().forEach((error) -> {
            this.errors += "\\" + error.getField() + ": " + error.getDefaultMessage() + ". ";
        });

        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), errors);
        this.errors = "";

        return exceptionResponse;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ExceptionResponse notFoundEntity(EntityNotFoundException exception) {
        return new ExceptionResponse( HttpStatus.NOT_FOUND.value(), "Product Not Found");
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ExceptionResponse notFoundDelete(EmptyResultDataAccessException exception) {
        return new ExceptionResponse( HttpStatus.NOT_FOUND.value(), "Product Not Found");
    }

    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionResponse notAllowed(HttpRequestMethodNotSupportedException exception) {
        return new ExceptionResponse( HttpStatus.METHOD_NOT_ALLOWED.value(), "Method Not Allowed");
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionResponse pageUnavailable(MethodArgumentTypeMismatchException exception) {
        return new ExceptionResponse( HttpStatus.BAD_REQUEST.value(), "Page Unavailable");
    }

}
