package com.springboot.api.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.springboot.api.entities.dtos.ResponseDTO;
import com.springboot.api.entities.dtos.TokenDTO;
import com.springboot.api.security.utils.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins="*")
public class AuthenticationController {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer";

    @Autowired
    private JwtTokenUtil jtwTokenUtil;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDTO<TokenDTO>> refreshTokenJwt(HttpServletRequest request) {
        ResponseDTO<TokenDTO> response = new ResponseDTO<>();
        Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

        if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
            token = Optional.of(token.get().substring(7));
        }

        if (!token.isPresent()) {
            response.addError(messageSource.getMessage("token.required", null, LocaleContextHolder.getLocale()));
        } else if (!jtwTokenUtil.validToken(token.get())) {
            response.addError(messageSource.getMessage("invalid.token", null, LocaleContextHolder.getLocale()));
        }

        if (!response.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(response);
        }

        String refreshedToken = jtwTokenUtil.refreshToken(token.get());
        response.setBody(new TokenDTO(refreshedToken));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-authenticated")
    public ResponseEntity<ResponseDTO<String>> isAuthenticated(HttpServletRequest request) {
        ResponseDTO<String> response = new ResponseDTO<>();
        Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

        if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
            token = Optional.of(token.get().substring(7));
        }

        if (!token.isPresent() || !jtwTokenUtil.validToken(token.get())) {
            response.setBody(messageSource.getMessage("not.authenticated", null, LocaleContextHolder.getLocale()));
        } else {
            response.setBody(messageSource.getMessage("authenticated", null, LocaleContextHolder.getLocale()));
        }

        return ResponseEntity.ok(response);
    }
    
}