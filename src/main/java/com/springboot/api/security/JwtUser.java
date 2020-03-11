package com.springboot.api.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * JwtUser
 */
@AllArgsConstructor
@Builder
public class JwtUser implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Getter
    private Long id;

    @Getter
    private Long companyId;

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public void clearSensitiveInfo(){
        this.password = "";
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    
}