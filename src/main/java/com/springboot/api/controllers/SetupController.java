package com.springboot.api.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.springboot.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * SetupController
 */
@RestController
public class SetupController {

    @Autowired
    private UserService userService;

    @PostMapping("/setup")
    public  ResponseEntity<?> setup() {
        String response = userService.setup();
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }
    
}