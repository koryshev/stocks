package com.koryshev.stocks.controller;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Customizes responses for some exceptions occurred in controller layer.
 *
 * @author Ivan Koryshev
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Customize the response for exception occurred during request validation.
     * <p>This method delegates to {@link ResponseEntityExceptionHandler#handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<String> errors = Stream.concat(
                result.getFieldErrors().stream().map(e -> e.getField() + ": " + e.getDefaultMessage()),
                result.getGlobalErrors().stream().map(e -> e.getObjectName() + ": " + e.getDefaultMessage()))
                .collect(Collectors.toList());

        Map<String, Object> errorAttributes = new DefaultErrorAttributes().getErrorAttributes(request, false);
        errorAttributes.remove("errors");
        errorAttributes.put("status", HttpStatus.BAD_REQUEST.value());
        errorAttributes.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorAttributes.put("message", errors);
        errorAttributes.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        return handleExceptionInternal(ex, errorAttributes, headers, status, request);
    }
}
