package com.springboot.api.security.services;

import java.util.Optional;

import com.springboot.api.entities.User;
import com.springboot.api.security.JwtUserFactory;
import com.springboot.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * JwtUserDetailsServiceImpl
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findUserByEmail(username);

        if (user.isPresent()) {
            return JwtUserFactory.create(user.get());
        }

        throw new UsernameNotFoundException("User not found");
    
    }

    
}