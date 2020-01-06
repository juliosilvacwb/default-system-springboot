package com.springboot.api.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.springboot.api.entities.User;
import com.springboot.api.entities.dtos.JwtAuthenticationDTO;
import com.springboot.api.entities.dtos.ResponseDTO;
import com.springboot.api.entities.dtos.TokenDTO;
import com.springboot.api.entities.dtos.UserDTO;
import com.springboot.api.security.utils.JTWTokenUtil;
import com.springboot.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JTWTokenUtil jtwTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private UserService userService;

    /**
     * new Token
     * 
     * @param authenticationDTO
     * @param result
     * @return ResponseEntity<ResponseDTO<Token>>
     * @throws AuthenticationException
     */
    @PostMapping
    public ResponseEntity<ResponseDTO<UserDTO>> newTokenJwt(@Valid @RequestBody JwtAuthenticationDTO authenticationDTO, 
        BindingResult result) throws AuthenticationException {
        
        ResponseDTO<UserDTO> response = new ResponseDTO<>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.addError(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getEmail(), authenticationDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDTO.getEmail());
        String token = jtwTokenUtil.getToken(userDetails);

        Optional<User> optionalUser = userService.findUserByEmail(authenticationDTO.getEmail());
        UserDTO userDTO = UserDTO.parseUserDTO(optionalUser.get());
        userDTO.setToken(token);

        response.setBody(userDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDTO<TokenDTO>> refreshTokenJwt(HttpServletRequest request) {
        ResponseDTO<TokenDTO> response = new ResponseDTO<>();
        Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

        if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
            token = Optional.of(token.get().substring(7));
        }

        if (!token.isPresent()) {
            response.addError("Token is required");
        } else if (!jtwTokenUtil.validToken(token.get())) {
            response.addError("Invalid token");
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
            response.setBody("Is not authenticated");
        } else {
            response.setBody("Authenticated");
        }

        return ResponseEntity.ok(response);
    }
    
}