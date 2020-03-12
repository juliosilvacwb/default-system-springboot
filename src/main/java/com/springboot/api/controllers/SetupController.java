package com.springboot.api.controllers;

import com.springboot.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * UserController
 */
@RestController
@RequestMapping("/users")
public class SetupController {

    @Autowired
    private UserService userService;

    @PostMapping("/setup")
    public  ResponseEntity<?> setup() {
        String response = userService.setup();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String addData(Model model) {
        return "redirect:/swagger-ui.html";
    }
    
}