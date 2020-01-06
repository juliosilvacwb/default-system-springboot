package com.springboot.api.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.springboot.api.entities.Module;
import com.springboot.api.entities.Role;
import com.springboot.api.entities.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.NoArgsConstructor;

/**
 * JwtUserFactory
 */
@NoArgsConstructor
public class JwtUserFactory {

    /**
     * 
     * Convert user in JWTUser;
     * 
     * @param user
     * @return JwtUser
     */
    public static JwtUser create(User user) {
        return JwtUser.builder()
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .companyId(user.getCompany().getId())
                .authorities(mapToGrantedAuthorites(user.getRoles(), user.getModules()))
                .build();
    }

    /**
     * 
     * Convert roles and modules in authorities
     * 
     * @param roles
     * @param modules
     * @return Collection<? extends GrantedAuthority>
     */
    private static Collection<? extends GrantedAuthority> mapToGrantedAuthorites(List<Role> roles,
            List<Module> modules) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        modules.forEach(module -> authorities.add(new SimpleGrantedAuthority(module.getName())));
        return authorities;
    }
    
}