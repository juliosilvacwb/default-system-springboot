package com.springboot.api.entities.projections;

import java.time.LocalDateTime;
import java.util.List;

import com.springboot.api.entities.Company;
import com.springboot.api.entities.User;
import com.springboot.api.entities.Role;
import com.springboot.api.entities.Module;

import org.springframework.data.rest.core.config.Projection;


/**
 * UserProjection
 */
@Projection(name = "customUser", types = { User.class })
public interface UserProjection {
    Long getId();
    String getFirstname();
    String getLastname();
    String getEmail();
    Company getCompany();
    List<Role> getRoles();
    List<Module> getModules();
    String getImage();
    LocalDateTime getCreateDate();
    LocalDateTime getUpdateDate();
}