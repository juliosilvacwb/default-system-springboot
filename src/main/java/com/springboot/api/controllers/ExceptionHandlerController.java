package com.springboot.api.controllers;

import com.springboot.api.entities.dtos.ResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * ExceptionHandlerController
 */
@ControllerAdvice
@RestController
@Slf4j
public class ExceptionHandlerController {

    @Autowired
    private MessageSource messageSource;
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<?>> handleException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        if(ex.getMessage().contains("Access is denied")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseDTO<>().addError(messageSource.getMessage("access.denied", null, LocaleContextHolder.getLocale())));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO<>().addError(ex.getClass().getName() + " - "+ex.getMessage()));
    }
   
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDTO<?>> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseDTO<>().addError(messageSource.getMessage("invalid.credentials", null, LocaleContextHolder.getLocale())));
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDTO<?>> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseDTO<>().addError(messageSource.getMessage("access.denied", null, LocaleContextHolder.getLocale())));
    }

}
