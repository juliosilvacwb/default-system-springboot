package com.springboot.api.controllers;

import java.util.Optional;

import com.springboot.api.entities.User;
import com.springboot.api.entities.dtos.ResponseDTO;
import com.springboot.api.entities.dtos.UserDTO;
import com.springboot.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * UserController
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}/full")
    public ResponseEntity<ResponseDTO<UserDTO>> getCompleteUser(@PathVariable Long id) throws Exception {
        Optional<User> optional = userService.findById(id);

        if(optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO<UserDTO>().addError("User not found."));
        }

        ResponseDTO<UserDTO> response = new ResponseDTO<UserDTO>(UserDTO.parseUserDTO(optional.get()), null);
        return ResponseEntity.ok(response);
    }
    
    
}