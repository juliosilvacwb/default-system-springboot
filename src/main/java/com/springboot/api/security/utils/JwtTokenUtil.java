package com.springboot.api.security.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

/**
 * JTWTokenUtil
 */
@Component
public class JwtTokenUtil {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_ROLE = "role";
    static final String CLAIM_KEY_CREATED = "created";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_ACCESS_TOKEN = "Authorization";

    public static String getTokenPrefix() {
        return TOKEN_PREFIX;
    }
   
    public static String getHeaderAccessToken() {
        return HEADER_ACCESS_TOKEN;
    }

    @Getter
    @Value("${jwt.secret}")
    private String secret;

    @Getter
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 
     * Parse token JWT to get body.
     * 
     * @param token
     * @return Claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * Get username (email) from token JWT.
     * 
     * @param token
     * @return String
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 
     * Get date expiration from token JWT
     * 
     * @param token
     * @return Date
     */
    public Date getExpirationDateFromToken​(String token) {
        Date expiraion;
        try {
            Claims claims = getClaimsFromToken(token);
            expiraion = claims.getExpiration();
        } catch (Exception e) {
            expiraion = null;
        }
        return expiraion;
    }




    /**
     * Create a new token (refresh)
     * 
     * @param token
     * @return String
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = newToken(claims);  
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }


    /**
     * Verify if token is valid.
     * 
     * @param token
     * @return boolean
     */
    public boolean validToken(String token) {
        return !expiredToken(token);
    }


    /**
     * 
     *  Get new token based in user details;
     * 
     * @param userDetails
     * @return
     */
    public String getToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));
        claims.put(CLAIM_KEY_CREATED, new Date());
        return newToken(claims);
    }

    /**
     * 
     * Get expiratoin date based in current date.
     * 
     * @return Date
     */
    private Date newExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * Verify if token is valid.
     * 
     * @param token
     * @return boolean
     */
    private boolean expiredToken(String token) {
        Date expirationDate = this.getExpirationDateFromToken​(token);
        if(expirationDate == null) {
            return false;
        }
        return expirationDate.before(new Date());
    }

    /**
     * New token based on data user
     * 
     * @param claims
     * @return String
     */
    private String newToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(newExpirationDate()).signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}