package com.springboot.api.security.filters;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.api.entities.User;
import com.springboot.api.entities.dtos.JwtAuthenticationDTO;
import com.springboot.api.entities.dtos.UserDTO;
import com.springboot.api.security.JwtUser;
import com.springboot.api.security.utils.JwtTokenUtil;
import com.springboot.api.services.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private JwtTokenUtil jwtTokenUtil;
    private UserService userService;
    
    protected JwtLoginFilter(final RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
	}
    
	public JwtLoginFilter(final String url, final AuthenticationManager authManager, JwtTokenUtil jwtTokenUtil, UserService userService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        final JwtAuthenticationDTO jwtAuthenticationDTO = new ObjectMapper().readValue(req.getInputStream(), JwtAuthenticationDTO.class);
        final UsernamePasswordAuthenticationToken authentication 
            = new UsernamePasswordAuthenticationToken(jwtAuthenticationDTO.getEmail(), jwtAuthenticationDTO.getPassword());
        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest req, final HttpServletResponse res, final FilterChain chain, final Authentication auth) {
        final JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        jwtUser.clearSensitiveInfo();
        addAuthentication(req, res, auth);
    }

    public void addAuthentication(final HttpServletRequest req, final HttpServletResponse res, final Authentication auth) {

        final JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        final String token = jwtTokenUtil.getToken(jwtUser);
        res.addHeader(JwtTokenUtil.getTokenPrefix(), JwtTokenUtil.getHeaderAccessToken() + " " + token);

        Optional<User> optionalUser = userService.findUserByEmail(jwtUser.getUsername());
        UserDTO userDTO = UserDTO.parseUserDTO(optionalUser.get());

        res.setContentType("aplication/json");

        try {
            res.getWriter().write(userDTO.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
