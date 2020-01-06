package com.springboot.api.repositories;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.springboot.api.entities.User;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * AuthenticationRepository
 */
@Repository
@Transactional(readOnly=true)
public class AuthenticationRepository {

    @PersistenceContext
    protected EntityManager manager;
    
    public Optional<User> findByEmail(String email) {

        String query = "select distinct(u) from User u left join u.roles r left join u.modules m join u.company c where u.email = :email";        

        return Optional.ofNullable( manager.createQuery(query, User.class).setParameter("email", email).getSingleResult());
    }
    
}