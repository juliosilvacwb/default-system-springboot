package com.springboot.api.repositories.event_handler;

import com.springboot.api.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @HandleBeforeCreate
    public void handleUserCreate(User user) throws Exception {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @HandleBeforeSave
    public void handleUserUpdate(User user) throws Exception {
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }

}