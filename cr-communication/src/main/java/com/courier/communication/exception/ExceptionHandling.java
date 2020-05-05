package com.courier.communication.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
@Log4j2
public class ExceptionHandling extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(ApplicationException.class)
//    public ResponseEntity<CourierApplicationException> handleApplicationException(ApplicationException exception) {
//        log.error("ApplicationException: ", exception);
//        CourierApplicationException responseException =
//                new CourierApplicationException().code(exception.getStatusCode()).message(exception.getMessage());
//        return new ResponseEntity<>(responseException, HttpStatus.valueOf(exception.getStatusCode()));
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<CourierApplicationException> handleException(Exception exception) {
//        log.error("General exception: ", exception);
//        CourierApplicationException responseException =
//                new CourierApplicationException().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                        .message(exception.getMessage());
//        return new ResponseEntity<>(responseException, HttpStatus.valueOf(responseException.getCode()));
//    }

    /**
     * BindException: This exception is thrown when fatal binding errors occur.
     * MethodArgumentNotValidException:
     * This exception is thrown when argument annotated with @Valid failed validation:
     */
    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NotNull MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatus status,
            @NotNull WebRequest request
    ) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiException apiError =
                new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    /**
     * MissingServletRequestPartException: This exception is thrown when when the part of a multipart request not found
     * MissingServletRequestParameterException: This exception is thrown when request missing parameter:
     */
    @NotNull
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            @NotNull MissingServletRequestParameterException ex, @NotNull HttpHeaders headers,
            @NotNull HttpStatus status, @NotNull WebRequest request
    ) {
        String error = ex.getParameterName() + " parameter is missing";

        ApiException apiError =
                new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /*
     * ConstrainViolationException: This exception reports the result of constraint violations:
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request
    ) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ApiException apiError =
                new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * TypeMismatchException: This exception is thrown when try to set bean property with wrong type.
     * MethodArgumentTypeMismatchException: This exception is thrown when method argument is not the expected type:
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request
    ) {
        String error = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();

        ApiException apiError =
                new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
