package com.springboot.api.services;

import java.util.Optional;

import com.springboot.api.entities.User;

/**
 * UserService
 */
public interface UserService {

    Optional<User> findById(Long id) throws Exception ;
    Optional<User> findUserByEmail(String email);
}