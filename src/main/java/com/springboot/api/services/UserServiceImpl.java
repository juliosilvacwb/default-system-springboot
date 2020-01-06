package com.springboot.api.services;

import java.util.Optional;

import com.springboot.api.entities.User;
import com.springboot.api.repositories.AuthenticationRepository;
import com.springboot.api.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public Optional<User> findUserByEmail(String email) {
        Optional<User> user = this.authenticationRepository.findByEmail(email);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) throws Exception {
        return userRepository.findById(id);
    }

}