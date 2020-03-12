package com.springboot.api.controllers;

import java.util.Optional;

import com.springboot.api.entities.User;
import com.springboot.api.entities.dtos.ResponseDTO;
import com.springboot.api.entities.dtos.UserDTO;
import com.springboot.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * UserController
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/{id}/full")
    public ResponseEntity<ResponseDTO<UserDTO>> getCompleteUser(@PathVariable Long id) throws Exception {
        Optional<User> optional = userService.findById(id);

        if(optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<UserDTO>().addError(messageSource.getMessage("user.not.found", null, LocaleContextHolder.getLocale())));
        }

        ResponseDTO<UserDTO> response = new ResponseDTO<UserDTO>(UserDTO.parseUserDTO(optional.get()), null);
        return ResponseEntity.ok(response);
    }

}